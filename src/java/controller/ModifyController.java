package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Account;

@WebServlet(name = "ModifyController", urlPatterns = {"/modify"})
public class ModifyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Admin")) {
                String modParam = request.getParameter("mod");
                if (modParam != null && modParam.equals("1")) {
                    AccountDAO accDao = new AccountDAO();
                    int id = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("id", id);

                    Account student = accDao.getStudentById(id);

                    String email = student.getEmail();
                    request.setAttribute("email", email);

                    String name = student.getName();
                    request.setAttribute("name", name);

                    String address = student.getAddress();
                    request.setAttribute("address", address);

                    boolean gender = student.isGender();
                    request.setAttribute("gender", gender);

                    Date dob = student.getDob();
                    request.setAttribute("dob", dob);

                    String phone = student.getPhone();
                    request.setAttribute("phone", phone);

                    String img = student.getImg();
                    request.getSession().setAttribute("img", img);
                    String email_1 = student.getEmail();

                    String[] preEmail = email_1.split("@");

                    String username = preEmail[0];
                    String domain = "@" + preEmail[1];

                    request.setAttribute("username", username);
                    request.setAttribute("domain", domain);
                    request.setAttribute("role", "Student");
                    request.getRequestDispatcher("admin/Modify_Student.jsp").forward(request, response);
                } else if (modParam != null && modParam.equals("2")) {
                    AccountDAO accDao = new AccountDAO();
                    int id = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("id", id);

                    Account teacher = accDao.getTeacherById(id);

                    String email = teacher.getEmail();
                    request.setAttribute("email", email);

                    String name = teacher.getName();
                    request.setAttribute("name", name);

                    String address = teacher.getAddress();
                    request.setAttribute("address", address);

                    boolean gender = teacher.isGender();
                    request.setAttribute("gender", gender);

                    Date dob = teacher.getDob();
                    request.setAttribute("dob", dob);

                    String phone = teacher.getPhone();
                    request.setAttribute("phone", phone);

                    String img = teacher.getImg();
                    request.getSession().setAttribute("img", img);

                    String email_1 = teacher.getEmail();

                    String[] preEmail = email_1.split("@");

                    String username = preEmail[0];
                    String domain = "@" + preEmail[1];

                    request.setAttribute("username", username);
                    request.setAttribute("domain", domain);
                    request.setAttribute("role", "Teacher");
                    request.getRequestDispatcher("admin/Modify_Teacher.jsp").forward(request, response);
                } else if (modParam != null && modParam.equals("3")) {

                    AccountDAO accDao = new AccountDAO();
                    int id = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("id", id);

                    Account admin = accDao.getAdminById(id);

                    String email = admin.getEmail();
                    request.setAttribute("email", email);

                    String name = admin.getName();
                    request.setAttribute("name", name);

                    String address = admin.getAddress();
                    request.setAttribute("address", address);

                    boolean gender = admin.isGender();
                    request.setAttribute("gender", gender);

                    Date dob = admin.getDob();
                    request.setAttribute("dob", dob);

                    String phone = admin.getPhone();
                    request.setAttribute("phone", phone);

                    String img = admin.getImg();
                    request.getSession().setAttribute("img", img);

                    String email_1 = admin.getEmail();

                    String[] preEmail = email_1.split("@");

                    String username = preEmail[0];
                    String domain = "@" + preEmail[1];

                    request.setAttribute("username", username);
                    request.setAttribute("domain", domain);
                    request.setAttribute("role", "Admin");
                    request.getRequestDispatcher("admin/Profile_Admin.jsp").forward(request, response);
                } else {
                    response.sendRedirect("login");
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
            if (request.getSession().getAttribute("role").equals("Admin")) {
                if (request.getParameter("mod").equals("updateStu")) {

                    int id = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("id", id);
                    request.setAttribute("role", "Student");

                    String r_name = request.getParameter("name");
                    request.setAttribute("name", r_name);

                    Date r_dob = Date.valueOf(request.getParameter("dob"));
                    request.setAttribute("dob", r_dob);

                    boolean r_gender = (request.getParameter("gender")).equals("male");
                    request.setAttribute("gender", r_gender);

                    String r_phone = request.getParameter("phone");
                    request.setAttribute("phone", r_phone);

                    String r_address = request.getParameter("address");
                    request.setAttribute("address", r_address);

                    String r_preEmail = request.getParameter("preEmail");
                    request.setAttribute("username", r_preEmail);

                    String r_subEmail = request.getParameter("subEmail");
                    request.setAttribute("domain", r_subEmail);

                    String r_email = r_preEmail + r_subEmail;
                    request.setAttribute("email", r_email);

                    AccountDAO adao = new AccountDAO();
                    //Validate
                    String mes1 = isValidFullName(r_name.trim());
                    request.setAttribute("errorMessage1", mes1);

                    String mes2 = isOver18YearsOld(r_dob);
                    request.setAttribute("errorMessage2", mes2);

                    String mes3 = isValidPhone(r_phone, id);
                    request.setAttribute("errorMessage3", mes3);

                    //Validate email k duoc trung voi email trong database
                    String mes4 = isValidEmail(r_email, id);
                    request.setAttribute("errorMessage4", mes4);

                    String mes5 = isValidPreEmail(r_preEmail);
                    request.setAttribute("errorMessage5", mes5);

                    if (mes1 != null || mes2 != null || mes3 != null || mes4 != null || mes5 != null) {
                        request.getRequestDispatcher("admin/Modify_Student.jsp").forward(request, response);
                    } else {
                        adao.updateAccount(id, r_email, r_name, r_dob, r_gender, r_phone, r_address);
                        response.sendRedirect("viewDetail");
                    }
                } else if (request.getParameter("mod").equals("updateTea")) {

                    int id = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("id", id);
                    request.setAttribute("role", "Teacher");

                    String r_name = request.getParameter("name");
                    request.setAttribute("name", r_name);

                    Date r_dob = Date.valueOf(request.getParameter("dob"));
                    request.setAttribute("dob", r_dob);

                    boolean r_gender = (request.getParameter("gender")).equals("male");
                    request.setAttribute("gender", r_gender);

                    String r_phone = request.getParameter("phone");
                    request.setAttribute("phone", r_phone);

                    String r_address = request.getParameter("address");
                    request.setAttribute("address", r_address);

                    String r_preEmail = request.getParameter("preEmail");
                    request.setAttribute("username", r_preEmail);

                    String r_subEmail = request.getParameter("subEmail");
                    request.setAttribute("domain", r_subEmail);

                    String r_email = r_preEmail + r_subEmail;
                    request.setAttribute("email", r_email);

                    AccountDAO adao = new AccountDAO();
                    //Validate
                    String mes1 = isValidFullName(r_name.trim());
                    request.setAttribute("errorMessage1", mes1);

                    String mes2 = isOver18YearsOld(r_dob);
                    request.setAttribute("errorMessage2", mes2);

                    String mes3 = isValidPhone(r_phone, id);
                    request.setAttribute("errorMessage3", mes3);

                    //Validate email k duoc trung voi email trong database
                    String mes4 = isValidEmail(r_email, id);
                    request.setAttribute("errorMessage4", mes4);

                    String mes5 = isValidPreEmail(r_preEmail);
                    request.setAttribute("errorMessage5", mes5);

                    if (mes1 != null || mes2 != null || mes3 != null || mes4 != null || mes5 != null) {
                        request.getRequestDispatcher("admin/Modify_Teacher.jsp").forward(request, response);
                    } else {
                        adao.updateAccount(id, r_email, r_name, r_dob, r_gender, r_phone, r_address);
                        response.sendRedirect("viewDetail");
                    }
                } else if (request.getParameter("mod").equals("updateAdmin")) {

                    int id = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("id", id);
                    request.setAttribute("role", "Admin");

                    String r_name = request.getParameter("name");
                    request.setAttribute("name", r_name);

                    Date r_dob = Date.valueOf(request.getParameter("dob"));
                    request.setAttribute("dob", r_dob);

                    boolean r_gender = (request.getParameter("gender")).equals("male");
                    request.setAttribute("gender", r_gender);

                    String r_phone = request.getParameter("phone");
                    request.setAttribute("phone", r_phone);

                    String r_address = request.getParameter("address");
                    request.setAttribute("address", r_address);

                    String r_preEmail = request.getParameter("preEmail");
                    request.setAttribute("username", r_preEmail);

                    String r_subEmail = request.getParameter("subEmail");
                    request.setAttribute("domain", r_subEmail);

                    String r_email = r_preEmail + r_subEmail;
                    request.setAttribute("email", r_email);

                    AccountDAO adao = new AccountDAO();
                    //Validate
                    String mes1 = isValidFullName(r_name.trim());
                    request.setAttribute("errorMessage1", mes1);

                    String mes2 = isOver18YearsOld(r_dob);
                    request.setAttribute("errorMessage2", mes2);

                    String mes3 = isValidPhone(r_phone, id);
                    request.setAttribute("errorMessage3", mes3);

                    //Validate email k duoc trung voi email trong database
                    String mes4 = isValidEmail(r_email, id);
                    request.setAttribute("errorMessage4", mes4);

                    String mes5 = isValidPreEmail(r_preEmail);
                    request.setAttribute("errorMessage5", mes5);
                    if (mes1 != null || mes2 != null || mes3 != null || mes4 != null || mes5 != null) {
                        request.getRequestDispatcher("admin/Profile_Admin.jsp").forward(request, response);
                    } else {
                        adao.updateAccount(id, r_email, r_name, r_dob, r_gender, r_phone, r_address);
                        response.sendRedirect("home");
                    }
                } else {
                    response.sendRedirect("login");
                }
            } else {
                response.sendRedirect("login");
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

    private String isValidPhone(String phoneNumber, int id) {
        String regex = "^\\d{10}$";
        String mes3 = null;
        boolean flag = true;
        AccountDAO aDAO = new AccountDAO();
        String a = aDAO.getPhone(phoneNumber);
        ArrayList<Account> accList = aDAO.getListAccount();
        for (Account account : accList) {
            if (account.getPhone().equals(phoneNumber) && account.getId() != id) {
                flag = false;
                break;
            }
        }
        if (flag = false) {
            mes3 = "Existed Phone Number";
        }
        if (!phoneNumber.matches(regex) || phoneNumber.trim() == "") {
            mes3 = "Invalid phone number";
        }

        return mes3;
    }

    private String isValidPreEmail(String preEmail) {
        String mes5 = null;
        String regex = "[a-zA-Z0-9]+";
        if (!preEmail.matches(regex)) {
            mes5 = "Email can't contain special character";
        }
        return mes5;
    }

    private String isValidEmail(String email, int id) {
        String[] preEmail = email.split("@");
        String username = preEmail[0];
        String EMAIL_REGEX = "^(?!\\\\.)[a-zA-Z0-9_.]+(?!\\\\.)$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        String mes4 = null;
        String regex = "\\s"; // Biểu thức chính quy kiểm tra dấu cách
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        Matcher matcher1 = EMAIL_PATTERN.matcher(username);
        AccountDAO adao = new AccountDAO();

        Account a = adao.getUsername(email);

        if (a != null && a.getId() != id) {
            mes4 = "Existed Email";
        }

        if (matcher.find() || username.startsWith(".") || username.endsWith(".") || username.contains("..") || username.startsWith("_") || username.endsWith("_") || username.contains("__") || username.equalsIgnoreCase("webmaster") || username.equalsIgnoreCase("admin") || !matcher1.matches()) {
            mes4 = "Invalid email";
        }
        return mes4;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
