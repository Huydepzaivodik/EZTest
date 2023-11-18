package controller;

import dal.ClassDAO;
import dal.CourseDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Class;
import model.Course;

@WebServlet(name = "ViewClassController", urlPatterns = {"/viewClass"})
public class ViewClassController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Admin")) {
                int course_id = Integer.parseInt(request.getParameter("course_id"));
                request.getSession().setAttribute("course_id", course_id);

                ClassDAO cldao = new ClassDAO();
                ArrayList<Class> classList = cldao.getListClass(course_id);
                ArrayList<Class> alterTeacherList = cldao.getListAlterTeacher(course_id);
                request.setAttribute("classList", classList);
                request.setAttribute("alterTeacherList", alterTeacherList);

                CourseDAO cdao = new CourseDAO();
                Course c = cdao.getCourseById(course_id);
                request.getSession().setAttribute("course", c);

                request.getRequestDispatcher("admin/HomeClass_Admin.jsp").forward(request, response);
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
