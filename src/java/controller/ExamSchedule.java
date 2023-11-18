/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.QuizDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import model.Quiz;
import utils.DateTimeHelper;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ExamSchedule", urlPatterns = {"/exam-schedule"})
public class ExamSchedule extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ExamSchedule</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExamSchedule at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Student")) {
                int student_id = (int) request.getSession().getAttribute("student_id");
                QuizDAO qDao = new QuizDAO();

                Timestamp from = null;
                Timestamp to = null;
                if (request.getParameter("fromdate") == null) {
                    Date today = new Date();
                    Date to1 = DateTimeHelper.addDays(today, 6);
                    from = DateTimeHelper.toTimestampSql(today);//Chuyen ngay giua javautil voi javasql
                    to = DateTimeHelper.toTimestampSql(to1);
                }
                if (request.getParameter("fromdate") != null) {
                    LocalDate date = LocalDate.parse(request.getParameter("fromdate"));
                    from = Timestamp.valueOf(date.atStartOfDay());
                    Date from1 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date to2 = DateTimeHelper.addDays(from1, 6);
                    to = DateTimeHelper.toTimestampSql(to2);
                }
                ArrayList<java.sql.Timestamp> dates = DateTimeHelper.getDatesList(from, to);
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                request.setAttribute("today", currentTimestamp);
                request.setAttribute("from", new java.sql.Date(DateTimeHelper.toDateUtil(from).getTime()));
                request.setAttribute("dates", dates);

                ArrayList<Quiz> quizList = qDao.getStudentExamSchedule(student_id);
                request.setAttribute("schedule", quizList);
                request.getRequestDispatcher("student/Exam_Schedule.jsp").forward(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
