package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import model.Account;

@WebServlet(name = "ConfirmAccountInfo", urlPatterns = {"/confirmInfo"})
public class ConfirmAccountInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login/Confirm_Info.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String r_email = request.getParameter("pre_email");
        request.setAttribute("r_email", r_email);

        String sub_email = request.getParameter("sub_email");
        request.setAttribute("sub_mail", sub_email);

        Date r_dob = Date.valueOf(request.getParameter("dob"));
        request.setAttribute("r_dob", r_dob);

        boolean r_gender = (request.getParameter("gender")).equals("male");
        request.setAttribute("r_gender", request.getParameter("gender"));

        String r_phone = request.getParameter("phone");
        request.setAttribute("r_phone", r_phone);

        AccountDAO adao = new AccountDAO();
        Account ac = adao.getAccountNotValid(r_email + sub_email, r_dob, r_gender, "0" + r_phone);

        if (ac == null) {
            String mes1 = "Incorrect Information";
            request.setAttribute("errorMessage1", mes1);
            request.getRequestDispatcher("login/Confirm_Info.jsp").forward(request, response);
        } else {
            adao.confirmAccountInfo(r_email + sub_email);
            Account ac1 = adao.getUsername(r_email + sub_email);
            if (ac.getRole_id() == 2) {
                request.getSession().setAttribute("account", ac);
                request.getSession().setAttribute("role", "Teacher");
                request.getSession().setAttribute("teacher_id", ac.getId());
            }
            if (ac.getRole_id() == 3) {
                request.getSession().setAttribute("account", ac);
                request.getSession().setAttribute("role", "Student");
                request.getSession().setAttribute("student_id", ac.getId());
            }
            response.sendRedirect("home");
        }
    }
}
