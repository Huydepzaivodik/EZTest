package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.GoogleConstants;
import model.UserGoogle;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

@WebServlet(name = "LoginGoogleHandler", urlPatterns = {"/login-google"})
public class LoginGoogleHandler extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String code = request.getParameter("code");
        String accessToken = getToken(code);
        UserGoogle user = getUserInfo(accessToken);
        AccountDAO dbA = new AccountDAO();
        Account ac = dbA.getUsername(user.getEmail());

        if (ac != null) {
            request.getSession().setAttribute("account", ac);
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
        } else {
            request.setAttribute("notice", "Your Google account can't access this web.");
            request.getRequestDispatcher("login/Login.jsp").forward(request, response);
        }
    }

    public static String getToken(String code)
            throws ClientProtocolException, IOException { //Get authentication token 
        // call api to get token
        String response = Request.Post(GoogleConstants.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", GoogleConstants.GOOGLE_CLIENT_ID)
                        .add("client_secret", GoogleConstants.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", GoogleConstants.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", GoogleConstants.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static UserGoogle getUserInfo(final String accessToken)
            throws ClientProtocolException, IOException {
        String link = GoogleConstants.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        UserGoogle googlePojo = new Gson().fromJson(response, UserGoogle.class);
        return googlePojo;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
