package controller;

import dal.AccountDAO;
import dal.ClassDAO;
import model.Class;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Account;

@WebServlet(name = "ModifyClassController", urlPatterns = {"/modifyClass"})
public class ModifyClassController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Admin")) {
                if (request.getParameter("course_id") != null) {
                    ClassDAO cldao = new ClassDAO();
                    AccountDAO accdao = new AccountDAO();
                    ArrayList<Account> teacherList = accdao.getListTeacher();
                    request.setAttribute("teacherList", teacherList);

                    int course_id = (int) request.getSession().getAttribute("course_id");
                    String class_name = request.getParameter("class_name");
                    Class cl = cldao.getClassByName(course_id, class_name);
                    //request.setAttribute("cl", cl);

                    String clname = cl.getClass_name();
                    request.setAttribute("class_name", clname);
                    int class_id = cl.getClass_id();
                    request.setAttribute("class_id", class_id);
                    int teacher_id = cl.getTeacher_id().getId();
                    request.setAttribute("teacher_id", teacher_id);
                    int alter_teacher_id = cl.getAlter_teacher_id().getId();
                    request.setAttribute("alter_teacher_id", alter_teacher_id);

                    //int alter_teacher_id = cldao.getAlterTeacherByClass(class_name);
                    //request.getSession().setAttribute("alter_teacher_id", alter_teacher_id);
                    request.getRequestDispatcher("admin/Modify_Class.jsp").forward(request, response);
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
        String noSpecialChars = "^[a-zA-Z0-9]*";
        String noNumberStarted = "^[^0-9].*";
        int course_id = (int) request.getSession().getAttribute("course_id");
        String class_name = request.getParameter("class_name");
        request.setAttribute("class_name", class_name);

        int teacher_id;
        try {
            teacher_id = Integer.parseInt(request.getParameter("teacher_id"));
        } catch (NumberFormatException e) {
            request.setAttribute("error1", "Teacher ID must be a number.");
            request.getRequestDispatcher("admin/Modify_Class.jsp").forward(request, response);
            return;
        }
        request.setAttribute("teacher_id", teacher_id);

        int class_id = Integer.parseInt(request.getParameter("class_id"));
        request.setAttribute("class_id", class_id);
        int alter_teacher_id = Integer.parseInt(request.getParameter("alter_teacher_id"));
        request.setAttribute("alter_teacher_id", alter_teacher_id);

        ClassDAO clDao = new ClassDAO();
        AccountDAO aDao = new AccountDAO();
        ArrayList<Account> teacherList = aDao.getListTeacher();
        Class cl = clDao.getClassByName(course_id, class_name);

        String mes1 = null;
        if (cl != null && cl.getClass_id() != class_id) {
            mes1 = "Class name existed!";
            request.setAttribute("error", mes1);
        }
        if (!class_name.matches(noNumberStarted)) {
            mes1 = "Class name can't start with a number.";
            request.setAttribute("error", mes1);
        }
        if (!class_name.matches(noSpecialChars)) {
            mes1 = "Class name can't contain blank or any special character.";
            request.setAttribute("error", mes1);
        }

        String mes2 = null;
        if (aDao.getTeacherById(teacher_id) == null) {
            mes2 = "Teacher doesn't exist!";
            request.setAttribute("error1", mes2);
        }
        if (teacher_id == alter_teacher_id) {
            mes2 = "Teacher and 2nd teacher can't be the same";
            request.setAttribute("error1", mes2);
        }

        if (mes1 != null || mes2 != null) {
            request.setAttribute("teacherList", teacherList);
            request.getRequestDispatcher("admin/Modify_Class.jsp").forward(request, response);
        } else {
            clDao.updateClass(class_id, course_id, class_name, teacher_id);
            response.sendRedirect("viewClass?course_id=" + course_id);
        }
    }
}
