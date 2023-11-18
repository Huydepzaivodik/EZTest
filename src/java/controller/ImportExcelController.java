package controller;

import dal.ExcelDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import java.io.File;
import java.util.List;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 20,
        maxRequestSize = 1024 * 1024 * 50
)
@WebServlet("/ImportExcelController")
public class ImportExcelController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExcelDAO excelDAO = new ExcelDAO();

        String excelFilePath = excelDAO.getExcelFilePath(request);
        if (excelFilePath == null) {
            response.getWriter().println("There is no Excel file selected.");
            return;
        }

        String savePath = request.getServletContext().getRealPath("/uploads");
        File saveDir = new File(savePath);

        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        request.getParameter("quiz_id");
        // Import Data from Excel to Database
        List<Integer> questionIds = excelDAO.importDataFromExcel(excelFilePath, 10);//quiz_id

        // Import Answers from Excel to Database
        excelDAO.importAnswersFromExcel(excelFilePath, questionIds);

        response.getWriter().println("Imported data from Excel successfully.");
    }
}
