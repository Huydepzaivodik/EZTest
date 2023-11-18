package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login/Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        AccountDAO aDAO = new AccountDAO();
        Account ac1 = aDAO.getUsername(username);
        Account ac = aDAO.getAccount(username, password);
        if (ac1 != null) {
            if (ac != null) {
                if (ac.isIs_valid() == true && ac.isIs_login()==true) {
                    String r = request.getParameter("rem");
                    //Tao 2 cookies: username, password, remember me
                    Cookie cu = new Cookie("cusername", username.trim());
                    Cookie cu2 = new Cookie("crem", r);
                    if (r != null) {
                        //co chon
                        cu.setMaxAge(60 * 60 * 24 * 7 * 4 * 4);//Luu tru 4 thang
                        cu2.setMaxAge(60 * 60 * 24 * 7 * 4 * 4);//Luu tru 4 thang
                    } else {
                        //khong chon
                        cu.setMaxAge(0);
                        cu2.setMaxAge(0);
                    }
                    //luu vao browser
                    response.addCookie(cu);
                    response.addCookie(cu2);
                    if (ac.getRole_id() == 1) {
                        request.getSession().setAttribute("account", ac);
                        request.getSession().setAttribute("role", "Admin");
                        request.getSession().setAttribute("admin_id", ac.getId());
                    }
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
                } else if(ac.isIs_valid() == false && ac.isIs_login()==true){
                    String r = request.getParameter("rem");
                    //Tao 2 cookies: username, password, remember me
                    Cookie cu = new Cookie("cusername", username);
                    Cookie cu2 = new Cookie("crem", r);
                    if (r != null) {
                        //co chon
                        cu.setMaxAge(60 * 60 * 24 * 7 * 4 * 4);//Luu tru 4 thang
                        cu2.setMaxAge(60 * 60 * 24 * 7 * 4 * 4);//Luu tru 4 thang
                    } else {
                        //khong chon
                        cu.setMaxAge(0);
                        cu2.setMaxAge(0);
                    }
                    //luu vao browser
                    response.addCookie(cu);
                    response.addCookie(cu2);
                    response.sendRedirect("confirmInfo");
                }else if(ac.isIs_valid() == false && ac.isIs_login()==false){
                    request.setAttribute("notice", "Invalid Account!");
                    request.getRequestDispatcher("login/Login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("notice", "Wrong Password!");
                request.getRequestDispatcher("login/Login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("notice", "Username does not exit");
            request.getRequestDispatcher("login/Login.jsp").forward(request, response);
        }

    }

}
