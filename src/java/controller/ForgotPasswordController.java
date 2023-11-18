package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.Helper;
import utils.Mail;

@WebServlet(name = "ForgotPasswordController", urlPatterns = {"/forgot-pass"})
public class ForgotPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login/Forgot_Pass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountDAO accountDAO = new AccountDAO();
        String email = request.getParameter("email");
        
        if(accountDAO.getUsername(email)!= null){
            session.setAttribute("email", email);
            String otp = Helper.genRandSixDigit();
            session.setAttribute("systemOtp", otp);
            Mail.send(email, "EzTest send you an OTP to change password", "Your verification OTP is: " + otp);
            response.sendRedirect("otp-confirm");
        }else {
            request.setAttribute("msg", "Email does not exist! Input again");
            request.getRequestDispatcher("login/Forgot_Pass.jsp").forward(request, response);           
        } 
    }

}
