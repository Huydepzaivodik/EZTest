package controller;

import dal.QuizDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Quiz;

@WebServlet(name = "TeacherViewQuizController", urlPatterns = {"/teacherViewQuiz"})
public class TeacherViewQuizController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Teacher")) {
                QuizDAO qDAO = new QuizDAO();
                int class_id = Integer.parseInt(request.getParameter("class_id"));
                request.getSession().setAttribute("class_id", class_id);
                ArrayList<Quiz> quizList = qDAO.getListQuizByTeacher(class_id);
                request.setAttribute("quizList", quizList);
                request.getRequestDispatcher("teacher/ViewQuizDetail.jsp").forward(request, response);
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
