package controller;

import dal.ExcelDAO;
import excel.ExcelDAO2;
import excel.ImportAccountResult;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import java.io.File;
import java.util.List;
//import org.apache.commons.lang.StringEscapeUtils;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 20,
        maxRequestSize = 1024 * 1024 * 50
)
@WebServlet("/ImportTeacherController")
public class ImportTeacherController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("doPost: you are here");

        ExcelDAO excelDAO = new ExcelDAO();
        ExcelDAO2 excelDAO2 = new ExcelDAO2();
        String excelFilePath = excelDAO.getExcelFilePath(request);
        String successMessage = "";

        if (excelFilePath == null) {
            successMessage = "You have not been choosed any file. Please choose 1 file and try again.";
            response.getWriter().println("<script>alert('" + successMessage + "');window.history.back();</script>");
            return;
        } else if (!excelFilePath.endsWith(".csv") && !excelFilePath.endsWith(".xlsx")) {
            successMessage = "The file have been choosed is not excel file. Try again!";
            response.getWriter().println("<script>alert('" + successMessage + "');window.history.back();</script>");
            return;
        }

        String savePath = request.getServletContext().getRealPath("/uploads");
        File saveDir = new File(savePath);

        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        // Import Data from Excel to Database
        List<ImportAccountResult> importAccountResults
                = excelDAO2.importTeachersFromExcel(excelFilePath); // Import Answers from Excel to Database

        int successCount = countSuccess(importAccountResults);
        int failedCount = countFailed(importAccountResults);
        final String buildResultInfos = buildResultInfos(importAccountResults);
        System.out.println("buildResultInfos: " + buildResultInfos);
        successMessage = "Import List Student successfully! Success: " + successCount + "| Failed: " + failedCount
                + buildResultInfos;
        request.setAttribute("message", escapeJavaScript(successMessage));
        System.out.println("EscapeHtml: " + escapeJavaScript(successMessage));

//        response.getWriter()
//                .println("<script>alert('" + successMessage + "');window.history.back();</script>");
        String redirectScript = "<script>alert(\"" + escapeJavaScript(successMessage) + "\"); window.location.href='viewDetail" + "';</script>";
        System.out.println("script: " + redirectScript);
        response.getWriter().println(redirectScript);
    }

    private int countSuccess(List<ImportAccountResult> importAccountResults) {
        int count = 0;
        if (importAccountResults == null) {
            return count;
        }
        for (ImportAccountResult item : importAccountResults) {
            if (isSuccess(item)) {
                count++;
            }
        }
        return count;
    }

    private int countFailed(List<ImportAccountResult> importAccountResults) {
        int count = 0;
        if (importAccountResults == null) {
            return count;
        }
        for (ImportAccountResult item : importAccountResults) {
            if (!isSuccess(item)) {
                count++;
            }
        }
        return count;
    }

    private static boolean isSuccess(ImportAccountResult item) {
        return item.getErrorMessage() == null || item.getErrorMessage().trim().isEmpty();
    }

    private String buildResultInfos(List<ImportAccountResult> items) {
        String resultInfo = "";
        if (items == null) {
            return resultInfo;
        }
        int rowIndex = 0;
        for (ImportAccountResult item : items) {
            resultInfo += ("\n" + buildResultInfo(item, rowIndex));
            rowIndex++;
        }
        return resultInfo;
    }

    private String buildResultInfo(ImportAccountResult item, int rowIndex) {
        if (isSuccess(item)) {
            return "\n ---------------------------- \n"
                    + "Row " + rowIndex + ": OK. AccountId=" + item.getId();
        } else {
            return "\n ---------------------------- \n"
                    + "Row " + rowIndex + ": FAILED. ErrorMessage=" + item.getErrorMessage();
        }
    }

    private static String escapeJavaScript(String input) {
        return input
                .replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("</", "<\\/");
    }
}
