package controller;

import dal.AccountDAO;
import dal.QuestionDAO;
import dal.QuizDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import model.Account;
import model.Choice;
import model.Quiz;
import model.Quiz_Question;
import model.Quiz_Result;

@WebServlet(name = "TeacherViewQuizInforController", urlPatterns = {"/quiz-info"})
public class TeacherViewQuizInforController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role") != null && request.getSession().getAttribute("role").equals("Teacher")) {
                QuizDAO qDAO = new QuizDAO();
                int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
                request.getSession().setAttribute("quiz_id", quiz_id);
                String quizName = request.getParameter("quiz_name");
                request.getSession().setAttribute("quiz_name", quizName);
                Date today = new Date();
                Timestamp td = new Timestamp(today.getTime());
                Timestamp start_time = qDAO.getStart_time(quiz_id);

                int teacher_id = (int) request.getSession().getAttribute("teacher_id");
                int createdByTeacherID = qDAO.getCreatedByTeacherID(quiz_id);

                switch (request.getParameter("mod")) {
                    case "1":
                        if (start_time != null && td != null && td.compareTo(start_time) < 1 && teacher_id == createdByTeacherID) {

                            Quiz q1 = qDAO.getQuizById(quiz_id);
                            String quizName1 = q1.getQuiz_name();
                            request.setAttribute("quizName", quizName1);

                            Timestamp t = q1.getStart_time();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                            String formattedDateTime = dateFormat.format(new Date(t.getTime()));
                            request.setAttribute("start_time", formattedDateTime);

                            Timestamp t1 = q1.getDate_end();
                            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                            String formattedDateTime1 = dateFormat1.format(new Date(t1.getTime()));
                            request.setAttribute("due_date", formattedDateTime1);

                            Timestamp t2 = q1.getCreate_date();
                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                            String formattedDateTime2 = dateFormat2.format(new Date(t2.getTime()));
                            request.setAttribute("create_date", formattedDateTime2);

                            boolean visible = q1.isIs_visible();
                            request.setAttribute("isVisible", visible);

                            boolean display = q1.isIs_displaydetail();
                            request.setAttribute("isDisplayDetail", display);

                            int duration = q1.getDuration();
                            String unit = "second";
                            request.setAttribute("timeUnit", unit);

                            request.setAttribute("duration", duration);

                            QuestionDAO qdao = new QuestionDAO();
                            ArrayList<Quiz_Question> qq = qdao.getListQuizQuestion(quiz_id);
                            request.setAttribute("listQuestion", qq);

                            ArrayList<Choice> choices = qdao.getListChoice(quiz_id);
                            request.setAttribute("listChoice", choices);

                            request.getRequestDispatcher("teacher/Edit_Quiz.jsp").forward(request, response);
                        } else if (start_time != null && td != null && td.compareTo(start_time) < 1 && teacher_id != createdByTeacherID) {

                            Quiz q1 = qDAO.getQuizById(quiz_id);
                            String quizName1 = q1.getQuiz_name();
                            request.setAttribute("quizName", quizName1);

                            Timestamp t = q1.getStart_time();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                            String formattedDateTime = dateFormat.format(new Date(t.getTime()));
                            request.setAttribute("start_time", formattedDateTime);

                            Timestamp t1 = q1.getDate_end();
                            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                            String formattedDateTime1 = dateFormat1.format(new Date(t1.getTime()));
                            request.setAttribute("due_date", formattedDateTime1);

                            Timestamp t2 = q1.getCreate_date();
                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                            String formattedDateTime2 = dateFormat2.format(new Date(t2.getTime()));
                            request.setAttribute("create_date", formattedDateTime2);

                            boolean visible = q1.isIs_visible();
                            request.setAttribute("isVisible", visible);

                            boolean display = q1.isIs_displaydetail();
                            request.setAttribute("isDisplayDetail", display);

                            int duration = q1.getDuration();
                            String unit = "second";
                            request.setAttribute("timeUnit", unit);

                            request.setAttribute("duration", duration);

                            QuestionDAO qdao = new QuestionDAO();
                            ArrayList<Quiz_Question> qq = qdao.getListQuizQuestion(quiz_id);
                            request.setAttribute("listQuestion", qq);

                            ArrayList<Choice> choices = qdao.getListChoice(quiz_id);
                            request.setAttribute("listChoice", choices);
                            request.getRequestDispatcher("teacher/View_Quiz_Question.jsp").forward(request, response);
                        } else {
                            ArrayList<Quiz_Result> quizList = qDAO.getQuizResult(quiz_id);
                            request.setAttribute("quizList", quizList);
                            
                            List<Float> scores = qDAO.getScoresByQuizId(quiz_id);
                            int goodCount = 0;
                            int averageCount = 0;
                            int poorCount = 0;
                            for (float score : scores) {
                                if (score >= 8) {
                                    goodCount++;
                                } else if (score >= 4) {
                                    averageCount++;
                                } else {
                                    poorCount++;
                                }
                            }
                            AccountDAO aDAO = new AccountDAO();
                            ArrayList<Account> studentClassList = aDAO.getListStudentByQuizId(quiz_id);
                            request.setAttribute("studentClassList", studentClassList);
                            request.setAttribute("goodCount", goodCount);
                            request.setAttribute("averageCount", averageCount);
                            request.setAttribute("poorCount", poorCount);
                            request.getRequestDispatcher("teacher/ViewQuizInfo.jsp").forward(request, response);
                        }
                        break;
                    case "2":
                        Quiz q1 = qDAO.getQuizById(quiz_id);
                        String quizName1 = q1.getQuiz_name();
                        request.setAttribute("quizName", quizName1);

                        Timestamp t = q1.getStart_time();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                        String formattedDateTime = dateFormat.format(new Date(t.getTime()));
                        request.setAttribute("start_time", formattedDateTime);

                        Timestamp t1 = q1.getDate_end();
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                        String formattedDateTime1 = dateFormat1.format(new Date(t1.getTime()));
                        request.setAttribute("due_date", formattedDateTime1);

                        Timestamp t2 = q1.getCreate_date();
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                        String formattedDateTime2 = dateFormat2.format(new Date(t2.getTime()));
                        request.setAttribute("create_date", formattedDateTime2);

                        boolean visible = q1.isIs_visible();
                        request.setAttribute("isVisible", visible);

                        boolean display = q1.isIs_displaydetail();
                        request.setAttribute("isDisplayDetail", display);

                        int duration = q1.getDuration();
                        String unit = "second";
                        request.setAttribute("timeUnit", unit);

                        request.setAttribute("duration", duration);

                        QuestionDAO qdao = new QuestionDAO();
                        ArrayList<Quiz_Question> qq = qdao.getListQuizQuestion(quiz_id);
                        request.setAttribute("listQuestion", qq);

                        ArrayList<Choice> choices = qdao.getListChoice(quiz_id);
                        request.setAttribute("listChoice", choices);
                        request.getRequestDispatcher("teacher/View_Quiz_Question.jsp").forward(request, response);
                        break;
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
