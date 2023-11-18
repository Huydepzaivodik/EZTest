/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Choice_Bank;
import model.Course;
import model.Question_Bank;
import model.Quiz;
import model.Class;

/**
 *
 * @author admin
 */
public class QuestionBankDAO extends DBContext {

    //Lấy question bank theo course id
    public ArrayList<Question_Bank> getListQuestionBankByCourseId(int course_id) {
        ArrayList<Question_Bank> questionBankList = new ArrayList<>();
        try {
            String sql = "select * from [Question_Bank] where course_id=?";//
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, course_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int question_bank_id = rs.getInt(1);
                Course c = new Course(rs.getInt(2));
                String question_text = rs.getString(3);
                String explanation = rs.getString(4);
                Boolean question_type = rs.getBoolean(5);

                Question_Bank qqb = new Question_Bank(question_bank_id, c, question_text, explanation, question_type);
                questionBankList.add(qqb);
            }
        } catch (SQLException e) {
            Logger.getLogger(QuestionBankDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return questionBankList;
    }

    //Lấy question bank theo question bank id
    public List<Question_Bank> getListQuestionBankByQuestionBankId(int[] questionBankIds) {
        List<Question_Bank> questionBanks = new ArrayList<>();
        try {
            for (int id : questionBankIds) {
                String sql = "SELECT * FROM [Question_Bank] WHERE question_bank_id=?";
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setInt(1, id);
                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    int question_bank_id = rs.getInt(1);
                    Course c = new Course(rs.getInt(2));
                    String question_text = rs.getString(3);
                    String explanation = rs.getString(4);
                    Boolean question_type = rs.getBoolean(5);

                    Question_Bank qqb = new Question_Bank(question_bank_id, c, question_text, explanation, question_type);
                    questionBanks.add(qqb);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(QuestionBankDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return questionBanks;
    }
//Chưa statement

    public void addQuestionBankToQuiz(int quiz_id, List<Question_Bank> questionBanks, ArrayList<Choice_Bank> choiceBanks) {
        try {
            String questionBankSql = "INSERT INTO [dbo].[Quiz_Question] (quiz_id, question_text, explanation, question_type, is_visible) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement questionBankStm = connection.prepareStatement(questionBankSql, Statement.RETURN_GENERATED_KEYS);

            for (Question_Bank qb : questionBanks) {
                questionBankStm.setInt(1, quiz_id);
                questionBankStm.setString(2, qb.getQuestion_text());
                questionBankStm.setString(3, qb.getExplanation());
                questionBankStm.setBoolean(4, qb.isQuestion_type());
                questionBankStm.setBoolean(5, true);

                int insertedRows = questionBankStm.executeUpdate();

                if (insertedRows > 0) {
                    ResultSet generatedKeys = questionBankStm.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int question_bank_id = generatedKeys.getInt(1);
                        for (Choice_Bank cb : choiceBanks) {
                            if (cb.getQuestion_bank_id().getQuestion_bank_id() == qb.getQuestion_bank_id()) {
                                // Print Choice_Bank details for debugging
                                System.out.println("Choice_Bank Details:");
                                System.out.println("Question Bank ID: " + cb.getQuestion_bank_id().getQuestion_bank_id());
                                System.out.println("Choice Bank Text: " + cb.getChoice_bank_text());
                                System.out.println("Is Correct: " + cb.isIs_correct());
                                System.out.println("Weight: " + cb.getWeight());

                                String choiceSql = "INSERT INTO [dbo].[Choice] (question_id, choice_text, is_correct, weight, is_visible) VALUES (?, ?, ?, ?, ?)";
                                PreparedStatement choiceStm = connection.prepareStatement(choiceSql);
                                choiceStm.setInt(1, question_bank_id);
                                choiceStm.setString(2, cb.getChoice_bank_text());
                                choiceStm.setBoolean(3, cb.isIs_correct());
                                choiceStm.setInt(4, cb.getWeight());
                                choiceStm.setBoolean(5, true);
                                choiceStm.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionBankDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    // lấy giá trị từ question bank rồi insert vào quiz question, choice bank insert vào choice
//    public void addQuestionBankToQuiz(Quiz q, List<Question_Bank> questionBanks, ArrayList<Choice_Bank> choiceBanks) {
//        try {
//            String questionBankSql = "INSERT INTO [dbo].[Question_Bank] (course_id, question_text, explanation, question_type, is_visible) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement questionBankStm = connection.prepareStatement(questionBankSql, Statement.RETURN_GENERATED_KEYS);
//
//            for (Question_Bank qb : questionBanks) {
//                questionBankStm.setInt(1, qb.getCourse_id().getCourse_id());
//                questionBankStm.setString(2, qb.getQuestion_text());
//                questionBankStm.setString(3, qb.getExplanation());
//                questionBankStm.setBoolean(4, qb.isQuestion_type());
//                questionBankStm.setBoolean(5, true);
//
//                int insertedRows = questionBankStm.executeUpdate();
//
//                if (insertedRows > 0) {
//                    ResultSet generatedKeys = questionBankStm.getGeneratedKeys();
//                    if (generatedKeys.next()) {
//                        int question_bank_id = generatedKeys.getInt(1);
//                        for (Choice_Bank cb : choiceBanks) {
//                            if (cb.getQuestion_bank_id().getQuestion_bank_id() == qb.getQuestion_bank_id()) {
//                                String choiceSql = "INSERT INTO [dbo].[Choice Bank] (question_bank_id, choice_bank_text, is_correct, weight, is_visible) VALUES (?, ?, ?, ?, ?)";
//                                PreparedStatement choiceStm = connection.prepareStatement(choiceSql);
//                                choiceStm.setInt(1, question_bank_id);
//                                choiceStm.setString(2, cb.getChoice_bank_text());
//                                choiceStm.setBoolean(3, cb.isIs_correct());
//                                choiceStm.setInt(4, cb.getWeight());
//                                choiceStm.setBoolean(5, true);
//                                choiceStm.executeUpdate();
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(QuestionBankDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

//    public static void main(String[] args) {
//        QuestionBankDAO qbDAO = new QuestionBankDAO();
//        int[] questionBankIds = {1, 2, 3}; // Thay thế mảng này bằng các giá trị question bank ids tương ứng
//        List<Question_Bank> questionBanks = qbDAO.getListQuestionBankByQuestionBankId(questionBankIds);
//
//        // In thông tin các question banks
//        for (Question_Bank qb : questionBanks) {
//            System.out.println("Question Bank ID: " + qb.getQuestion_bank_id());
//            System.out.println("Course ID: " + qb.getCourse_id().getCourse_id());
//            System.out.println("Question Text: " + qb.getQuestion_text());
//            System.out.println("Explanation: " + qb.getExplanation());
//            System.out.println("Question Type: " + qb.isQuestion_type());
//            System.out.println("-----------------------");
//        }
//    }
    public static void main(String[] args) {
        // Tạo đối tượng Quiz
        Quiz quiz = new Quiz();
        Class cl = new Class(1);
        quiz.setClass_id(cl); // Thay thế 1 với ID lớp hợp lệ
        quiz.setQuiz_name("Sample Quiz"); // Thay thế "Sample Quiz" với tên bài kiểm tra hợp lệ

        // Tạo danh sách Question_Bank
        List<Question_Bank> questionBanks = new ArrayList<>();
        // Thêm các phần tử vào questionBanks
        Question_Bank qb1 = new Question_Bank(1, new Course(1), "Sample Question 1", "Sample Explanation 1", true);
        Question_Bank qb2 = new Question_Bank(1, new Course(2), "Sample Question 2", "Sample Explanation 2", false);
        questionBanks.add(qb1);
        questionBanks.add(qb2);

        // Tạo danh sách Choice_Bank
        ArrayList<Choice_Bank> choiceBanks = new ArrayList<>();
        // Thêm các phần tử vào choiceBanks
        Choice_Bank cb1 = new Choice_Bank(qb1, "Sample Choice 1", true, 1);
        Choice_Bank cb2 = new Choice_Bank(qb2, "Sample Choice 2", false, 2);
        choiceBanks.add(cb1);
        choiceBanks.add(cb2);

        // Tạo đối tượng QuestionBankDAO
        QuestionBankDAO questionBankDAO = new QuestionBankDAO();
        // Thực hiện thêm danh sách Question_Bank và Choice_Bank vào Quiz
        questionBankDAO.addQuestionBankToQuiz(1, questionBanks, choiceBanks);
    }
}
