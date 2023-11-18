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
import model.Course;
import model.Class;

@WebServlet(name = "CreateClassController", urlPatterns = {"/create-class"})
public class CreateClassController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Admin")) {
                AccountDAO accdao = new AccountDAO();
                ArrayList<Account> teacherList = accdao.getListTeacher();
                request.setAttribute("teacherList", teacherList);
                request.getRequestDispatcher("admin/Create_Class.jsp").forward(request, response);
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

        int courseID = (int) request.getSession().getAttribute("course_id");
        String className = request.getParameter("class_name");
        request.setAttribute("class_name", className);
        int teacherID = Integer.parseInt(request.getParameter("teacher_id"));
        request.setAttribute("teacher_id", teacherID);
       
        ClassDAO clDao = new ClassDAO();
        AccountDAO aDao = new AccountDAO();
        ArrayList<Account> teacherList = aDao.getListTeacher();
        Class class_name = clDao.getClassByName(courseID, className);

        if (class_name == null) {
            if (aDao.getTeacherById(teacherID) == null) {
                request.setAttribute("error1", "Teacher doesn't exist!");
                request.setAttribute("teacherList", teacherList);
                request.getRequestDispatcher("admin/Create_Class.jsp").forward(request, response);
            } else if (!className.matches(noNumberStarted)) {
                request.setAttribute("error", "Class name can not start with a number.");
                request.setAttribute("teacherList", teacherList);
                request.getRequestDispatcher("admin/Create_Class.jsp").forward(request, response);
            } else if (!className.matches(noSpecialChars)) {
                request.setAttribute("teacherList", teacherList);
                request.setAttribute("error", "Class name can not contain blank and special character.");
                request.getRequestDispatcher("admin/Create_Class.jsp").forward(request, response);
            } else {
                Course c = new Course();
                Class cl = new Class();
                Account a = new Account();

                c.setCourse_id(courseID);
                cl.setCourse_id(c);
                cl.setClass_name(className);
                a.setId(teacherID);
                cl.setTeacher_id(a);
                clDao.addNewClass(cl);
                response.sendRedirect("viewClass?course_id=" + courseID);
            }
        } else {
            request.setAttribute("error", "Class existed in this course.");
            request.setAttribute("teacherList", teacherList);
            request.getRequestDispatcher("admin/Create_Class.jsp").forward(request, response);
        }

    }

}
