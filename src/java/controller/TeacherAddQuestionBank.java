package controller;

import dal.ChoiceBankDAO;
import dal.QuestionBankDAO;
import dal.QuizDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Choice;
import model.Choice_Bank;
import model.Question_Bank;
import model.Quiz;

@MultipartConfig()
@WebServlet(name = "TeacherAddQuestionBank", urlPatterns = {"/add-question-bank"})
public class TeacherAddQuestionBank extends HttpServlet {

    public static final String UPLOAD_DIR = "images";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (request.getSession().getAttribute("role").equals("Teacher")) {
                // if (request.getParameter("mod") != null && request.getParameter("mod").equals("addQues")) {

                //Có course_id thì dùng FK lấy ra quiz question bank
                //int courseId = Integer.parseInt(request.getParameter("courseId"));
                int courseId = 1;
                //Bên kia lấy quiz làm Fk thì bên này lấy course làm FK
                QuestionBankDAO qbDAO = new QuestionBankDAO();
                //Lấy list questionBank bằng courseId
                List<Question_Bank> qbList = qbDAO.getListQuestionBankByCourseId(courseId);

                // phải lấy ra list choice
                ChoiceBankDAO cbDAO = new ChoiceBankDAO();
                List<Choice_Bank> cbList = cbDAO.getChoiceBankListByQuestionBankId();
                //set attribute và gửi qua jsp để hiển thị list bank
                request.setAttribute("qbList", qbList);
                request.setAttribute("cbList", cbList);
                request.getRequestDispatcher("teacher/Edit_Question_Bank.jsp").forward(request, response);

            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int classId = (int) request.getSession().getAttribute("class_id");
        int quizId = (int) request.getSession().getAttribute("quiz_id");
        String quizName = (String) request.getSession().getAttribute("quiz_name");
        if (request.getParameterValues("questionBankIds") == null) {
            response.sendRedirect("quiz-info?class_id=" + classId + "&quiz_id=" + quizId + "&quiz_name=" + quizName + "&mod=1");
        } else {
            String[] questionBankIds = request.getParameterValues("questionBankIds");
            //chuyển về int để xử lý
            int[] question_bank_ids = Arrays.stream(questionBankIds).mapToInt(Integer::parseInt).toArray();
            if (questionBankIds != null) {
                // Xử lý các question bank ids được chọn ở đây
                System.out.println("Các question bank IDs được chọn: " + Arrays.toString(questionBankIds));
            } else {
                System.out.println("Không có question bank nào được chọn.");
            }
            //Bước 1 dùng list question bank id được chọn vừa lấy được trong jsp để add vào quiz

            // Lấy dữ liệu từ biểu mẫu JSP
            //tẠO HÀM LẤY bank bằng questionBankIds
            QuestionBankDAO qbDAO = new QuestionBankDAO();
            List<Question_Bank> questionBanks = qbDAO.getListQuestionBankByQuestionBankId(question_bank_ids); // Lấy danh sách các question bank từ các id đã chọn        //Tạo hàm add cái list<question bank> đã lấy được 
            QuizDAO qDAO = new QuizDAO();

            //3: arrayList choice bank
            ChoiceBankDAO cbDAO = new ChoiceBankDAO();

            ArrayList<Choice_Bank> choiceBanks = new ArrayList<>();
            for (int question_bank_id : question_bank_ids) {
                ArrayList<Choice_Bank> currentChoiceBanks = cbDAO.getChoiceBankByQuestionBankId(question_bank_id);
                choiceBanks.addAll(currentChoiceBanks);
            }

            qbDAO.addQuestionBankToQuiz(quizId, questionBanks, choiceBanks);
            //dùng for duyệt
            response.sendRedirect("quiz-info?class_id=" + classId + "&quiz_id=" + quizId + "&quiz_name=" + quizName + "&mod=1");
        }
    }
}
