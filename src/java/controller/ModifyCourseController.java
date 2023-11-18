package controller;

import dal.CourseDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Course;

@WebServlet(name = "ModifyCourseController", urlPatterns = {"/modifyCourse"})
public class ModifyCourseController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Admin")) {
                if (request.getParameter("course_id") != null) {
                    int course_id = Integer.parseInt(request.getParameter("course_id"));
                    CourseDAO cdao = new CourseDAO();
                    Course c = cdao.getCourseById(course_id);
                    int major = c.getMajor_id().getMajor_id();
                    request.setAttribute("major", major);
                    int semester = c.getSemester_id().getSemester_id();
                    request.setAttribute("semester", semester);
                    request.getSession().setAttribute("course", c);
                    request.getRequestDispatcher("admin/Modify_Course.jsp").forward(request, response);
                } else {
                    response.sendRedirect("login");
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
        String noSpecialChars = "^[a-zA-Z0-9]*$";
        String noNumberStarted = "^[^0-9].*";

        String course_name = request.getParameter("course_name");
        request.setAttribute("course_name", course_name);
        int semester_id = Integer.parseInt(request.getParameter("semester"));
        request.setAttribute("semester", semester_id);
        int major_id = Integer.parseInt(request.getParameter("major"));
        request.setAttribute("major", major_id);
        int course_id = Integer.parseInt(request.getParameter("course_id"));

        CourseDAO cDAO = new CourseDAO();
        Course c = cDAO.getCourseByName(course_name);

        if (c != null && c.getCourse_id() != course_id && c.getMajor_id().getMajor_id() == major_id) {
            request.setAttribute("error", "Course existed!");
            request.getRequestDispatcher("admin/Modify_Course.jsp").forward(request, response);
        } else if (!course_name.matches(noNumberStarted)) {
            request.setAttribute("error", "Course name cannot start with a number.");
            request.getRequestDispatcher("admin/Modify_Course.jsp").forward(request, response);
        } else if (!course_name.matches(noSpecialChars)) {
            request.setAttribute("error", "Course name cannot contain blank and special characters.");
            request.getRequestDispatcher("admin/Modify_Course.jsp").forward(request, response);
        } else {
            cDAO.updateCourse(course_id, course_name, semester_id, major_id);
            response.sendRedirect("home");
        }

    }

}
