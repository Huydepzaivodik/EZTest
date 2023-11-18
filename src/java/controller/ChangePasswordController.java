package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet(name = "ChangePasswordController", urlPatterns = {"/change-pass"})
public class ChangePasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            request.getRequestDispatcher("login/Change_Pass.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Account account = (Account) request.getSession().getAttribute("account");
        String user = account.getEmail();
        String pass = account.getPassword();
        String oldPass = request.getParameter("oldPassword");
        String newPass = request.getParameter("newPassword");
        String confirmPass = request.getParameter("confirmPassword");
        String upperCaseChars = "(.*[A-Z].*)";
        String lowerCaseChars = "(.*[a-z].*)";
        String numbers = "(.*[0-9].*)";
        String noSpecialChars = "^[a-zA-Z0-9\\s.,?!'\"\"(){}\\[\\]:;]+$";
        String spaceCharacter = "^[^\\s]+$";

        if (isNullOrEmpty(oldPass)) {
            handleValidationFailure(request, response, "oldPassErr", "Old password cannot be empty");
        } else if (isNullOrEmpty(newPass)) {
            handleValidationFailure(request, response, "newPassErr", "New password cannot be empty");
        } else if (isNullOrEmpty(confirmPass)) {
            handleValidationFailure(request, response, "confirmPassErr", "Confirm password cannot be empty");
        } else if (!oldPass.equals(pass)) {
            handleValidationFailure(request, response, "oldPassErr", "Old password is not correct");
        } else if (newPass.length() < 6) {
            handleValidationFailure(request, response, "newPassErr", "Password length must be more than 6 characters");
        } else if (!newPass.matches(upperCaseChars)) {
            handleValidationFailure(request, response, "newPassErr", "Password must have at least one uppercase character");
        } else if (!newPass.matches(lowerCaseChars)) {
            handleValidationFailure(request, response, "newPassErr", "Password must have at least one lowercase character");
        } else if (!newPass.matches(numbers)) {
            handleValidationFailure(request, response, "newPassErr", "Password must have at least one number");
        } else if (!newPass.matches(noSpecialChars)) {
            handleValidationFailure(request, response, "newPassErr", "Password cannot have any special character");
        } else if (!newPass.matches(spaceCharacter)) {
            handleValidationFailure(request, response, "newPassErr", "Password cannot have space character");
        } else if (!newPass.equals(confirmPass)) {
            handleValidationFailure(request, response, "error", "New password and Confirm password do not match");
        } else {
            AccountDAO accountDAO = new AccountDAO();
            Account newAccount = new Account(user, newPass);
            accountDAO.changeAccountPass(newAccount);
            response.sendRedirect("home");
        }
    }

    private void handleValidationFailure(HttpServletRequest request, HttpServletResponse response, String attribute, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute(attribute, errorMessage);
        request.getRequestDispatcher("login/Change_Pass.jsp").forward(request, response);
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
