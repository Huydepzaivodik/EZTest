package controller;

import dal.AccountDAO;
import dal.ClassDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import model.Class;
import java.util.ArrayList;

@WebServlet(name = "DeleteTeacherAccountController", urlPatterns = {"/delete-teacher"})
public class DeleteTeacherAccountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Admin")) {
                ClassDAO clDAO = new ClassDAO();
                int id = Integer.parseInt(request.getParameter("id"));
                ArrayList<Class> classList = clDAO.selectListClassByTeacherID(id);
                boolean canDelete = true;
                for (Class cl : classList) {
                    int teacher_id = cl.getTeacher_id().getId();
                    int alter_teacher_id = cl.getAlter_teacher_id().getId();
                    if (teacher_id != 0 && alter_teacher_id == 0) {
                        canDelete = false;
                        break;
                    }
                }
                if (canDelete) {
                    AccountDAO accDAO = new AccountDAO();
                    accDAO.deleteAccountTeacher(id);
                    response.sendRedirect("viewDetail");
                } else {
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Can not delete this Teacher account if class does not have Alter teacher to replaced.');");
                    out.println("window.location='viewDetail';");
                    out.println("</script>");
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
