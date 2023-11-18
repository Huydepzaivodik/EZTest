package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Choice;
import model.Quiz_Question;

public class ChoiceDAO extends DBContext {

    public ArrayList<Choice> getChoiceListByQuestionId(int question_id) {
        ArrayList<Choice> choiceList = new ArrayList<>();
        try {
            String sql = "select * from Choice where question_id =?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, question_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int choice_id = rs.getInt(1);
                Quiz_Question qq = new Quiz_Question(rs.getInt(2));
                String choice_text = rs.getString(3);
                Boolean is_correct = rs.getBoolean(4);
                int weight = rs.getInt(5);
                Choice c = new Choice(choice_id, qq, choice_text, is_correct, weight);
                choiceList.add(c);
            }
        } catch (SQLException e) {
            Logger.getLogger(ChoiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return choiceList;
    }

    public ArrayList<Choice> getChoiceListByQuestionId() {
        ArrayList<Choice> choiceList = new ArrayList<>();
        try {
            String sql = "select * from Choice";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int choice_id = rs.getInt(1);
                Quiz_Question qq = new Quiz_Question(rs.getInt(2));
                String choice_text = rs.getString(3);
                Boolean is_correct = rs.getBoolean(4);
                int weight = rs.getInt(5);
                Choice c = new Choice(choice_id, qq, choice_text, is_correct, weight);
                choiceList.add(c);
            }
        } catch (SQLException e) {
            Logger.getLogger(ChoiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return choiceList;
    }

    public boolean isCorrectChoice(int choiceId) {
        boolean isCorrect = false;
        try {
            String sql = "SELECT is_correct FROM Choice WHERE choice_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, choiceId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                isCorrect = rs.getBoolean("is_correct");
            }
        } catch (SQLException e) {
            Logger.getLogger(ChoiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return isCorrect;
    }

    public ArrayList<Choice> getTrueChoiceList() {
        ArrayList<Choice> trueChoiceList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Choice WHERE weight <> 0";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int choice_id = rs.getInt(1);
                Quiz_Question qq = new Quiz_Question(rs.getInt(2));
                String choice_text = rs.getString(3);
                Boolean is_correct = rs.getBoolean(4);
                int weight = rs.getInt(5);
                boolean is_visible = rs.getBoolean(6);
                Choice c = new Choice(choice_id, qq, choice_text, is_correct, weight, is_visible);
                trueChoiceList.add(c);
            }
        } catch (SQLException e) {
            Logger.getLogger(ChoiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return trueChoiceList;
    }

    public void deleteChoice(int choiceId) {
        try {
            String sql = "DELETE FROM [dbo].[Choice]\n"
                    + "      WHERE choice_id =?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, choiceId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ChoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
