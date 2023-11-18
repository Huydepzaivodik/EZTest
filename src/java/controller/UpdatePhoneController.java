package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

@WebServlet(name = "UpdatePhoneController", urlPatterns = {"/updatePhone"})
public class UpdatePhoneController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AccountDAO dao = new AccountDAO();
        String phone = request.getParameter("phone");
        int id = Integer.parseInt(request.getParameter("id"));
        dao.updatePhone(id, phone);
        if (request.getParameter("role").equals("Student")) {
            Account acc = dao.getStudentById(id);

            // Invalidate the session to log the user out
            HttpSession session = request.getSession();
            session.invalidate();

            //Set session again
            request.getSession().setAttribute("account", acc);
            request.getSession().setAttribute("role", "Student");
            request.getSession().setAttribute("student_id", acc.getId());
            response.sendRedirect("profileStu");
        } else if (request.getParameter("role").equals("Teacher")) {
            Account acc = dao.getTeacherById(id);

            // Invalidate the session to log the user out
            HttpSession session = request.getSession();
            session.invalidate();

            //Set session again
            request.getSession().setAttribute("account", acc);
            request.getSession().setAttribute("role", "Teacher");
            request.getSession().setAttribute("teacher_id", acc.getId());
            response.sendRedirect("profileTeacher");
        } else if (request.getParameter("role").equals("Admin")) {
            Account acc = dao.getAdminById(id);

            // Invalidate the session to log the user out
            HttpSession session = request.getSession();
            session.invalidate();

            //Set session again
            request.getSession().setAttribute("account", acc);
            request.getSession().setAttribute("role", "Admin");
            request.getSession().setAttribute("admin_id", acc.getId());
            response.sendRedirect("profileAdmin");
        }
    }
}
