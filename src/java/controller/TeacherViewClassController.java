package controller;

import dal.ClassDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Class;

@WebServlet(name = "TeacherViewClassController", urlPatterns = {"/teacherViewClass"})
public class TeacherViewClassController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Teacher")) {
                ClassDAO cldao = new ClassDAO();
                int course_id = Integer.parseInt(request.getParameter("course_id"));
                request.getSession().setAttribute("course_id", course_id);
                int teacher_id = (int) request.getSession().getAttribute("teacher_id");
                ArrayList<Class> classList = cldao.getListClassByTeacher(course_id, teacher_id, teacher_id);
                request.setAttribute("classList", classList);
                request.getRequestDispatcher("teacher/HomeClass_Teacher.jsp").forward(request, response);
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
