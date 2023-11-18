/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ClassDAO;
import dal.CourseDAO;
import dal.ExcelDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import utils.ExportExcel;

/**
 *
 * @author admin
 */
@WebServlet(name = "ExportScoreExcelController", urlPatterns = {"/ExportScoreExcelController"})
public class ExportScoreExcelController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ExcelDAO export = new ExcelDAO();
        int class_id = (int) request.getSession().getAttribute("class_id");
        int quiz_id = (int) request.getSession().getAttribute("quiz_id");
        int course_id = (int) request.getSession().getAttribute("course_id");

        CourseDAO cDAO = new CourseDAO();
        ClassDAO clDAO = new ClassDAO();

        String course_name = cDAO.getCourseById(course_id).getCourse_name();
        String class_name = clDAO.getClassById(class_id).getClass_name();

        request.getSession().setAttribute("course_name", course_name);
        request.getSession().setAttribute("class_name", class_name);
        String quiz_name = request.getParameter("quiz_name");

        System.out.println(class_id);
        System.out.println(quiz_id);
        System.out.println(quiz_name);
        System.out.println(course_name);
        System.out.println(class_name);

        String filePath = export.ExportMarkClass(class_id, quiz_id, course_name, class_name, quiz_name);
        String fileName = course_name + "_" + class_name + "_" + quiz_name + "_Score.xlsx";
        File file = new File(fileName);
        String absolutePath = file.getAbsolutePath();
        filePath = file.getName(); // Chỉ lấy tên file từ đường dẫn tuyệt đối
        request.getSession().setAttribute("filePath", filePath);
        response.sendRedirect("quiz-info?quiz_id=" + quiz_id + "&quiz_name=" + quiz_name +"&mod=1"+ "&export_success=true");
    }
}
