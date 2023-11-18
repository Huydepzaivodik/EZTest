package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Quiz;
import model.Class;
import model.Course_ClassName_QuizName;
import model.Question_Bank;
import model.Quiz_Result;

public class QuizDAO extends DBContext {

    public Quiz addQuiz(Quiz q) {
        try {
            String sql = "INSERT INTO [dbo].[Quiz]\n"
                    + "           ([class_id]\n"
                    + "           ,[quiz_name]\n"
                    + "           ,[start_time]\n"
                    + "           ,[duration]\n"
                    + "           ,[date_end]\n"
                    + "           ,[is_displaydetail]\n"
                    + "           ,[create_date],[is_visible],[created_by])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, q.getClass_id().getClass_id());
            stm.setString(2, q.getQuiz_name());
            stm.setTimestamp(3, q.getStart_time());
            stm.setInt(4, q.getDuration());
            stm.setTimestamp(5, q.getDate_end());
            stm.setBoolean(6, q.isIs_displaydetail());
            stm.setTimestamp(7, q.getCreate_date());
            stm.setBoolean(8, q.isIs_visible());
            stm.setInt(9, q.getCreated_by());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getQuizID(Quiz q) {
        try {
            String sql = "SELECT [quiz_id]\n"
                    + "  FROM [dbo].[Quiz]\n"
                    + "  where class_id = ?\n"
                    + "      and quiz_name = ?\n"
                    + "      and start_time = ?\n"
                    + "      and duration = ?\n"
                    + "      and status = ?\n"
                    + "      and date_end = ?\n"
                    + "      and is_visible = ?\n"
                    + "      and is_displaydetail = ?\n"
                    + "      and create_date= ?\n"
                    + "      and created_by= ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, q.getClass_id().getClass_id());
            stm.setString(2, q.getQuiz_name());
            stm.setTimestamp(3, q.getStart_time());
            stm.setInt(4, q.getDuration());
            stm.setBoolean(5, q.isStatus());
            stm.setTimestamp(6, q.getDate_end());
            stm.setBoolean(7, q.isIs_visible());
            stm.setBoolean(8, q.isIs_displaydetail());
            stm.setTimestamp(9, q.getCreate_date());
            stm.setInt(10, q.getCreated_by());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("quiz_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getCreatedByTeacherID(int id) {
        try {
            String sql = "select created_by from Quiz where quiz_id=? ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("created_by");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getQuizID(int class_id, String quiz_name) {
        try {
            String sql = "SELECT [quiz_id]\n"
                    + "  FROM [dbo].[Quiz]\n"
                    + "  where class_id = ? and quiz_name=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, class_id);
            stm.setString(2, quiz_name);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("quiz_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<Quiz> getListQuizByTeacher(int class_id) {
        ArrayList<Quiz> quizList = new ArrayList<>();
        try {
            String sql = "select q.quiz_id, q.quiz_name, q.class_id, q.duration, q.create_date, q.start_time, q.date_end, q.is_visible from Quiz q \n"
                    + "inner join Class cl on cl.class_id = q.class_id\n"
                    + "where q.class_id=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, class_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Quiz q = new Quiz();
                Class c = new Class();

                q.setQuiz_id(rs.getInt(1));
                q.setQuiz_name(rs.getString(2));
                c.setClass_id(rs.getInt(3));
                q.setClass_id(c);
                q.setDuration(rs.getInt(4));
                q.setCreate_date(rs.getTimestamp(5));
                q.setStart_time(rs.getTimestamp(6));
                q.setDate_end(rs.getTimestamp(7));
                q.setIs_visible(rs.getBoolean(8));

                quizList.add(q);
            }
        } catch (SQLException e) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return quizList;
    }

    public ArrayList<Quiz> getListQuizInClass(int course_id, int student_id) {
        ArrayList<Quiz> quizList = new ArrayList<>();
        try {
            String sql = "select q.quiz_id, q.quiz_name, q.class_id, q.duration, q.create_date, q.start_time, q.date_end, q.is_visible from Quiz q\n"
                    + "INNER JOIN dbo.Class cl ON cl.class_id = q.class_id\n"
                    + "inner JOIN dbo.Student_Class sc ON sc.class_id = cl.class_id\n"
                    + "WHERE cl.course_id = ? and sc.student_id = ? and q.is_visible='True'";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, course_id);
            stm.setInt(2, student_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Quiz q = new Quiz();
                Class c = new Class();

                q.setQuiz_id(rs.getInt(1));
                q.setQuiz_name(rs.getString(2));
                c.setClass_id(rs.getInt(3));
                q.setClass_id(c);
                q.setDuration(rs.getInt(4));
                q.setCreate_date(rs.getTimestamp(5));
                q.setStart_time(rs.getTimestamp(6));
                q.setDate_end(rs.getTimestamp(7));
                q.setIs_visible(rs.getBoolean(8));
                quizList.add(q);
            }
        } catch (SQLException e) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return quizList;
    }

    public ArrayList<Quiz> getListQuizInClassTotal(int course_id, int student_id) {
        ArrayList<Quiz> quizList = new ArrayList<>();
        try {
            String sql = "select q.quiz_id, q.quiz_name, q.class_id, q.duration, q.create_date, q.start_time, q.date_end, q.is_visible from Quiz q\n"
                    + "INNER JOIN dbo.Class cl ON cl.class_id = q.class_id\n"
                    + "inner JOIN dbo.Student_Class sc ON sc.class_id = cl.class_id\n"
                    + "WHERE cl.course_id = ? and sc.student_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, course_id);
            stm.setInt(2, student_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Quiz q = new Quiz();
                Class c = new Class();

                q.setQuiz_id(rs.getInt(1));
                q.setQuiz_name(rs.getString(2));
                c.setClass_id(rs.getInt(3));
                q.setClass_id(c);
                q.setDuration(rs.getInt(4));
                q.setCreate_date(rs.getTimestamp(5));
                q.setStart_time(rs.getTimestamp(6));
                q.setDate_end(rs.getTimestamp(7));
                q.setIs_visible(rs.getBoolean(8));
                quizList.add(q);
            }
        } catch (SQLException e) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return quizList;
    }

//    public static void main(String[] args) {
//        QuizDAO qd = new QuizDAO();
//        qd.updateIsvisibleQuiz(146);
//    }
    public void updateIsvisibleQuiz(int quiz_id) {
        try {
            String sql = "Update Quiz set is_visible = 'true' where quiz_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quiz_id);
            stm.executeUpdate();
        } catch (Exception e) {
        }
    }

    public List<Float> getScoresByQuizId(int quiz_id) {
        List<Float> scores = new ArrayList<>();
        try {
            String sql = "SELECT score FROM Quiz_Result WHERE quiz_id = ? and is_valid =1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quiz_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                float score = rs.getFloat("score");
                scores.add(score);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;
    }
//    public static void main(String[] args) {
//        QuizDAO  qd = new QuizDAO();
//        System.out.println(qd.getQuizResult(146).get(1).isIs_valid());
//    }

    public ArrayList<Quiz_Result> getQuizResult(int quiz_id) {
        ArrayList<Quiz_Result> quizList = new ArrayList<>();
        try {
            String sql = "select quiz_id, student_id, score, start_time_student, end_time_student,is_valid from Quiz_Result where quiz_id=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quiz_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Quiz_Result qr = new Quiz_Result();
                Quiz q = new Quiz();
                Account a = new Account();

                q.setQuiz_id(rs.getInt(1));
                qr.setQuiz_id(q);
                a.setId(rs.getInt(2));
                qr.setStudent_id(a);
                qr.setScore(rs.getFloat(3));
                qr.setStart_time_student(rs.getTimestamp(4));
                qr.setEnd_time_student(rs.getTimestamp(5));
                qr.setIs_valid(rs.getBoolean(6));
                quizList.add(qr);
            }
        } catch (SQLException e) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return quizList;
    }

    public Quiz getQuizByName(String quiz_name, int class_id) {
        try {
            String sql = "Select * from Quiz where quiz_name = ? and class_id=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, quiz_name);
            stm.setInt(2, class_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Quiz q = new Quiz();
                Class cl = new Class();

                q.setQuiz_id(rs.getInt(1));
                cl.setClass_id(rs.getInt(2));
                q.setClass_id(cl);
                q.setQuiz_name(rs.getString(3));
                q.setStart_time(rs.getTimestamp(4));
                q.setDuration(rs.getInt(5));
                q.setStatus(rs.getBoolean(6));
                q.setDate_end(rs.getTimestamp(7));
                q.setIs_visible(rs.getBoolean(8));
                q.setIs_displaydetail(rs.getBoolean(9));
                q.setCreate_date(rs.getTimestamp(10));

                return q;

            }
        } catch (SQLException e) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public Timestamp getStart_time(int quiz_id) {
        try {
            String sql = "select start_time from Quiz where quiz_id=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quiz_id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("start_time");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void deleteQuiz(int quiz_id) {
        try {
            String sql1 = "UPDATE Quiz SET is_visible = 0 WHERE quiz_id = ?";
            PreparedStatement stm1 = connection.prepareStatement(sql1);
            stm1.setInt(1, quiz_id);
            stm1.executeUpdate();

            String sql2 = "UPDATE Quiz_Question SET is_visible = 0 WHERE quiz_id = ?";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setInt(1, quiz_id);
            stm2.executeUpdate();

            String sql3 = "UPDATE Choice SET is_visible = 0 WHERE question_id IN (SELECT question_id FROM Quiz_Question WHERE quiz_id = ?)";
            PreparedStatement stm3 = connection.prepareStatement(sql3);
            stm3.setInt(1, quiz_id);
            stm3.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Course_ClassName_QuizName getCCQByQuizId(int quizId) {
        try {
            String sql = "select q.quiz_name,c.course_name,cl.class_name from Quiz q\n"
                    + "	inner join Class cl on cl.class_id = q.class_id\n"
                    + "	inner join Course c on c.course_id = cl.course_id\n"
                    + "where q.quiz_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quizId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String quiz_name = rs.getString(1);
                String course_name = rs.getString(2);
                String class_name = rs.getString(3);
                return new Course_ClassName_QuizName(quiz_name, course_name, class_name);
            }
        } catch (SQLException e) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public void deactivateQuiz(int quiz_id) {
        try {
            String sql1 = "UPDATE Quiz SET is_visible = 0 WHERE quiz_id = ?";
            PreparedStatement stm1 = connection.prepareStatement(sql1);
            stm1.setInt(1, quiz_id);
            stm1.executeUpdate();

            String sql2 = "UPDATE Quiz_Question SET is_visible = 0 WHERE quiz_id = ?";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setInt(1, quiz_id);
            stm2.executeUpdate();

            String sql3 = "UPDATE Choice SET is_visible = 0 WHERE question_id IN (SELECT question_id FROM Quiz_Question WHERE quiz_id=?)";
            PreparedStatement stm3 = connection.prepareStatement(sql3);
            stm3.setInt(1, quiz_id);
            stm3.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void activateQuiz(int quiz_id) {
        try {
            String sql1 = "UPDATE Quiz SET is_visible = 1 WHERE quiz_id = ?";
            PreparedStatement stm1 = connection.prepareStatement(sql1);
            stm1.setInt(1, quiz_id);
            stm1.executeUpdate();

            String sql2 = "UPDATE Quiz_Question SET is_visible = 1 WHERE quiz_id = ?";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setInt(1, quiz_id);
            stm2.executeUpdate();

            String sql3 = "UPDATE Choice SET is_visible = 1 WHERE question_id IN (SELECT question_id FROM Quiz_Question WHERE quiz_id=?)";
            PreparedStatement stm3 = connection.prepareStatement(sql3);
            stm3.setInt(1, quiz_id);
            stm3.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Quiz getQuizById(int quiz_id) {
        try {
            String sql = "Select * from Quiz where quiz_id=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quiz_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Quiz q = new Quiz();
                Class cl = new Class();

                q.setQuiz_id(rs.getInt(1));
                cl.setClass_id(rs.getInt(2));
                q.setClass_id(cl);
                q.setQuiz_name(rs.getString(3));
                q.setStart_time(rs.getTimestamp(4));
                q.setDuration(rs.getInt(5));
                q.setStatus(rs.getBoolean(6));
                q.setDate_end(rs.getTimestamp(7));
                q.setIs_visible(rs.getBoolean(8));
                q.setIs_displaydetail(rs.getBoolean(9));
                q.setCreate_date(rs.getTimestamp(10));

                return q;
            }
        } catch (SQLException e) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public boolean updateQuizInfo(Quiz q) {
        boolean flag = false;
        try {
            String sql = "UPDATE [dbo].[Quiz]\n"
                    + "   SET [quiz_name] = ?\n"
                    + "      ,[start_time] = ?\n"
                    + "      ,[duration] = ?\n"
                    + "      ,[date_end] = ?\n"
                    + "      ,[is_visible] = ?\n"
                    + "      ,[is_displaydetail] = ?\n"
                    + " WHERE quiz_id =?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, q.getQuiz_name());
            stm.setTimestamp(2, q.getStart_time());
            stm.setInt(3, q.getDuration());
            stm.setTimestamp(4, q.getDate_end());
            stm.setBoolean(5, q.isIs_visible());
            stm.setBoolean(6, q.isIs_displaydetail());
            stm.setInt(7, q.getQuiz_id());
            stm.executeUpdate();
            flag = true;
        } catch (SQLException ex) {
            flag = false;
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public String getExistedQuizName(String quizName, int quizId, int classId) {
        try {
            String sql = "SELECT [quiz_name]\n"
                    + "  FROM [dbo].[Quiz]\n"
                    + "  inner join dbo.Class\n"
                    + "  on Quiz.class_id = Class.class_id\n"
                    + "  where quiz_name =? and quiz_id <> ? and Class.class_id= ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, quizName);
            stm.setInt(2, quizId);
            stm.setInt(3, classId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString("quiz_name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean isQuizInfoChanged(Quiz q) {
        try {
            String sql = "SELECT [quiz_name], [start_time], [duration], [date_end], [is_visible], [is_displaydetail] "
                    + "FROM [dbo].[Quiz] WHERE quiz_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, q.getQuiz_id());
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                // So sánh giá trị trong đối tượng Quiz với giá trị trong cơ sở dữ liệu
                return !q.getQuiz_name().equals(rs.getString("quiz_name"))
                        || !q.getStart_time().equals(rs.getTimestamp("start_time"))
                        || q.getDuration() != rs.getInt("duration")
                        || !q.getDate_end().equals(rs.getTimestamp("due_date"))
                        || q.isIs_visible() != rs.getBoolean("isVisible")
                        || q.isIs_displaydetail() != rs.getBoolean("isDisplayDetail");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Trả về true nếu có lỗi xảy ra
        return true;
    }

    public ArrayList<Quiz> getStudentExamSchedule(int studentId) {
        ArrayList<Quiz> quizList = new ArrayList<>();
        try {
            String sql = "Select cl.class_id, q.quiz_name, q.start_time, q.date_end from dbo.Quiz q\n"
                    + "inner join dbo.Class cl\n"
                    + "on cl.class_id = q.class_id\n"
                    + "inner join dbo.Student_Class sc \n"
                    + "on sc.class_id = cl.class_id\n"
                    + "Where sc.student_id = ?\n"
                    + "order by q.start_time asc";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, studentId);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Quiz q1 = new Quiz();
                Class c = new Class();
                c.setClass_id(rs.getInt(1));
                q1.setClass_id(c);
                q1.setQuiz_name(rs.getString(2));
                q1.setStart_time(rs.getTimestamp(3));
                q1.setDate_end(rs.getTimestamp(4));
                quizList.add(q1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizList;
    }
    
    public static void main(String[] args) {
        ArrayList<Quiz> quizList = new ArrayList<>();
        QuizDAO q = new QuizDAO();
        q.getStudentExamSchedule(5);
        System.out.println(q);
    }

}
