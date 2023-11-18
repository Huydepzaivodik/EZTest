package controller;

import dal.CourseDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Course;
import model.Major;
import model.Semester;

@WebServlet(name = "CreateCourseController", urlPatterns = {"/create-course"})
public class CreateCourseController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Admin")) {
                request.getRequestDispatcher("admin/Create_Course.jsp").forward(request, response);
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

        String courseName = request.getParameter("course_name");
        request.setAttribute("course_name", courseName);
        int semesterID = Integer.parseInt(request.getParameter("semester"));
        request.setAttribute("semester", semesterID);
        int majorID = Integer.parseInt(request.getParameter("major"));
        request.setAttribute("major", majorID);
        boolean active = Boolean.parseBoolean(request.getParameter("active"));
        request.setAttribute("active", active);

        CourseDAO cDAO = new CourseDAO();
        Semester s = new Semester(semesterID);
        Major m = new Major(majorID);
        Course c = cDAO.getCourseByName(courseName);

        if (c != null && c.getMajor_id().getMajor_id() == majorID) {
            request.setAttribute("error", "Course existed!");
            request.getRequestDispatcher("admin/Create_Course.jsp").forward(request, response);
        } else if (!courseName.matches(noNumberStarted)) {
            request.setAttribute("error", "Course name can not start with a number.");
            request.getRequestDispatcher("admin/Create_Course.jsp").forward(request, response);
        } else if (!courseName.matches(noSpecialChars)) {
            request.setAttribute("error", "Course name can not contain blank and special character.");
            request.getRequestDispatcher("admin/Create_Course.jsp").forward(request, response);
        } else {
            Course cNew = new Course(courseName, s, m, active);
            cDAO.addNewCourse(cNew);
            response.sendRedirect("home"); 
        }
    }

}
