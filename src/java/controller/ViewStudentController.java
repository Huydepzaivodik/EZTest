package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Account;

@WebServlet(name = "ViewStudentController", urlPatterns = {"/viewStudent"})
public class ViewStudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Admin")) {
                AccountDAO aDao = new AccountDAO();
                int course_id = (int) request.getSession().getAttribute("course_id");
                ArrayList<Account> assignedStd = aDao.getListStudentNotInCourse(course_id);              
                request.setAttribute("assignedStd", assignedStd);
                request.getRequestDispatcher("admin/ListStudentToAssign.jsp").forward(request, response);
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
