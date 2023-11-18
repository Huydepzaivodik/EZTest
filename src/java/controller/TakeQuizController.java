package controller;

import com.google.gson.Gson;
import dal.ChoiceDAO;
import dal.QuestionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Choice;
import model.Quiz_Question;
import model.SelectedChoices;
import org.json.JSONObject;

@WebServlet(name = "TakeQuizController", urlPatterns = {"/attempt"})
public class TakeQuizController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int quizId = Integer.parseInt(request.getParameter("quizId"));
        int questionIndex = Integer.parseInt(request.getParameter("questionIndex"));

        QuestionDAO qDao = new QuestionDAO();
        Quiz_Question qq = qDao.getQuestionAtIndex(quizId, questionIndex);
        int questionId = qq.getQuestion_id();

        ChoiceDAO chDao = new ChoiceDAO();
        ArrayList<Choice> choiceList = chDao.getChoiceListByQuestionId(questionId);
        JSONObject json = new JSONObject();
        json.put("question_text", qq.getQuestion_text());
        json.put("explanation", qq.getExplanation());
        json.put("question_id", qq.getQuestion_id());
        json.put("question_type", qq.isQuestion_type());
        json.put("choiceListOfQuestion", choiceList);
        response.getWriter().write(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String selectedChoicesJSON = request.getParameter("selectedChoicesJSON");
        Gson gson = new Gson();
        SelectedChoices selectedChoices = gson.fromJson(selectedChoicesJSON, SelectedChoices.class);

        if (request.getParameter("action") != null) {
            int teacherId = selectedChoices.getTeacherId();
            int questionId = selectedChoices.getQuestionId();
            List<Integer> selectedAnswerIds = selectedChoices.getSelectedAnswerIds();
            int quizId = selectedChoices.getQuizId();
            QuestionDAO qdao = new QuestionDAO();
            boolean hasSelectedChoices = qdao.hasSelectedChoices(teacherId, questionId, quizId);

            if (hasSelectedChoices == true) {
                // Giao vien đã chọn câu trả lời, cập nhật lại lựa chọn
                qdao.updateSelectedChoices(teacherId, selectedAnswerIds, questionId, quizId);
            } else {
                // giao vien chưa chọn câu trả lời, thêm mới
                qdao.submitCurrentChoices(teacherId, selectedAnswerIds, questionId, quizId);
            }
        } else {
            int studentId = selectedChoices.getStudentId();
            int questionId = selectedChoices.getQuestionId();
            List<Integer> selectedAnswerIds = selectedChoices.getSelectedAnswerIds();
            int quizId = selectedChoices.getQuizId();
            QuestionDAO qdao = new QuestionDAO();
            boolean hasSelectedChoices = qdao.hasSelectedChoices(studentId, questionId, quizId);

            if (hasSelectedChoices == true) {
                // Học sinh đã chọn câu trả lời, cập nhật lại lựa chọn
                qdao.updateSelectedChoices(studentId, selectedAnswerIds, questionId, quizId);
            } else {
                // Học sinh chưa chọn câu trả lời, thêm mới
                qdao.submitCurrentChoices(studentId, selectedAnswerIds, questionId, quizId);
            }
        }

    }
}
