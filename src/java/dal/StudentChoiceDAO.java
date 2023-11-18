package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Account;
import model.Choice;
import model.Quiz;
import model.Quiz_Question;
import model.StudentChoice;

public class StudentChoiceDAO extends DBContext {

    public ArrayList<StudentChoice> getListStudentChoiceByStuIdAndQuizId(int student_id, int quiz_id) {
        ArrayList<StudentChoice> studentChoiceList = new ArrayList<>();
        try {
            String sql = "select * from Student_Choice where student_id = ? and quiz_id =?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, student_id);
            stm.setInt(2, quiz_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int student_student_choice = rs.getInt(1);
                Account acc = new Account(rs.getInt(2));
                Choice ch = new Choice(rs.getInt(3));
                Quiz_Question qq = new Quiz_Question(rs.getInt(4));
                Quiz q = new Quiz(rs.getInt(5));

                StudentChoice stdChoice = new StudentChoice(student_student_choice, acc, ch, qq, q);
                studentChoiceList.add(stdChoice);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return studentChoiceList;
    }

    public ArrayList<Integer> getSubmittedChoiceId(int student_id, int quiz_id, int question_id) {
        ArrayList<Integer> _submittedAnswerIds = new ArrayList<>();
        try {
            String sql = "select student_choice from Student_Choice where student_id = ? and quiz_id =? and question_id=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, student_id);
            stm.setInt(2, quiz_id);
            stm.setInt(3, question_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Choice ch = new Choice(rs.getInt(1));
                _submittedAnswerIds.add(ch.getChoice_id());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return _submittedAnswerIds;
    }
//    public static void main(String[] args) {
//        StudentChoiceDAO st = new StudentChoiceDAO();
//        System.out.println(st.getSubmittedChoiceId(1, 142, 74));
//    }

    public ArrayList<Integer> getQuestionIdInStudentChoice(int student_id, int quiz_id) {
        ArrayList<Integer> listQuestionId = new ArrayList<>();
        try {
            String sql = "select distinct question_id from Student_Choice where student_id = ? and quiz_id =?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, student_id);
            stm.setInt(2, quiz_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int question_id = rs.getInt(1);
                listQuestionId.add(question_id);
            }
        } catch (Exception e) {
        }
        return listQuestionId;
    }

    public ArrayList<StudentChoice> getListStudentChoiceByStuIdAndQuestionId(int student_id, int question_id) {
        ArrayList<StudentChoice> studentChoiceList = new ArrayList<>();
        try {
            String sql = "select * from Student_Choice where student_id = ? and question_id =?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, student_id);
            stm.setInt(2, question_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int student_student_choice = rs.getInt(1);
                Account acc = new Account(rs.getInt(2));
                Choice ch = new Choice(rs.getInt(3));
                Quiz_Question qq = new Quiz_Question(rs.getInt(4));
                Quiz q = new Quiz(rs.getInt(5));

                StudentChoice stdChoice = new StudentChoice(student_student_choice, acc, ch, qq, q);
                studentChoiceList.add(stdChoice);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return studentChoiceList;
    }

}
