package controller;

import dal.ClassDAO;
import dal.QuizDAO;
import dal.QuizResultDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import model.Class;
import model.Quiz;
import model.Quiz_Result;

@WebServlet(name = "StudentViewClassController", urlPatterns = {"/studentViewClass"})
public class StudentViewClassController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Student")) {

                ClassDAO cldao = new ClassDAO();
                int course_id = Integer.parseInt(request.getParameter("course_id"));
                request.getSession().setAttribute("course_id", course_id);
                int student_id = (int) request.getSession().getAttribute("student_id");
                Class cl = cldao.getClassByStudent(course_id, student_id);
                request.setAttribute("cl", cl);

                QuizDAO qDAO = new QuizDAO();
                ArrayList<Quiz> quizList = qDAO.getListQuizInClassTotal(course_id, student_id);

                for (Quiz quiz : quizList) {
                    Date currentTime = new Date();
                    if (quiz.getStart_time().before(currentTime) && quiz.getDate_end().after(currentTime)) {
                        qDAO.updateIsvisibleQuiz(quiz.getQuiz_id());
                    }
                }
                ArrayList<Quiz> quizListIsvisible = qDAO.getListQuizInClass(course_id, student_id);
                request.setAttribute("quizList", quizListIsvisible);
                request.getRequestDispatcher("student/HomeClass_Student.jsp").forward(request, response);
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
