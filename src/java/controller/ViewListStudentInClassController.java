package controller;

import dal.ClassDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Account;

@WebServlet(name = "ViewListStudentInClassController", urlPatterns = {"/viewListStuInClass"})
public class ViewListStudentInClassController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Object role = request.getSession().getAttribute("role");
        if (request.getSession().getAttribute("account") != null && role != null) {
            int class_id = Integer.parseInt(request.getParameter("class_id"));
            request.getSession().setAttribute("class_id", class_id);
            ClassDAO clDao = new ClassDAO();
            ArrayList<Account> stdList = clDao.getListStudentInClass(class_id);
            request.setAttribute("stdList", stdList);

            String forwardPath;
            if ("Admin".equals(role)) {
                forwardPath = "admin/ViewListStudentInClass.jsp";
            } else if ("Teacher".equals(role)) {
                forwardPath = "teacher/ViewListStudentInClass_Teacher.jsp";
            } else if ("Student".equals(role)) {
                forwardPath = "student/ViewListStudentInClass_Student.jsp";
            } else {
                forwardPath = "login";
            }

            request.getRequestDispatcher(forwardPath).forward(request, response);
        } else {
            response.sendRedirect("login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
