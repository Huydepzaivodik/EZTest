package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dal.AccountDAO;
import dal.ChoiceDAO;
import dal.QuestionDAO;
import dal.QuizDAO;
import dal.QuizResultDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Choice;
import model.Course_ClassName_QuizName;
import model.Quiz_Question;
import model.Quiz_Result;
import dal.StudentChoiceDAO;
import java.sql.Timestamp;
import model.Account;
import model.StudentChoice;

@WebServlet(name = "ViewScoreStudentController", urlPatterns = {"/viewScoreOfStudent"})
public class ViewScoreStudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("account") != null) {
            QuizResultDAO qrDao = new QuizResultDAO();
            int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
            if (request.getSession().getAttribute("role").equals("Student")) {

                int student_id = Integer.parseInt(request.getParameter("student_id"));
                Quiz_Result qr = qrDao.getResultStudent(quiz_id, student_id);

                //List các Question
                QuestionDAO queDao = new QuestionDAO();
                ArrayList<Quiz_Question> quizQuestionList = queDao.getListQuizQuestion(quiz_id);

                //List các choice 
                ChoiceDAO cDao = new ChoiceDAO();
                ArrayList<Choice> choiceList = cDao.getChoiceListByQuestionId();

                QuizDAO q = new QuizDAO();
                Course_ClassName_QuizName ccq = q.getCCQByQuizId(quiz_id);

                //List các choice sviên đã chọn 
                StudentChoiceDAO stdCDao = new StudentChoiceDAO();
                ArrayList<StudentChoice> studentChoiceList = stdCDao.getListStudentChoiceByStuIdAndQuizId(student_id, quiz_id);

                Timestamp startTimestamp = qr.getStart_time_student();
                Timestamp endTimestamp = qr.getEnd_time_student();

                long startTimeInMillis = startTimestamp.getTime();
                long endTimeInMillis = endTimestamp.getTime();

                // Tính thời gian làm bài trong milliseconds
                long timeTakenInMillis = endTimeInMillis - startTimeInMillis;

                // Chuyển đổi thời gian làm bài từ milliseconds sang giây
                long timeTakenInSeconds = timeTakenInMillis / 1000;

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
                request.setAttribute("time_taken", timeTakenInSeconds);
                request.setAttribute("choiceList", choiceList);
                request.setAttribute("ccq", ccq);
                request.setAttribute("quiz_result", qr);
                request.getRequestDispatcher("student/StudentViewScore.jsp").forward(request, response);

            } else if (request.getSession().getAttribute("role").equals("Teacher")) {

                int teacher_id = Integer.parseInt(request.getParameter("teacher_id"));
                Quiz_Result qr = qrDao.getResultStudent(quiz_id, teacher_id);

                //List các Question
                QuestionDAO queDao = new QuestionDAO();
                ArrayList<Quiz_Question> quizQuestionList = queDao.getListQuizQuestion(quiz_id);

                //List các choice 
                ChoiceDAO cDao = new ChoiceDAO();
                ArrayList<Choice> choiceList = cDao.getChoiceListByQuestionId();

                QuizDAO q = new QuizDAO();
                Course_ClassName_QuizName ccq = q.getCCQByQuizId(quiz_id);

                //List các choice sviên đã chọn 
                StudentChoiceDAO stdCDao = new StudentChoiceDAO();
                ArrayList<StudentChoice> teacherChoiceList = stdCDao.getListStudentChoiceByStuIdAndQuizId(teacher_id, quiz_id);

                Timestamp startTimestamp = qr.getStart_time_student();
                Timestamp endTimestamp = qr.getEnd_time_student();

                long startTimeInMillis = startTimestamp.getTime();
                long endTimeInMillis = endTimestamp.getTime();

                // Tính thời gian làm bài trong milliseconds
                long timeTakenInMillis = endTimeInMillis - startTimeInMillis;

                // Chuyển đổi thời gian làm bài từ milliseconds sang giây
                long timeTakenInSeconds = timeTakenInMillis / 1000;

                Gson gson = new Gson();
                JsonArray questionJson = new JsonArray();
                for (Quiz_Question quizQuestion : quizQuestionList) {
                    JsonObject qJo = new JsonObject();
                    qJo.add("question", gson.toJsonTree(quizQuestion));
                    JsonArray answers = (JsonArray) gson.toJsonTree(cDao.getChoiceListByQuestionId(quizQuestion.getQuestion_id()));
                    qJo.add("answers", answers);
                    questionJson.add(qJo);
                }
                JsonArray chooseJson = (JsonArray) gson.toJsonTree(teacherChoiceList);

                request.setAttribute("questionJson", questionJson);
                request.setAttribute("choosenJson", chooseJson);
                request.setAttribute("time_taken", timeTakenInSeconds);
                request.setAttribute("questionNumber", queDao.countTotalNumberQuestion(quiz_id));
                request.setAttribute("studentChoiceList", teacherChoiceList);
                request.setAttribute("quizQuestionList", quizQuestionList);
                request.setAttribute("choiceList", choiceList);
                request.setAttribute("ccq", ccq);
                request.setAttribute("quiz_result", qr);
                request.getRequestDispatcher("teacher/TeacherViewScore.jsp").forward(request, response);

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
