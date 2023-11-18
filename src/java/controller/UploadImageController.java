package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import model.Account;
import utils.Helper;

@WebServlet(name = "UploadImageController", urlPatterns = {"/upload"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UploadImageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("newAvatar");
        String originalFileName = filePart.getSubmittedFileName();
        String randomString = Helper.generateRandomSring(10);

        // contextPath = ./build/web/...
        String _contextPath = getServletContext().getRealPath("/");
        String contextPath = new File(new File(_contextPath).getParent()).getParent();

        String relativePath = "/image/" + originalFileName.replace(".", "-" + randomString + ".");
        String fullPath = contextPath + "/web" + relativePath;

        // fullPath = ./web/img/...
        filePart.write(fullPath);

        String role = request.getParameter("role");
        int userId = Integer.parseInt(String.valueOf(request.getParameter("userId")));
        new AccountDAO().updateImage(userId, "." + relativePath);

        int currentUserId = ((Account) request.getSession().getAttribute("account")).getId();

        request.getSession().removeAttribute("account");
        request.getSession().setAttribute("account", new AccountDAO().getAccountById(currentUserId));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
        }

//        response.sendRedirect("./modify?id=" + userId + "&mod=3" + (request.getParameter("queryString") != null
//                ? "?" + request.getParameter("queryString")
//                : ""));
        switch (role) {
            case "Admin":
                response.sendRedirect("modify?id=" + userId + "&mod=3");
                break;
            case "Teacher":
                response.sendRedirect("modify?id=" + userId + "&mod=2");
                break;
            case "Student":
                response.sendRedirect("modify?id=" + userId + "&mod=1");
                break;
            default:
                break;
        }
//        response.sendRedirect("addaccount");
    }

}
