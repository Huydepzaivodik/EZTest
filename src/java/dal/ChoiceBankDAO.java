/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Choice_Bank;
import model.Course;
import model.Question_Bank;
import model.Quiz_Question;

/**
 *
 * @author admin
 */
public class ChoiceBankDAO extends DBContext {

    public ArrayList<Choice_Bank> getChoiceBankListByQuestionBankId() {
        ArrayList<Choice_Bank> choiceBankList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Choice_Bank]";//
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int choice_bank_id = rs.getInt("choice_bank_id"); // Sửa lấy cột bằng tên cột hoặc thứ tự của cột
                Question_Bank qb = new Question_Bank(rs.getInt("question_bank_id")); // Sửa lấy cột bằng tên cột hoặc thứ tự của cột
                String choice_bank_text = rs.getString("choice_bank_text"); // Sửa lấy cột bằng tên cột hoặc thứ tự của cột
                boolean is_correct = rs.getBoolean("is_correct"); // Sửa lấy cột bằng tên cột hoặc thứ tự của cột
                int weight = rs.getInt("weight"); // Sửa lấy cột bằng tên cột hoặc thứ tự của cột
                Choice_Bank cb = new Choice_Bank(choice_bank_id, qb, choice_bank_text, is_correct, weight);// Thêm is_correct vào constructor nếu có
                choiceBankList.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ChoiceBankDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return choiceBankList;
    }

    public static void main(String[] args) {

        ChoiceBankDAO choiceBankDAO = new ChoiceBankDAO();

        // Replace the argument with the appropriate question_bank_id you want to test
        ArrayList<Choice_Bank> choiceList = choiceBankDAO.getChoiceBankListByQuestionBankId();

        // Print the results
        for (Choice_Bank choice : choiceList) {
            System.out.println(choice.toString());
        }

    }
    //cái này phải lấy ra
    public ArrayList<Choice_Bank> getChoiceBankByQuestionBankId(int question_bank_id) {
        ArrayList<Choice_Bank> choiceBankList = new ArrayList<>();
        try {
            String sql = "select * from [Choice_Bank] where question_bank_id =?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, question_bank_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int choice_bank_id = rs.getInt(1);
                Question_Bank qb = new Question_Bank(rs.getInt(2));
                String choice_bank_text = rs.getString(3);
                Boolean is_correct = rs.getBoolean(4);
                int weight = rs.getInt(5);
                Choice_Bank cb = new Choice_Bank(choice_bank_id, qb, choice_bank_text, is_correct, weight);
                choiceBankList.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ChoiceBankDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return choiceBankList;
    }

}
