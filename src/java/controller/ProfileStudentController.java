package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet(name = "ProfileStudentController", urlPatterns = {"/profileStu"})
public class ProfileStudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Student")) {
                request.getRequestDispatcher("student/Profile_Student.jsp").forward(request, response);
            } else if (request.getSession().getAttribute("role").equals("Teacher")) {
                AccountDAO accDao = new AccountDAO();
                int id = Integer.parseInt(request.getParameter("id"));
                Account student = accDao.getStudentById(id);
                request.setAttribute("student", student);
                request.setAttribute("role", "Student");
                request.getRequestDispatcher("teacher/ViewStudent.jsp").forward(request, response);
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
