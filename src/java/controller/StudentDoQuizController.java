package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dal.ChoiceDAO;
import dal.QuestionDAO;
import dal.QuizDAO;
import dal.QuizResultDAO;
import dal.StudentChoiceDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import model.Choice;
import model.Course_ClassName_QuizName;
import model.Quiz_Question;
import model.Quiz_Result;
import model.StudentChoice;

@WebServlet(name = "StudentDoQuizController", urlPatterns = {"/studentDoQuiz"})
public class StudentDoQuizController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Student")) {
                int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
                int student_id = Integer.parseInt(request.getParameter("student_id"));
                float duration = Float.parseFloat(request.getParameter("duration"));
                QuizResultDAO qr = new QuizResultDAO();

                QuestionDAO qDao = new QuestionDAO();
                QuizDAO quDao = new QuizDAO();

                boolean is_displaydetail = quDao.getQuizById(quiz_id).isIs_displaydetail();
                request.getSession().setAttribute("is_displaydetail", is_displaydetail);

                Quiz_Result quizResult = qr.getResultStudent(quiz_id, student_id);

                if (quizResult != null) {
                    QuizDAO q = new QuizDAO();
                    Course_ClassName_QuizName ccq = q.getCCQByQuizId(quiz_id);

                    QuestionDAO queDao = new QuestionDAO();
                    ArrayList<Quiz_Question> quizQuestionList = queDao.getListQuizQuestion(quiz_id);

                    ChoiceDAO cDao = new ChoiceDAO();
                    ArrayList<Choice> choiceList = cDao.getChoiceListByQuestionId();

                    StudentChoiceDAO stdCDao = new StudentChoiceDAO();
                    ArrayList<StudentChoice> studentChoiceList = stdCDao.getListStudentChoiceByStuIdAndQuizId(student_id, quiz_id);

                    Gson gson = new Gson();
                    JsonArray questionJson = new JsonArray();
                    for (Quiz_Question quizQuestion : quizQuestionList) {
                        JsonObject qJo = new JsonObject();
                        qJo.add("question", gson.toJsonTree(quizQuestion));
                        JsonArray answers = (JsonArray) gson.toJsonTree(cDao.getChoiceListByQuestionId(quizQuestion.getQuestion_id()));
                        qJo.add("answers", answers);
                        questionJson.add(qJo);
                    }
                    JsonArray chooseJson = (JsonArray) gson.toJsonTree(studentChoiceList);
                    request.setAttribute("questionJson", questionJson);
                    request.setAttribute("choosenJson", chooseJson);

                    Timestamp startTimestamp = quizResult.getStart_time_student();
                    Timestamp endTimestamp = quizResult.getEnd_time_student();

                    long startTimeInMillis = startTimestamp.getTime();
                    long endTimeInMillis = endTimestamp.getTime();

                    // Tính thời gian làm bài trong milliseconds
                    long timeTakenInMillis = endTimeInMillis - startTimeInMillis;

                    // Chuyển đổi thời gian làm bài từ milliseconds sang giây
                    long timeTakenInSeconds = timeTakenInMillis / 1000;

                    request.setAttribute("questionNumber", queDao.countTotalNumberQuestion(quiz_id));
                    request.setAttribute("studentChoiceList", studentChoiceList);
                    request.setAttribute("quizQuestionList", quizQuestionList);
                    request.setAttribute("choiceList", choiceList);
                    request.setAttribute("time_taken", timeTakenInSeconds);
                    request.setAttribute("ccq", ccq);
                    request.setAttribute("quiz_result", quizResult);
                    request.setAttribute("mess", "This quiz is done!");
                    request.getRequestDispatcher("student/StudentViewScore.jsp").forward(request, response);
                } else if (request.getParameter("mod") != null && quizResult == null) {

                    QuizDAO q = new QuizDAO();
                    Course_ClassName_QuizName ccq = q.getCCQByQuizId(quiz_id);

                    QuestionDAO queDao = new QuestionDAO();
                    ArrayList<Quiz_Question> quizQuestionList = queDao.getListQuizQuestion(quiz_id);

                    ChoiceDAO cDao = new ChoiceDAO();
                    ArrayList<Choice> choiceList = cDao.getChoiceListByQuestionId();

                    StudentChoiceDAO stdCDao = new StudentChoiceDAO();
                    ArrayList<StudentChoice> studentChoiceList = stdCDao.getListStudentChoiceByStuIdAndQuizId(student_id, quiz_id);

                    Gson gson = new Gson();
                    JsonArray questionJson = new JsonArray();
                    for (Quiz_Question quizQuestion : quizQuestionList) {
                        JsonObject qJo = new JsonObject();
                        qJo.add("question", gson.toJsonTree(quizQuestion));
                        JsonArray answers = (JsonArray) gson.toJsonTree(cDao.getChoiceListByQuestionId(quizQuestion.getQuestion_id()));
                        qJo.add("answers", answers);
                        questionJson.add(qJo);
                    }
                    JsonArray chooseJson = (JsonArray) gson.toJsonTree(studentChoiceList);
                    request.setAttribute("questionJson", questionJson);
                    request.setAttribute("choosenJson", chooseJson);

                    request.setAttribute("questionNumber", queDao.countTotalNumberQuestion(quiz_id));
                    request.setAttribute("studentChoiceList", studentChoiceList);
                    request.setAttribute("quizQuestionList", quizQuestionList);
                    request.setAttribute("choiceList", choiceList);
                    request.setAttribute("ccq", ccq);
                    request.setAttribute("quiz_result", quizResult);
                    request.setAttribute("mess", "You haven't done this quiz!");
                    request.getRequestDispatcher("student/StudentViewScore.jsp").forward(request, response);
                } else {

                    int totalQuestion = qDao.countTotalNumberQuestion(quiz_id);

                    request.setAttribute("duration", duration);
                    request.setAttribute("quiz_id", quiz_id);
                    request.setAttribute("totalQuestion", totalQuestion);
                    request.getRequestDispatcher("student/Take_Quiz.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect("login");
            }
        } else {
            response.sendRedirect("login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
