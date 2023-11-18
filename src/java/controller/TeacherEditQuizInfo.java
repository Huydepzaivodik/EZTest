/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ChoiceDAO;
import dal.QuestionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Choice;
import model.Quiz_Question;

@WebServlet(name = "TeacherEditQuizInfo", urlPatterns = {"/editquizinfo"})
public class TeacherEditQuizInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("mod").equals("deleteChoice")) {
            int classId1 = Integer.parseInt(request.getParameter("class_id"));
            int quizId1 = Integer.parseInt(request.getParameter("quiz_id"));
            String quizName1 = request.getParameter("quiz_name");
            int questionId1 = Integer.parseInt(request.getParameter("question_id"));
            int choiceId = Integer.parseInt(request.getParameter("choice_id"));
            ChoiceDAO cDao = new ChoiceDAO();
            cDao.deleteChoice(choiceId);
            response.sendRedirect("update-delete-quiz?class_id=" + classId1 + "&quiz_id=" + quizId1 + "&quiz_name=" + quizName1 + "&question_id=" + questionId1 + "&mod=1");

        } else if (request.getParameter("mod").equals("updateQues")) {

            int classId = Integer.parseInt(request.getParameter("classId"));
            int quizId = Integer.parseInt(request.getParameter("quizId"));
            String quizName = request.getParameter("quizName");
            int questionId = Integer.parseInt(request.getParameter("questionId"));

            String questionContent = request.getParameter("questionContent");
            String explanation = request.getParameter("explanation");
            String questionType = request.getParameter("questionType");

            Quiz_Question qq = new Quiz_Question();
            qq.setQuestion_id(questionId);
            qq.setQuestion_text(questionContent);
            qq.setExplanation(explanation);
            qq.setQuestion_type(questionType.equals("single"));
            qq.setIs_visible(true);

            ArrayList<Choice> choices = new ArrayList<>();
            int index = 0;
            String answerContent;
            int weight = 0;
            while (true) {

                answerContent = request.getParameter("answerContent_" + index);
                if (answerContent == null) {
                    break;
                }

                String isCorrect = request.getParameter("isCorrect_" + index);
                boolean isCorrect1 = true;

                Choice choice = new Choice();
                choice.setChoice_text(answerContent);

                if (questionType.equals("single")) {
                    if (isCorrect != null) {
                        isCorrect1 = true;
                        weight = 100;
                    } else {
                        isCorrect1 = false;
                        weight = -100;
                    }
                }
                if (questionType.equals("multiple")) {
                    if (isCorrect != null) {
                        isCorrect1 = true;
                        weight = Integer.parseInt(request.getParameter("weight_" + index));
                    } else {
                        isCorrect1 = false;
                        weight = -100;
                    }
                }

                choice.setWeight(weight);
                choice.setIs_correct(isCorrect1);
                choices.add(choice);
                index++;
                response.getWriter().print(index);
            }
            QuestionDAO questionDAO = new QuestionDAO();
            questionDAO.updateQuestion(qq, choices);
            response.sendRedirect("quiz-info?class_id=" + classId + "&quiz_id=" + quizId + "&quiz_name=" + quizName +"&mod=1");
        }
    }



@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
