package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet(name = "CreatePasswordController", urlPatterns = {"/create-pass"})
public class CreatePasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login/Create_Pass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("email");
        String pass = request.getParameter("newPassword");
        String cPass = request.getParameter("confirmPassword");
        String upperCaseChars = "(.*[A-Z].*)";
        String lowerCaseChars = "(.*[a-z].*)";
        String numbers = "(.*[0-9].*)";
        String noSpecialChars = "^[a-zA-Z0-9\\s.,?!'\"\"(){}\\[\\]:;]+$";
        String spaceCharacter = "^[^\\s]+$";

        if (pass == null || pass.isEmpty()) {
            handleValidationFailure(request, response, "newPassErr", "Password can't be empty.");
        } else if (cPass == null || cPass.isEmpty()) {
            handleValidationFailure(request, response, "confirmPassErr", "Confirmed password can't be empty.");
        } else if (pass.length() < 6) {
            handleValidationFailure(request, response, "newPassErr", "Password length must be more than 6 characters.");
        } else if (!pass.matches(upperCaseChars)) {
            handleValidationFailure(request, response, "newPassErr", "Password must have at least one uppercase character.");
        } else if (!pass.matches(lowerCaseChars)) {
            handleValidationFailure(request, response, "newPassErr", "Password must have at least one lowercase character.");
        } else if (!pass.matches(numbers)) {
            handleValidationFailure(request, response, "newPassErr", "Password must have at least one number.");
        } else if (!pass.matches(noSpecialChars)) {
            handleValidationFailure(request, response, "newPassErr", "Password cannot have any special character.");
        } else if (!pass.matches(spaceCharacter)) {
            handleValidationFailure(request, response, "newPassErr", "Password cannot have a space character.");
        } else if (!pass.equals(cPass)) {
            handleValidationFailure(request, response, "error", "New password and Confirm password do not match.");
        } else {
            AccountDAO aDAO = new AccountDAO();
            Account acc = new Account(user, pass);
            aDAO.changeAccountPass(acc);
            response.sendRedirect("login");
        }
    }

    private void handleValidationFailure(HttpServletRequest request, HttpServletResponse response, String attribute, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute(attribute, errorMessage);
        request.getRequestDispatcher("login/Create_Pass.jsp").forward(request, response);
    }

}
