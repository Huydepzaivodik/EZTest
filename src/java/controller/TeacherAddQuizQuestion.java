package controller;

import dal.CourseDAO;
import dal.QuestionDAO;
import dal.QuizDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;
import model.Choice;
import model.Quiz;
import model.Class;
import model.Course;
import model.Quiz_Question;

@MultipartConfig()
@WebServlet(name = "TeacherAddQuizQuestion", urlPatterns = {"/add-quiz-question"})
public class TeacherAddQuizQuestion extends HttpServlet {

    public static final String UPLOAD_DIR = "images";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Teacher")) {
                if (request.getParameter("mod") != null && request.getParameter("mod").equals("addQues")) {

                    int classId = Integer.parseInt(request.getParameter("classId"));

                    String quizName = request.getParameter("quizName");
                    QuizDAO q = new QuizDAO();
                    Quiz quiz1 = q.getQuizByName(quizName, classId);
                    int quizId = quiz1.getQuiz_id();

                    CourseDAO cDAO = new CourseDAO();

                    int courseId = cDAO.getCourseIdByClassId(classId);

                    String questionContent = request.getParameter("questionContent");
                    String explanation = request.getParameter("explanation");
                    String questionType = request.getParameter("questionType");

                    request.getSession().setAttribute("class_id", classId);
                    request.getSession().setAttribute("quiz_id", quizId);
                    request.getSession().setAttribute("quiz_name", quizName);

                    // Tạo đối tượng Quiz_Question
                    Class c = new Class(classId);
                    Quiz quiz = new Quiz(c, quizName);//classid va ten
                    Quiz q1 = new Quiz(c);

                    Quiz_Question quizQuestion = new Quiz_Question();
                    quizQuestion.setQuiz_id(q1);
                    quizQuestion.setQuestion_text(questionContent);
                    quizQuestion.setExplanation(explanation);
                    quizQuestion.setQuestion_type(questionType.equals("single"));
                    quizQuestion.setIs_visible(true);

                    ArrayList<Choice> choices = new ArrayList<>();
                    int index = 0;
                    String answerContent;
                    int weight = 0;
                    while (true) {
                        index++;
                        answerContent = request.getParameter("answerContent" + index);
                        if (answerContent == null) {
                            break;
                        }

                        String isCorrect = request.getParameter("isCorrect" + index);
                        boolean isCorrect1 = true;

                        Choice choice = new Choice();
                        choice.setChoice_text(answerContent);
                        choice.setIs_correct(isCorrect1);

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
                                weight = Integer.parseInt(request.getParameter("weight" + index));
                            } else {
                                isCorrect1 = false;
                                weight = -100;
                            }
                        }
                        choice.setWeight(weight);
                        choice.setIs_visible(true);
                        choice.setIs_correct(isCorrect1);
                        choices.add(choice);
                    }

                    QuestionDAO questionDAO = new QuestionDAO();
                    questionDAO.addQuestion(quiz, quizQuestion, choices);
                    //add question bank nhưng ko add đc choice bank
                    System.out.println(courseId);
                    System.out.println(quizQuestion);
                    System.out.println(choices);

                    questionDAO.addQuestionToQuestionBank(courseId, quizQuestion, choices);
                    response.sendRedirect("quiz-info?class_id=" + classId + "&quiz_id=" + quizId + "&quiz_name=" + quizName + "&mod=1");
                } else {
                    request.getRequestDispatcher("teacher/Add_Question_Choice.jsp").forward(request, response);
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
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Teacher")) {
                QuizDAO qDAO = new QuizDAO();
                int classId = Integer.parseInt(request.getParameter("classId"));
                int quizId = Integer.parseInt(request.getParameter("quizId"));
                String quizName = request.getParameter("quizName");
                int quiz_id = qDAO.getQuizID(classId, quizName);

                CourseDAO cDAO = new CourseDAO();

                int courseId = cDAO.getCourseIdByClassId(classId);
                // Lấy dữ liệu từ biểu mẫu JSP
                String questionContent = request.getParameter("questionContent");
                String explanation = request.getParameter("explanation");
                response.getWriter().print(explanation);
                response.getWriter().print(questionContent);
                response.getWriter().print(classId);
                response.getWriter().print(quizName);
                boolean questionType = request.getParameter("questionType").equals("single");

                // Tạo đối tượng Quiz_Question
                Class c = new Class(classId);
                Quiz quiz = new Quiz(c, quizName);//classid va ten
                Quiz q1 = new Quiz(c);

                Quiz_Question quizQuestion = new Quiz_Question();
                quizQuestion.setQuiz_id(q1);
                quizQuestion.setQuestion_text(questionContent);
                quizQuestion.setExplanation(explanation);
                quizQuestion.setQuestion_type(questionType);
                quizQuestion.setIs_visible(true);

                ArrayList<Choice> choices = new ArrayList<>();
                int index = 0;
                String answerContent;
                int weight = 0;
                while (true) {
                    index++;
                    answerContent = request.getParameter("answerContent" + index);
                    if (answerContent == null) {
                        break;
                    }

                    String isCorrect = request.getParameter("isCorrect" + index);
                    boolean isCorrect1 = true;

                    // Tạo một đối tượng Choice mới cho mỗi lựa chọn
                    Choice choice = new Choice();
                    choice.setChoice_text(answerContent);
                    choice.setIs_correct(isCorrect1);

                    // Xử lý trường hợp "single" hoặc "multiple"
                    if (questionType == true) {
                        if (isCorrect != null) {
                            isCorrect1 = true;
                            weight = 100;
                        } else {
                            isCorrect1 = false;
                            weight = -100;
                        }
                    }
                    if (questionType == false) {
                        if (isCorrect != null) {
                            isCorrect1 = true;
                            weight = Integer.parseInt(request.getParameter("weight" + index));
                        } else {
                            isCorrect1 = false;
                            weight = -100;
                        }
                    }

                    // Đặt trọng số và hình ảnh
                    choice.setWeight(weight);
                    choice.setIs_visible(true);
                    choice.setIs_correct(isCorrect1);

                    // Thêm đối tượng Choice mới vào danh sách
                    choices.add(choice);
                }

// Sau khi vòng lặp hoàn tất, bạn có danh sách choices hoàn chỉnh để thêm vào cơ sở dữ liệu
                QuestionDAO questionDAO = new QuestionDAO();
                questionDAO.addQuestion(quiz, quizQuestion, choices);

                System.out.println(courseId);
                System.out.println(quizQuestion);
                System.out.println(choices);
                questionDAO.addQuestionToQuestionBank(courseId, quizQuestion, choices);
                response.sendRedirect("quiz-info?quiz_id=" + quiz_id + "&quiz_name=" + quizName + "&mod=1");
            } else {
                response.sendRedirect("login");
            }
        } else {
            response.sendRedirect("login");
        }

    }

}
