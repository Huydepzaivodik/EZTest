package controller;

import dal.AccountDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Account;

@WebServlet(name = "AdminAddAccount", urlPatterns = {"/addaccount"})
public class AdminAddAccount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null){
            if (request.getSession().getAttribute("role").equals("Admin")) {
                String r_pass = generateRandomPass(6);
                request.getSession().setAttribute("r_pass", r_pass);
                request.getRequestDispatcher("admin/AddAccount_Admin.jsp").forward(request, response);
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

        response.setContentType("text/html;charset=UTF-8");

        String r_name = request.getParameter("name");
        request.setAttribute("r_name", r_name);

        Date r_dob = Date.valueOf(request.getParameter("dob"));
        request.setAttribute("r_dob", r_dob);

        boolean r_gender = (request.getParameter("gender")).equals("male");
        request.setAttribute("r_gender", request.getParameter("gender"));

        String r_phone = request.getParameter("phone");
        request.setAttribute("r_phone", r_phone);

        String r_address = request.getParameter("address");
        request.setAttribute("r_address", r_address);
        
        String r_email = request.getParameter("pre_email");
        request.setAttribute("r_email", r_email);
        
        String sub_mail = request.getParameter("subEmail");
        request.setAttribute("sub_mail", sub_mail);
        
        String email = r_email + sub_mail;

        String r_pass = request.getParameter("password");

        int r_role = Integer.parseInt(request.getParameter("role"));
        request.setAttribute("r_role", r_role);

        //Validate
        String mes1 = isValidFullName(r_name.trim());
        request.setAttribute("errorMessage1", mes1);

        String mes2 = isOver18YearsOld(r_dob);
        request.setAttribute("errorMessage2", mes2);

        String mes3 = isValidPhone(r_phone);
        request.setAttribute("errorMessage3", mes3);

        //Validate email k duoc trung voi email trong database
        String mes4 = isValidEmail(r_email, email);
        request.setAttribute("errorMessage4", mes4);


        //Sinh random password
        if (mes1 != null || mes2 != null || mes3 != null || mes4 != null) {
            request.getRequestDispatcher("admin/AddAccount_Admin.jsp").forward(request, response);
        } else {
            if (r_email != null || !r_email.equals("")) {
                Account a = new Account();
                AccountDAO aDAO = new AccountDAO();
                a.setName(r_name);
                a.setDob(r_dob);
                a.setGender(r_gender);
                a.setPhone("0" + r_phone);
                a.setAddress(r_address);
                a.setEmail(r_email + sub_mail);
                a.setPassword(r_pass);
                a.setRole_id(r_role);
                aDAO.addNewAccount(a);

                String to = r_email + sub_mail;
                response.getWriter().print(to);

                // Lấy đối tượng ServletContext
                ServletContext context = getServletContext();

                // Lấy đường dẫn gốc (context path) của ứng dụng web
                String contextPath = context.getContextPath();
                String path = contextPath + "/login";//Lay duong dan de confirm info

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

                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(r_email));// change accordingly
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    message.setSubject("Hello " + r_name);
                    String emailContent = "Welcome to EzTest system!\n";
                    emailContent += "\n";
                    emailContent += "This is your account: \n";
                    emailContent += "Username: " + to;
                    emailContent += "\n";
                    emailContent += "Password: " + r_pass;
                    message.setText(emailContent);

                    // send message
                    Transport.send(message);
                    response.sendRedirect("viewDetail");
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private String isValidFullName(String fullName) {
        String mes1 = null;
        String regex = "^[\\p{L}\\s]+$";
        if (!fullName.matches(regex) || fullName == null) {
            mes1 = "Invalid full name";
        }
        return mes1;
    }


    private String isOver18YearsOld(Date dob) {
        String mes2 = null;
        if (dob == null || dob.equals("")) {
            mes2 = "Date of birth must not be empty";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dob);
        Calendar now = Calendar.getInstance();
        int age = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
        if (now.get(Calendar.MONTH) < cal.get(Calendar.MONTH)
                || (now.get(Calendar.MONTH) == cal.get(Calendar.MONTH) && now.get(Calendar.DAY_OF_MONTH) < cal.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }
        if (age < 18) {
            mes2 = "Must be greater than 18 years old";
        }
        return mes2;
    }

    private String isValidPhone(String phoneNumber) {
        String regex = "^\\d{9}$";
        String mes3 = null;
        AccountDAO aDAO = new AccountDAO();
        String a = aDAO.getPhone(phoneNumber);
        if (a != null) {
            mes3 = "Existed Phone Number";
        }
        if (!phoneNumber.matches(regex) || phoneNumber == null) {
            mes3 = "Invalid phone number";
        }
        return mes3;
    }

    private String isValidEmail(String email, String fullEmail) {
        String EMAIL_REGEX = "^(?!\\\\.)[a-zA-Z0-9_.]+(?!\\\\.)$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        String mes4 = null;
        String regex = "\\s"; // Biểu thức chính quy kiểm tra dấu cách
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        Matcher matcher1 = EMAIL_PATTERN.matcher(email);
        AccountDAO adao = new AccountDAO();
        Account a = adao.getUsername(fullEmail);

        if (a != null) {
            mes4 = "Existed Email";
        }

        if (matcher.find() || email.startsWith(".") || email.endsWith(".") || email.contains("..") || email.startsWith("_") || email.endsWith("_") || email.contains("__") || email.equalsIgnoreCase("webmaster") || email.equalsIgnoreCase("admin") || !matcher1.matches()) {
            mes4 = "Invalid email";
        }
        return mes4;
    }

    private String generateRandomPass(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }
        return randomString.toString();
    }

}
