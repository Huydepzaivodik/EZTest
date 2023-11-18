/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.QuestionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "TeacherDoQuizController", urlPatterns = {"/teacherDoQuiz"})
public class TeacherDoQuizController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Teacher")) {
                int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
                float duration = Float.parseFloat(request.getParameter("duration"));
                int teacher_id = Integer.parseInt(request.getParameter("teacher_id"));
                
                QuestionDAO qDao = new QuestionDAO();
                int totalQuestion = qDao.countTotalNumberQuestion(quiz_id);
                
                qDao.deleteAllStudentChoice(teacher_id, quiz_id);
                qDao.deleteTeacherScore(teacher_id, quiz_id);
                
                request.setAttribute("quiz_id", quiz_id);
                request.setAttribute("duration", duration);
                request.setAttribute("totalQuestion", totalQuestion);
                request.getRequestDispatcher("teacher/Take_Quiz_Teacher.jsp").forward(request, response);
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

    }

}
