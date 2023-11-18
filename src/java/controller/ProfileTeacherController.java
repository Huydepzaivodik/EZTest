package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet(name = "ProfileTeacherController", urlPatterns = {"/profileTeacher"})
public class ProfileTeacherController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Teacher")) {
                request.getRequestDispatcher("teacher/Profile_Teacher.jsp").forward(request, response);
            } else if (request.getSession().getAttribute("role").equals("Student") || request.getSession().getAttribute("role").equals("Admin")) {
                AccountDAO accDao = new AccountDAO();
                int id = Integer.parseInt(request.getParameter("id"));
                Account teacher = accDao.getTeacherById(id);
                request.setAttribute("teacher", teacher);
                request.setAttribute("role", "Teacher");
                request.getRequestDispatcher("student/ViewTeacher.jsp").forward(request, response);
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