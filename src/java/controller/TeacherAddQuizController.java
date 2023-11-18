package controller;

import com.google.gson.Gson;
import dal.QuizDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Quiz;

@WebServlet(name = "TeacherAddQuizController", urlPatterns = {"/add-quiz"})
public class TeacherAddQuizController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Teacher")) {
                request.getRequestDispatcher("teacher/ViewQuizDetail.jsp").forward(request, response);
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
        String quiz_name = request.getParameter("quiz_name");
        int class_id = Integer.parseInt(request.getParameter("class_id"));
        String data;

        QuizDAO qDao = new QuizDAO();
        Quiz q = qDao.getQuizByName(quiz_name, class_id);
        if (q == null) {
            LocalDateTime currentTime = LocalDateTime.now();

            // Định dạng thời gian thành chuỗi
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String today = currentTime.format(formatter);
            request.getSession().setAttribute("create_date", today);
            request.getSession().setAttribute("quiz_name", quiz_name);
            data = "create-quiz";
            response.setStatus(200);
        } else {
            data = "This quiz is existed !";
            response.setStatus(406);
        }
        String json = new Gson().toJson(data);
        response.setContentType("text/plain");
        response.getWriter().write(json);

    }
}
