package controller;

import dal.AccountDAO;
import dal.ClassDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Account;
import model.Student_Class;

@WebServlet(name = "AssignStudentController", urlPatterns = {"/assign-student"})
public class AssignStudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Admin")) {
                int class_id = Integer.parseInt(request.getParameter("class_id"));
                int student_id = Integer.parseInt(request.getParameter("student_id"));
                ClassDAO clDao = new ClassDAO();
                Student_Class scl = clDao.getStudentClassById(class_id, student_id);
                if (scl == null) {
                    clDao.assignStudentInClass(class_id, student_id);
                    response.sendRedirect("viewListStuInClass?class_id=" + class_id);
                } else {
                    AccountDAO aDao = new AccountDAO();
                    ArrayList<Account> stdList = aDao.getListStudent();
                    request.setAttribute("stdList", stdList);
                    request.setAttribute("error", "This student is existed in this class!");
                    request.getRequestDispatcher("admin/ListStudentToAssign.jsp").forward(request, response);
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
