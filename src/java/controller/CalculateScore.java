package controller;

import dal.ChoiceDAO;
import dal.QuestionDAO;
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
import model.Choice;
import model.StudentChoice;

@WebServlet(name = "CalculateScore", urlPatterns = {"/calScore"})
public class CalculateScore extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Student")) {
                float score = 0;
                int student_id = Integer.parseInt(request.getParameter("student_id"));
                int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
                int course_id = Integer.parseInt(request.getParameter("course_id"));

                String endTimeString = request.getParameter("endTime");
                String startTimeString = request.getParameter("startTime");

                // Chuyển đổi chuỗi thành số nguyên (milliseconds)
                long startTime = Long.parseLong(startTimeString);
                long endTime = Long.parseLong(endTimeString);

                // Chuyển đổi số nguyên (milliseconds) thành đối tượng Timestamp
                Timestamp startTimestamp = new Timestamp(startTime);
                Timestamp endTimestamp = new Timestamp(endTime);

                QuizResultDAO qrDao = new QuizResultDAO();

                StudentChoiceDAO stdDao = new StudentChoiceDAO();
                ChoiceDAO chDao = new ChoiceDAO();
                ArrayList<StudentChoice> studentChoices = stdDao.getListStudentChoiceByStuIdAndQuizId(student_id, quiz_id);
                ArrayList<Integer> listQuestionId = stdDao.getQuestionIdInStudentChoice(student_id, quiz_id);
                for (int question_id : listQuestionId) {
                    ArrayList<StudentChoice> studentChoiceList = stdDao.getListStudentChoiceByStuIdAndQuestionId(student_id, question_id);
                    float scoreSmall = 0;

                    for (StudentChoice stdChoice : studentChoiceList) {
                        ArrayList<Choice> choiceList = chDao.getChoiceListByQuestionId(question_id);
                        for (Choice choice : choiceList) {
                            if (stdChoice.getStudent_choice().getChoice_id() == choice.getChoice_id()) {
                                scoreSmall = scoreSmall + choice.getWeight();
                            }
                        }
                        if (scoreSmall <= 0) { //Nếu như weight mà nhỏ hơn bằng 0 thì coi như câu đấy sai
                            scoreSmall = 0;
                        }
                    }
                    score = (score + scoreSmall);
                }
                QuestionDAO qDao = new QuestionDAO();
                int numberOfQuestion = qDao.countTotalNumberQuestion(quiz_id);
                score = score / (numberOfQuestion * 10);

                boolean exists = qrDao.existsQuizResult(quiz_id, student_id);

                if (exists) {
                    qrDao.updateScore(quiz_id, student_id, (float) score, startTimestamp, endTimestamp);
                } else {
                    qrDao.addScoreOfStudent(quiz_id, student_id, (float) score, startTimestamp, endTimestamp);
                }
                boolean checkDoQuiz = qrDao.existsQuizResult(quiz_id, student_id);
                response.sendRedirect("viewScoreOfStudent?student_id=" + student_id + "&quiz_id=" + quiz_id);
            } else if (request.getSession().getAttribute("role").equals("Teacher")) {
                float score = 0;
                int teacher_id = Integer.parseInt(request.getParameter("teacher_id"));
                int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
                int course_id = Integer.parseInt(request.getParameter("course_id"));
         
                String endTimeString = request.getParameter("endTime");
                String startTimeString = request.getParameter("startTime");

                // Chuyển đổi chuỗi thành số nguyên (milliseconds)
                long startTime = Long.parseLong(startTimeString);
                long endTime = Long.parseLong(endTimeString);

                // Chuyển đổi số nguyên (milliseconds) thành đối tượng Timestamp
                Timestamp startTimestamp = new Timestamp(startTime);
                Timestamp endTimestamp = new Timestamp(endTime);

                QuizResultDAO qrDao = new QuizResultDAO();

                StudentChoiceDAO stdDao = new StudentChoiceDAO();
                ChoiceDAO chDao = new ChoiceDAO();
                ArrayList<StudentChoice> studentChoices = stdDao.getListStudentChoiceByStuIdAndQuizId(teacher_id, quiz_id);
                ArrayList<Integer> listQuestionId = stdDao.getQuestionIdInStudentChoice(teacher_id, quiz_id);
                for (int question_id : listQuestionId) {
                    ArrayList<StudentChoice> studentChoiceList = stdDao.getListStudentChoiceByStuIdAndQuestionId(teacher_id, question_id);
                    float scoreSmall = 0;

                    for (StudentChoice stdChoice : studentChoiceList) {
                        ArrayList<Choice> choiceList = chDao.getChoiceListByQuestionId(question_id);
                        for (Choice choice : choiceList) {
                            if (stdChoice.getStudent_choice().getChoice_id() == choice.getChoice_id()) {
                                scoreSmall = scoreSmall + choice.getWeight();
                            }
                        }
                        if (scoreSmall <= 0) { //Nếu như weight mà nhỏ hơn bằng 0 thì coi như câu đấy sai
                            scoreSmall = 0;
                        }
                    }
                    score = (score + scoreSmall);
                }
                QuestionDAO qDao = new QuestionDAO();
                int numberOfQuestion = qDao.countTotalNumberQuestion(quiz_id);
                score = score / (numberOfQuestion * 10);
                boolean exists = qrDao.existsQuizResult(quiz_id, teacher_id);  
                if (exists) {
                    qrDao.updateScore(quiz_id, teacher_id, (float) score, startTimestamp, endTimestamp);
                } else {
                    qrDao.addScoreOfTeacher(quiz_id, teacher_id, (float) score, startTimestamp, endTimestamp);
                }
                boolean checkDoQuiz = qrDao.existsQuizResult(quiz_id, teacher_id);
                response.sendRedirect("viewScoreOfStudent?teacher_id=" + teacher_id + "&quiz_id=" + quiz_id);

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
