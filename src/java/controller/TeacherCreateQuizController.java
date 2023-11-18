package controller;

import dal.AccountDAO;
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
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Account;
import model.Quiz;
import utils.DateTimeHelper;
import model.Class;

@WebServlet(name = "TeacherCreateQuizController", urlPatterns = {"/create-quiz"})
public class TeacherCreateQuizController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Teacher")) {
                request.getRequestDispatcher("teacher/Create_Quiz.jsp").forward(request, response);
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
                int class_id = (int) request.getSession().getAttribute("class_id");

                String quizName = (String) request.getSession().getAttribute("quiz_name");

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
                int createdByTeacherID = (int) request.getSession().getAttribute("teacher_id");

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
                    mes2 = "The due date must be at least later than the start time";
                    request.setAttribute("errorMessage2", mes2);
                }
                if (due_date.compareTo(endTime) < 1) {
                    mes2 = "The due date must include the time the test was taken";
                    request.setAttribute("errorMessage2", mes2);
                }

                boolean isVisible = (request.getParameter("is_visible")).equals("yes");
                request.setAttribute("is_visible", request.getParameter("is_visible"));

                boolean isDisplayDetail = (request.getParameter("detail")).equals("yes");
                request.setAttribute("dis_detail", request.getParameter("detail"));

                String create_Date = (String) request.getSession().getAttribute("create_date");
                Timestamp createDate = Timestamp.valueOf(create_Date);

                if (mes1 != null || mes2 != null || mes4 != null) {
                    request.getRequestDispatcher("teacher/Create_Quiz.jsp").forward(request, response);
                } else {

                    Quiz quiz = new Quiz();
                    QuizDAO qDAO = new QuizDAO();
                    Class cl = new Class();
                    quiz.setQuiz_name(quizName);
                    cl.setClass_id(class_id);
                    quiz.setClass_id(cl);

                    quiz.setStart_time(startTime);
                    quiz.setDuration(duration);
                    quiz.setDate_end(due_date);

                    quiz.setIs_displaydetail(isDisplayDetail);
                    quiz.setCreate_date(createDate);
                    quiz.setIs_visible(isVisible);
                    quiz.setCreated_by(createdByTeacherID);
                    qDAO.addQuiz(quiz);

                    if (isVisible == true) {
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
                        ArrayList<Account> listStudentInQuiz = accDAO.getListMailtByClassId(class_id);
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
                                    String emailContent = "Your have a new quiz. Please check your exam schedule for more details.\n";
                                    emailContent += "\n";
                                    emailContent += "Your quiz \n";
                                    emailContent += "Quizname: " + quizName;
                                    emailContent += "\nStart Time: " + startTime;
                                    emailContent += "\nDue Time: " + due_date;
                                    message.setText(emailContent);

                                    // send message
                                    Transport.send(message);
                                } catch (MessagingException e) {
                                    throw new RuntimeException("Error sending email to " + email, e);
                                }
                            }
                        }
                    }
                    int quizID = qDAO.getQuizID(quiz);
                    request.getSession().setAttribute("quiz_id", quizID);
                    request.getSession().setAttribute("quiz_name_real", quizName);

                    response.sendRedirect("add-quiz-question");

                }
            } else {
                response.sendRedirect("login");
            }
        } else {
            response.sendRedirect("login");
        }

    }

    private String isValidDuration(int duration) {
        String mes2 = null;
        if (duration < 0) {
            mes2 = "Time duration must be a positive integer number";
        }
        return mes2;
    }
}
