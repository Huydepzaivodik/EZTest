
package controller;

import dal.QuestionDAO;
import dal.QuizDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import model.Choice;
import model.Quiz;
import model.Quiz_Question;
import utils.DateTimeHelper;
import dal.AccountDAO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Account;

@WebServlet(name = "TeacherUDQuiz", urlPatterns = {"/update-delete-quiz"})
public class TeacherUDQuiz extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Teacher")) {
                int teacher_id = (int) request.getSession().getAttribute("teacher_id");
                int quizId = Integer.parseInt(request.getParameter("quiz_id"));
                request.getSession().setAttribute("quiz_id", quizId);
                String quizName = request.getParameter("quiz_name");
                int questionId = Integer.parseInt(request.getParameter("question_id"));
                QuestionDAO qdao = new QuestionDAO();
                if (request.getParameter("mod").equals("delete")) {
                    qdao.deleteQuestion(questionId,teacher_id);
                    response.sendRedirect("quiz-info?quiz_id=" + quizId + "&quiz_name=" + quizName + "&mod=1");
                } else {
                    Quiz_Question qq = qdao.getQuestionToEdit(questionId);
                    request.setAttribute("qq", qq);
                    ArrayList<Choice> choice = qdao.getChoicesOfQuestion(questionId);
                    request.setAttribute("choice", choice);
                    int classId1 = Integer.parseInt(request.getParameter("class_id"));
                    int quizId1 = Integer.parseInt(request.getParameter("quiz_id"));
                    String quizName1 = request.getParameter("quiz_name");
                    request.setAttribute("class_id", classId1);
                    request.setAttribute("quiz_id", quizId1);
                    request.setAttribute("quiz_name", quizName1);
                    request.setAttribute("question_id", questionId);
                    request.getRequestDispatcher("/teacher/Edit_Quiz_Question.jsp").forward(request, response);
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
                if (request.getParameter("mod").equals("editquiz")) {
                    int quizId = Integer.parseInt(request.getParameter("quiz_id"));

                    String quizName = request.getParameter("quiz_name");

                    String start_time = request.getParameter("start_time");
                    request.setAttribute("start_time", start_time);
                    LocalDateTime localDateTime = LocalDateTime.parse(start_time, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                    Timestamp startTime = Timestamp.valueOf(localDateTime);

                    java.util.Date today = new java.util.Date();

                    String mes1 = null;
                    java.util.Date to = DateTimeHelper.addDays(today, 0);
                    Timestamp from = DateTimeHelper.toTimestampSql(to);
                    if (startTime.compareTo(from) < 1) {
                        mes1 = "Starting time must be at least today";
                        request.setAttribute("errorMessage1", mes1);
                    }

                    String timeUnit = request.getParameter("unit");
                    request.setAttribute("timeUnit", request.getParameter("unit"));

                    int duration = Integer.parseInt(request.getParameter("duration"));
                    request.setAttribute("duration", duration);

                    if (timeUnit.equals("minute")) {
                        request.setAttribute("duration", duration);
                        duration *= 60;
                    }
                    if (timeUnit.equals("second")) {
                        request.setAttribute("duration", duration);
                    }

                    String mes4 = isValidDuration(duration);
                    request.setAttribute("errorMessage4", mes4);

                    // Chuyển startTime thành LocalDateTime
                    LocalDateTime startLocalDateTime = startTime.toLocalDateTime();

                    // Thêm duration phút vào startLocalDateTime
                    LocalDateTime endLocalDateTime = startLocalDateTime.plusSeconds(duration); // Sử dụng plusSeconds thay vì plusMinutes
                    Timestamp endTime = Timestamp.valueOf(endLocalDateTime);

                    String dueDate = request.getParameter("due_date");
                    request.setAttribute("due_date", dueDate);
                    LocalDateTime localDateTime1 = LocalDateTime.parse(dueDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                    Timestamp due_date = Timestamp.valueOf(localDateTime1);

                    String mes2 = null;
                    if (due_date.compareTo(startTime) < 1) {
                        mes2 = "The due date must be after the start time";
                        request.setAttribute("errorMessage2", mes2);
                    }
                    if (due_date.compareTo(endTime) < 1) {
                        mes2 = "The due date must include the time the test was taken";
                        request.setAttribute("errorMessage2", mes2);
                    }

                    String isVisible = request.getParameter("isVisible");
                    boolean isVisible1 = true;
                    request.setAttribute("isVisible", request.getParameter("isVisible"));
                    if (isVisible != null && isVisible.equals("true")) {
                        isVisible1 = Boolean.parseBoolean(isVisible);
                    } else {
                        isVisible1 = false;
                    }

                    String isDisplayDetail = request.getParameter("isDisplayDetail");
                    boolean isDisplayDetail1 = true;
                    request.setAttribute("isDisplayDetail", request.getParameter("isDisplayDetail"));
                    if (isDisplayDetail != null && isDisplayDetail.equals("true")) {
                        isDisplayDetail1 = Boolean.parseBoolean(isDisplayDetail);
                    } else {
                        isDisplayDetail1 = false;
                    }
                    int classId = (int) request.getSession().getAttribute("class_id");

                    String mes3 = isValidQuizName(quizName, quizId, classId);
                    request.setAttribute("errorMessage3", mes3);
                    request.setAttribute("quiz_name", quizName);

                    QuestionDAO qdao = new QuestionDAO();
                    ArrayList<Quiz_Question> qq = qdao.getListQuizQuestion(quizId);
                    request.setAttribute("listQuestion", qq);

                    ArrayList<Choice> choices = qdao.getListChoice(quizId);
                    request.setAttribute("listChoice", choices);

                    if (mes1 != null || mes2 != null || mes3 != null || mes4 != null) {
                        request.getRequestDispatcher("teacher/Edit_Quiz.jsp").forward(request, response);
                    } else {
                        Quiz quiz = new Quiz();
                        QuizDAO qDAO = new QuizDAO();
                        quiz.setQuiz_name(quizName);

                        quiz.setStart_time(startTime);
                        quiz.setDuration(duration);
                        quiz.setDate_end(due_date);

                        quiz.setIs_displaydetail(isDisplayDetail1);
                        quiz.setIs_visible(isVisible1);
                        quiz.setQuiz_id(quizId);

                        boolean flag = qDAO.updateQuizInfo(quiz);
                        if (flag && isVisible1==true) {
                            Properties props = new Properties();
                            props.put("mail.smtp.host", "smtp.gmail.com");
                            props.put("mail.smtp.socketFactory.port", "465");
                            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                            props.put("mail.smtp.auth", "true");
                            props.put("mail.smtp.port", "465");
                            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication("eztestproject@gmail.com", "npsd gefi azgt ppxr");// Put your email
                                }
                            });

                            AccountDAO accDAO = new AccountDAO();
                            //Lấy ra học sinh có quiz đó
                            ArrayList<Account> listStudentInQuiz = accDAO.getListMailtByQuizId(quizId);
                            for (Account account : listStudentInQuiz) {
                                System.out.println(account.getEmail());
                            }
                            for (Account student : listStudentInQuiz) {
                                String email = student.getEmail();
                                if (email != null && !email.isEmpty()) {
                                    String name = student.getName();
                                    MimeMessage message = new MimeMessage(session);
                                    try {
                                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                                        message.setSubject("Hello " + name);
                                        String emailContent = "Your quiz has been updated. Please check your quiz for more details.\n";
                                        emailContent += "\n";
                                        emailContent += "Your quiz \n";
                                        emailContent += "Quizname: " + quizName;
                                        message.setText(emailContent);

                                        // send message
                                        Transport.send(message);
                                    } catch (MessagingException e) {
                                        throw new RuntimeException("Error sending email to " + email, e);
                                    }
                                }
                            }
                            flag = false;
                            request.getSession().setAttribute("quiz_id", quizId);
                            request.getSession().setAttribute("quiz_name", quizName);
                            response.sendRedirect("quiz-info?quiz_id=" + quizId + "&quiz_name=" + quizName + "&mod=1");
                        }else if(flag && isVisible1==false){
                            response.sendRedirect("quiz-info?quiz_id=" + quizId + "&quiz_name=" + quizName + "&mod=1");
                        }
                    }
                }
            } else {
                response.sendRedirect("login");
            }
        }
    }

    private String isValidDuration(int duration) {
        String mes2 = null;
        if (duration < 0) {
            mes2 = "Time duration must be a positive integer number";
        }
        return mes2;
    }

    private String isValidQuizName(String quizName, int quizId, int classId) {
        String mes = null;
        String regex = ".*\\S+.*";
        QuizDAO q = new QuizDAO();
        if (q.getExistedQuizName(quizName, quizId, classId) != null) {
            mes = "Existed Quiz Name";
        }
        if (!quizName.matches(regex)) {
            mes = "Invalid Quiz Name";
        }
        return mes;
    }

}