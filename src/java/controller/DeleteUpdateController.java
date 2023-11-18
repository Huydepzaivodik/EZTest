package controller;

import dal.AccountDAO;
import dal.ClassDAO;
import dal.CourseDAO;
import dal.QuizDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import model.Account;

@WebServlet(name = "DeleteUpdateController", urlPatterns = {"/deleteUpdate"})
public class DeleteUpdateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDAO accDAO = new AccountDAO();
        ClassDAO clDAO = new ClassDAO();
        CourseDAO courseDAO = new CourseDAO();
        QuizDAO quizDAO = new QuizDAO();

        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Admin")) {
                switch (request.getParameter("mod")) {
                    case "1": {
                        int id = Integer.parseInt(request.getParameter("id"));
                        accDAO.deleteAccountStudent(id);
                        response.sendRedirect("viewDetail");
                        break;
                    }
                    case "2": {
                        int id = Integer.parseInt(request.getParameter("id"));
                        accDAO.deleteAccountTeacher(id);
                        response.sendRedirect("viewDetail");
                        break;
                    }
                    case "3": {
                        int class_id = Integer.parseInt(request.getParameter("class_id"));
                        int student_id = Integer.parseInt(request.getParameter("student_id"));
                        clDAO.deleteAssignStudent(class_id, student_id);
                        response.sendRedirect("viewListStuInClass?class_id=" + class_id);
                        break;
                    }
                    case "4": {
                        int course_id = Integer.parseInt(request.getParameter("course_id"));
                        courseDAO.deactivateCourse(course_id);
                        response.sendRedirect("home");
                        break;
                    }
                    case "5": {
                        int class_id = Integer.parseInt(request.getParameter("class_id"));
                        int course_id = (int) request.getSession().getAttribute("course_id");
                        clDAO.deactivateClass(class_id);
                        response.sendRedirect("viewClass?course_id=" + course_id);
                        break;
                    }
                    case "6": {
                        int course_id = Integer.parseInt(request.getParameter("course_id"));
                        courseDAO.activateCourse(course_id);
                        response.sendRedirect("home");
                        break;
                    }
                    case "7": {
                        int class_id = Integer.parseInt(request.getParameter("class_id"));
                        int course_id = (int) request.getSession().getAttribute("course_id");
                        clDAO.activateClass(class_id);
                        response.sendRedirect("viewClass?course_id=" + course_id);
                        break;
                    }
                    case "8": {
                        int course_id = Integer.parseInt(request.getParameter("course_id"));
                        courseDAO.deleteCourse(course_id);
                        response.sendRedirect("home");
                        break;
                    }
                    case "9": {
                        int class_id = Integer.parseInt(request.getParameter("class_id"));
                        int course_id = (int) request.getSession().getAttribute("course_id");
                        clDAO.deleteClass(class_id);
                        response.sendRedirect("viewClass?course_id=" + course_id);
                        break;
                    }
                    default:
                        break;
                }
            } else if (request.getSession().getAttribute("role").equals("Teacher")) {
                if (request.getParameter("mod").equals("1")) {
                    int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
                    int class_id = (int) request.getSession().getAttribute("class_id");
                    quizDAO.deactivateQuiz(quiz_id);
                    response.sendRedirect("teacherViewQuiz?class_id=" + class_id);
                } else if (request.getParameter("mod").equals("2")) {
                    int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
                    int class_id = (int) request.getSession().getAttribute("class_id");
                    quizDAO.activateQuiz(quiz_id);
                    response.sendRedirect("teacherViewQuiz?class_id=" + class_id);
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
