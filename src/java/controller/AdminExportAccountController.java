/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
@WebServlet(name = "AdminExportAccountController", urlPatterns = {"/AdminExportAccountController"})
public class AdminExportAccountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExcelDAO excelDAO = new ExcelDAO();
        String exportStudent = request.getParameter("export-student");
        String exportTeacher = request.getParameter("export-teacher");
        String filePath = null;

        if (exportStudent != null) {
            filePath = excelDAO.exportAllStudentList();
            if (filePath != null) {
                filePath = "all_student_list.xlsx";
                request.getSession().setAttribute("filePath", filePath);
                response.sendRedirect("viewDetail?export_success=true");
            } else {
                response.sendRedirect("viewDetail?export_success=false");
            }
        } else if (exportTeacher != null) {
            filePath = excelDAO.exportAllTeacherList();
            if (filePath != null) {
                filePath = "all_teacher_list.xlsx";
                request.getSession().setAttribute("filePath", filePath);
                response.sendRedirect("viewDetail?export_success=true");
            } else {
                response.sendRedirect("viewDetail?export_success=false");
            }
        } else {
            // Xử lý logic mặc định nếu không phù hợp với bất kỳ trường hợp nào
            response.sendRedirect("viewDetail?export_success=false");
        }
    }
}
