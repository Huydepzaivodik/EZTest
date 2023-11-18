/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ClassDAO;
import dal.ExcelDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;

/**
 *
 * @author admin
 */
@WebServlet(name = "ExportStudentExcelController", urlPatterns = {"/ExportStudentExcelController"})
public class ExportStudentExcelController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ExcelDAO export = new ExcelDAO();
        ClassDAO clDAO = new ClassDAO();
        int class_id = (int) request.getSession().getAttribute("class_id");
        String class_name = clDAO.getClassById(class_id).getClass_name();
        String filePath = export.exportStudentList(class_id, class_name);
        String fileName = "student_list_" + class_name + ".xlsx";
        File file = new File(fileName);
        String absolutePath = file.getAbsolutePath();
        filePath = file.getName();

        request.getSession().setAttribute("filePath", filePath);
        response.sendRedirect("viewListStuInClass?class_id=" + class_id + "&export_success=true");
    }

}
