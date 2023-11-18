package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import model.Account;
import model.Quiz;
import model.Quiz_Result;

public class QuizResultDAO extends DBContext {

    public void addScoreOfStudent(int quiz_id, int student_id, float score, Timestamp startTime, Timestamp endTime) {
        try {
            String sql = "Insert into  Quiz_Result (quiz_id,student_id,score,start_time_student, end_time_student,is_valid)\n"
                    + "values (?,?,?,?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quiz_id);
            stm.setInt(2, student_id);
            stm.setFloat(3, score);
            stm.setTimestamp(4, startTime);
            stm.setTimestamp(5, endTime);
            stm.setBoolean(6, true);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addScoreOfTeacher(int quiz_id, int student_id, float score, Timestamp startTime, Timestamp endTime) {
        try {
            String sql = "Insert into  Quiz_Result (quiz_id,student_id,score,start_time_student, end_time_student,is_valid)\n"
                    + "values (?,?,?,?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quiz_id);
            stm.setInt(2, student_id);
            stm.setFloat(3, score);
            stm.setTimestamp(4, startTime);
            stm.setTimestamp(5, endTime);
            stm.setBoolean(6, false);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateScore(int quiz_id, int student_id, float score, Timestamp startTime, Timestamp endTime) {
        try {
            String sql = "UPDATE Quiz_Result SET score = ? WHERE quiz_id = ? AND student_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setFloat(1, score);
            stm.setInt(2, quiz_id);
            stm.setInt(3, student_id);
            stm.setTimestamp(4, startTime);
            stm.setTimestamp(5, endTime);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public boolean existsQuizResult(int quiz_id, int student_id) {
        try {
            String sql = "SELECT * FROM Quiz_Result WHERE quiz_id = ? AND student_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quiz_id);
            stm.setInt(2, student_id);
            ResultSet rs = stm.executeQuery();
            return rs.next(); // Nếu có dòng dữ liệu trả về, tức là đã tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        QuizResultDAO q = new QuizResultDAO();
        System.out.println(q.existsQuizResult(1, 4));
    }

    public Quiz_Result getResultStudent(int quiz_id, int student_id) {
        try {
            String sql = "select * from Quiz_Result where quiz_id = ? and student_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quiz_id);
            stm.setInt(2, student_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int result_id = rs.getInt(1);
                Quiz q = new Quiz(rs.getInt(2));
                Account a = new Account(rs.getInt(3));
                float score = rs.getFloat(4);
                Timestamp start_time_student = rs.getTimestamp(5);
                Timestamp end_time_student = rs.getTimestamp(6);
                return new Quiz_Result(result_id, q, a, score, start_time_student, end_time_student);
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public ArrayList<Quiz_Result> getResultListOfStudent(int student_id) {
        ArrayList<Quiz_Result> quizResultList = new ArrayList<>();
        try {

            String sql = "select * from Quiz_Result where student_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, student_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int result_id = rs.getInt(1);
                Quiz q = new Quiz(rs.getInt(2));
                Account a = new Account(rs.getInt(3));
                float score = rs.getFloat(4);
                Timestamp start_time_student = rs.getTimestamp(5);
                Timestamp end_time_student = rs.getTimestamp(6);
                boolean is_valid = rs.getBoolean(7);
                Quiz_Result qz = new Quiz_Result(result_id, q, a, score, start_time_student, end_time_student, is_valid);
                quizResultList.add(qz);
            }
        } catch (Exception e) {
        }
        return quizResultList;
    }

    public void insertTimeDoneQuiz(Timestamp startTime, Timestamp endTime, int student_id, int quiz_id) {
        try {
            String sql = "INSERT INTO Quiz_Result (start_time_student, end_time_student, student_id, quiz_id) VALUES (?, ?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, startTime);
            stm.setTimestamp(2, endTime);
            stm.setInt(3, student_id);
            stm.setInt(4, quiz_id);
            stm.executeUpdate();
        } catch (Exception e) {
        }
    }

}
