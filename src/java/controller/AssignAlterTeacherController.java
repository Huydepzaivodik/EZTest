package controller;

import dal.AccountDAO;
import model.Class;
import dal.ClassDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Account;

@WebServlet(name = "AssignAlterTeacherController", urlPatterns = {"/assign-alter-teacher"})
public class AssignAlterTeacherController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Admin")) {
                if (request.getParameter("class_id") != null) {
                    ClassDAO cldao = new ClassDAO();
                    AccountDAO accdao = new AccountDAO();
                    ArrayList<Account> teacherList = accdao.getListTeacher();
                    request.setAttribute("teacherList", teacherList);

                    int course_id = (int) request.getSession().getAttribute("course_id");
                    System.out.println(course_id);
                    String class_name = request.getParameter("class_name");
                    System.out.println(class_name);
                    Class cl = cldao.getClassByName(course_id, class_name);

                    int class_id = cl.getClass_id();
                    request.setAttribute("class_id", class_id);
                    
                    int teacher_id = cl.getTeacher_id().getId();
                    request.setAttribute("teacher_id", teacher_id);
                    
                    int alter_teacher_id = cl.getAlter_teacher_id().getId();
                    request.setAttribute("alter_teacher_id", alter_teacher_id);

//                    int alter_teacher_id = cldao.getAlterTeacherByClass(class_name);
//                    request.getSession().setAttribute("cl", cl);
//                    request.getSession().setAttribute("alter_teacher_id", alter_teacher_id);
                    request.getRequestDispatcher("admin/Assign_Alter_Teacher.jsp").forward(request, response);
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
        AccountDAO accdao = new AccountDAO();
        ArrayList<Account> teacherList = accdao.getListTeacher();

        int course_id = (int) request.getSession().getAttribute("course_id");
        int class_id = Integer.parseInt(request.getParameter("class_id"));
        request.setAttribute("class_id", class_id);
        int alter_teacher_id = Integer.parseInt(request.getParameter("alter_teacher_id"));
        request.setAttribute("alter_teacher_id", alter_teacher_id);
        int teacher_id = Integer.parseInt(request.getParameter("teacher_id"));
        request.setAttribute("teacher_id", teacher_id);

        if (teacher_id == alter_teacher_id) {
            request.setAttribute("error", "Teacher and 2nd teacher can't be the same.");
            request.setAttribute("teacherList", teacherList);
            request.getRequestDispatcher("admin/Assign_Alter_Teacher.jsp").forward(request, response);
        } else {
            ClassDAO clDAO = new ClassDAO();
            clDAO.updateAlter_teacher_id(alter_teacher_id, class_id);
            response.sendRedirect("viewClass?course_id=" + course_id);
        }

    }

}
