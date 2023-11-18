package controller;

import dal.CourseDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Account;
import model.Course;

@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            String role = (String) request.getSession().getAttribute("role");
            CourseDAO courseDAO = new CourseDAO();
            ArrayList<Course> courseList = new ArrayList<>();

            switch (role) {
                case "Admin":
                    courseList = courseDAO.getListCourse();
                    request.setAttribute("courseList", courseList);
                    request.getRequestDispatcher("admin/Home_Admin.jsp").forward(request, response);
                    break;
                case "Student":
                    int studentId = (int) request.getSession().getAttribute("student_id");
                    courseList = courseDAO.getListCourseByStudent(studentId);
                    request.setAttribute("courseList", courseList);
                    request.getRequestDispatcher("student/Home_Student.jsp").forward(request, response);
                    break;
                case "Teacher":
                    Account ac = new Account();
                    int teacherId = (int) request.getSession().getAttribute("teacher_id");
                    // Nếu role_id của người dùng là 2 (giáo viên), lấy teacher_id làm alter_teacher_id
                    // Lưu alterTeacherId vào phiên (session)
                    request.getSession().setAttribute("alter_teacher_id", teacherId);
                    courseList = courseDAO.getListCourseByTeacher(teacherId, teacherId);
                    request.setAttribute("courseList", courseList);
                    request.getRequestDispatcher("teacher/Home_Teacher.jsp").forward(request, response);
                    break;
                default:
                    response.sendRedirect("login");
                    break;
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
