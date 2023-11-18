package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "OTPConfirmation", urlPatterns = {"/otp-confirm"})
public class OTPConfirmationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login/OTP_Confirm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String otp = request.getParameter("otp");
        String systemOTP = (String) session.getAttribute("systemOtp");
        if (otp.equals(systemOTP)) {
            session.removeAttribute("otp");
            session.setAttribute("isOtpConfirmSuccess", "true");
            response.sendRedirect("create-pass");
        } else {
            request.setAttribute("msg", "Wrong OTP, enter again.");
            request.getRequestDispatcher("login/OTP_Confirm.jsp").forward(request, response);
        }
    }


}
