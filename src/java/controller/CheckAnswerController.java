package controller;

import com.google.gson.Gson;
import dal.StudentChoiceDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@WebServlet(name = "CheckAnswerController", urlPatterns = {"/submittedChoice"})
public class CheckAnswerController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StudentChoiceDAO stcDao = new StudentChoiceDAO();
        int currentQuestionId = Integer.parseInt(request.getParameter("questionId"));
        if (request.getParameter("action") != null) {
            int teacherId = Integer.parseInt(request.getParameter("teacherId"));
            int quizId = Integer.parseInt(request.getParameter("quizId"));
            ArrayList<Integer> _submittedAnswerIds = stcDao.getSubmittedChoiceId(teacherId, quizId, currentQuestionId);

            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(
                    new Gson().toJson(_submittedAnswerIds)
            );
        } else {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            int quizId = Integer.parseInt(request.getParameter("quizId"));
            ArrayList<Integer> _submittedAnswerIds = stcDao.getSubmittedChoiceId(studentId, quizId, currentQuestionId);
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(
                    new Gson().toJson(_submittedAnswerIds)
            );
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
