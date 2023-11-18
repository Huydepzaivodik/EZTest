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

@WebServlet(name = "ViewDetailController", urlPatterns = {"/viewDetail"})
public class ViewDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Admin")) {
                //List student             
                AccountDAO accDao = new AccountDAO();
                ArrayList<Account> stdList = accDao.getListStudent();
                request.setAttribute("stdList", stdList);
                //List teacher         
                ArrayList<Account> teacherList = accDao.getListTeacher();
                request.setAttribute("teacherList", teacherList);
                request.getRequestDispatcher("admin/ViewDetail_Admin.jsp").forward(request, response);

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
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Admin")) {
                response.sendRedirect("viewDetail");
            } else {
                response.sendRedirect("login");
            }
        } else {
            response.sendRedirect("login");
        }

    }

}
