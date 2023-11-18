package dal;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Choice;
import model.Quiz_Question;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import model.Choice_Bank;
import model.Quiz;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import model.Class;
import model.Question_Bank;

public class QuestionDAO extends DBContext {

    public void addQuestionFromExcel(InputStream fileContent) throws IOException {
        try {
            String sql1 = "SELECT TOP (1) [quiz_id]\n"
                    + "  FROM [QuizPractice].[dbo].[Quiz]\n"
                    + "  ORDER BY quiz_id DESC";
            PreparedStatement stm1 = connection.prepareStatement(sql1);
            ResultSet rs1 = stm1.executeQuery();
            int latestQuizId = 0;
            if (rs1.next()) {
                latestQuizId = rs1.getInt("quiz_id");
            }
            XSSFWorkbook workbook = new XSSFWorkbook(fileContent);
            Sheet sheet = workbook.getSheetAt(0);
            String insertQuestionSql = "INSERT INTO Quiz_Question (quiz_id, question_text) VALUES (?, ?)";
            String insertChoiceSql = "INSERT INTO Choice (question_id, choice_text, is_correct, weight) VALUES (?, ?, ?, ?)";
            PreparedStatement questionStm = connection.prepareStatement(insertQuestionSql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement choiceStm = connection.prepareStatement(insertChoiceSql);
            for (Row row : sheet) {
                Cell questionTextCell = row.getCell(0);
                Cell choiceTextCell = row.getCell(1);
                Cell isCorrectCell = row.getCell(2);
                Cell weightCell = row.getCell(3);
                if (questionTextCell != null && choiceTextCell != null && isCorrectCell != null && weightCell != null) {
                    if (questionTextCell.getCellType() == CellType.STRING
                            && choiceTextCell.getCellType() == CellType.STRING
                            && isCorrectCell.getCellType() == CellType.BOOLEAN
                            && weightCell.getCellType() == CellType.NUMERIC) {
                        String questionText = questionTextCell.getStringCellValue();
                        String choiceText = choiceTextCell.getStringCellValue();
                        boolean isCorrect = isCorrectCell.getBooleanCellValue();
                        double weightDouble = weightCell.getNumericCellValue();
                        int weight = (int) Math.round(weightDouble);
                        questionStm.setInt(1, latestQuizId);
                        questionStm.setString(2, questionText);
                        questionStm.executeUpdate();
                        ResultSet generatedKeys = questionStm.getGeneratedKeys();
                        int questionId = 0;
                        if (generatedKeys.next()) {
                            questionId = generatedKeys.getInt(1);
                        }
                        choiceStm.setInt(1, questionId);
                        choiceStm.setString(2, choiceText);
                        choiceStm.setBoolean(3, isCorrect);
                        choiceStm.setInt(4, weight);
                        choiceStm.executeUpdate();
                    }
                }
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getQuizId(int classId) {
        try {
            String sql = "SELECT TOP(1) [quiz_id] FROM [dbo].[Quiz] where class_id = ? order by quiz_id desc";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, classId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("quiz_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void addQuestion(Quiz q, Quiz_Question qq, ArrayList<Choice> choice) {

        try {
            String sql0 = "SELECT TOP(1) quiz_id FROM [dbo].Quiz where class_id= ? and quiz_name=? order by quiz_id desc";
            PreparedStatement stm0 = connection.prepareStatement(sql0);
            stm0.setInt(1, q.getClass_id().getClass_id());
            stm0.setString(2, q.getQuiz_name());
            ResultSet rs0 = stm0.executeQuery();
            if (rs0.next()) {
                int quizId = rs0.getInt("quiz_id");
                String sql = "INSERT INTO [dbo].[Quiz_Question]\n"
                        + "           ([quiz_id]\n"
                        + "           ,[question_text]\n"
                        + "           ,[explanation]\n"
                        + "           ,[question_type],[is_visible])\n"
                        + "     VALUES\n"
                        + "           (?,?,?,?,?)";
                PreparedStatement stm = connection.prepareStatement(sql);
                System.out.println();
                stm.setInt(1, quizId);
                stm.setString(2, qq.getQuestion_text());
                stm.setString(3, qq.getExplanation());
                stm.setBoolean(4, qq.isQuestion_type());
                stm.setBoolean(5, true);
                int i = stm.executeUpdate();
                if (i > 0) {
                    String sql1 = "SELECT TOP(1) question_id FROM [dbo].[Quiz_Question] order by question_id desc";
                    PreparedStatement stm1 = connection.prepareStatement(sql1);
                    ResultSet rs1 = stm1.executeQuery();
                    if (rs1.next()) {
                        int questionId = rs1.getInt("question_id");
                        for (Choice choices : choice) {
                            String sql3 = "INSERT INTO [dbo].[Choice]\n"
                                    + "           ([question_id]\n"
                                    + "           ,[choice_text]\n"
                                    + "           ,[is_correct]\n"
                                    + "           ,[weight]\n"
                                    + "           ,[is_visible])\n"
                                    + "     VALUES\n"
                                    + "           (?,?,?,?,?)";
                            PreparedStatement stm3 = connection.prepareStatement(sql3);
                            stm3.setInt(1, questionId);
                            stm3.setString(2, choices.getChoice_text());
                            stm3.setBoolean(3, choices.isIs_correct());
                            stm3.setInt(4, choices.getWeight());
                            stm3.setBoolean(5, choices.isIs_visible());
                            stm3.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void addQ(Quiz_Question qq) {
//        try {
//            String sql = "INSERT INTO [dbo].[Quiz_Question]\n"
//                    + "           ([quiz_id]\n"
//                    + "           ,[question_text]\n"
//                    + "           ,[explanation]\n"
//                    + "           ,[question_img]\n"
//                    + "           ,[question_type])\n"
//                    + "     VALUES\n"
//                    + "           (?,?,?,?,?)";
//            PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setInt(1, qq.getQuiz_id().getQuiz_id());
//            stm.setString(2, qq.getQuestion_text());
//            stm.setString(3, qq.getExplanation());
//            stm.setBytes(4, qq.getQuestion_img());
//            stm.setBoolean(5, qq.isQuestion_type());
//            stm.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public ArrayList<Quiz_Question> getListQuizQuestion(int quiz_id) {
        ArrayList<Quiz_Question> quizQuestionList = new ArrayList<>();
        try {
            String sql = "select * from Quiz_Question where quiz_id=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quiz_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int question_id = rs.getInt(1);
                Quiz q = new Quiz(rs.getInt(2));
                String question_text = rs.getString(3);
                String explanation = rs.getString(4);
                Boolean question_type = rs.getBoolean(5);

                Quiz_Question qq = new Quiz_Question(question_id, q, question_text, explanation, question_type);
                quizQuestionList.add(qq);
            }
        } catch (SQLException e) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return quizQuestionList;
    }

//    public static void main(String[] args) {
//        Class c = new Class(4);
//        Quiz quiz = new Quiz(c, "LAB1");//classid va ten
//        Quiz q1 = new Quiz(c);
//        // Replace with the appropriate class_id
//
//        QuestionDAO qdao = new QuestionDAO();
//        String questionText = "dmaddnhanhdcm";
//        String explanation = "This is a geography question.";
//        byte[] imageQues = null;
//        boolean questionType1 = true;
//        Quiz_Question qq = new Quiz_Question(q1, questionText, explanation, imageQues, questionType1, true);
//        ArrayList<Choice> choices = new ArrayList<>();
//        Choice choice1 = new Choice();
//        choice1.setChoice_text("dmdapan");
//        choice1.setIs_correct(true); // Set to true for the correct answer
//        choice1.setWeight(1);
//        choice1.setImage(null); // Replace with actual image data
//        choice1.setIs_visible(true);
//        choices.add(choice1);
//        qdao.addQuestion(quiz, qq, choices);
//
//    }
//    public static void main(String[] args) {
//        Class c = new Class(4);
//        Quiz quiz = new Quiz(c, "LAB1");//classid va ten
//        Quiz q1 = new Quiz(c);
//
//        QuestionDAO qdao = new QuestionDAO();
//        String questionText = "dmaddnhanhdcm";
//        String explanation = "This is a geography question.";
//        byte[] imageQues = null;
//        boolean questionType1 = true;
//        Quiz_Question qq = new Quiz_Question(q1, questionText, explanation, imageQues, questionType1, true);
//        ArrayList<Choice> choices = new ArrayList<>();
//        Choice choice1 = new Choice();
//        choice1.setChoice_text("dmdapan");
//        choice1.setIs_correct(true);
//        choice1.setWeight(1);
//        choice1.setImage(null);
//        choice1.setIs_visible(true);
//        choices.add(choice1);
//        qdao.addQuestion(quiz, qq, choices);
//
//    }
    public Quiz_Question getQuestionAtIndex(int quiz_id, int index) {
        try {
            String sql = "WITH RankedQuestions AS (\n"
                    + "    SELECT *, ROW_NUMBER() OVER (ORDER BY question_id) AS rownum\n"
                    + "    FROM Quiz_Question\n"
                    + "    WHERE quiz_id = ?\n"
                    + ")\n"
                    + "SELECT *\n"
                    + "FROM RankedQuestions\n"
                    + "WHERE rownum = ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(2, index);
            stm.setInt(1, quiz_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Quiz q = new Quiz(rs.getInt(2));
                return new Quiz_Question(rs.getInt(1), q, rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getBoolean(6));
            }
        } catch (SQLException e) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public void submitCurrentChoices(int student_id, List<Integer> choice_ids, int question_id, int quiz_id) {
        try {
            String sql = "Insert into Student_Choice (student_id,student_choice,question_id,quiz_id) \n"
                    + "values (?,?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            for (int choice_id : choice_ids) {
                stm.setInt(1, student_id);
                stm.setInt(2, choice_id);
                stm.setInt(3, question_id);
                stm.setInt(4, quiz_id);
                stm.addBatch(); // Thêm câu lệnh SQL vào batch
            }
            stm.executeBatch(); // Thực hiện tất cả các câu lệnh trong batch cùng một lúc
        } catch (SQLException e) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean hasSelectedChoices(int student_id, int question_id, int quiz_id) {
        try {
            String sql = "SELECT COUNT(*) FROM Student_Choice WHERE student_id = ? AND question_id = ? AND quiz_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, student_id);
            stm.setInt(2, question_id);
            stm.setInt(3, quiz_id);

            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public void updateSelectedChoices(int student_id, List<Integer> choice_ids, int question_id, int quiz_id) {
        try {
            // Xóa các lựa chọn cũ của học sinh cho câu hỏi cụ thể
            String deleteSql = "DELETE FROM Student_Choice WHERE student_id = ? AND question_id = ? AND quiz_id = ?";
            PreparedStatement deleteStm = connection.prepareStatement(deleteSql);
            deleteStm.setInt(1, student_id);
            deleteStm.setInt(2, question_id);
            deleteStm.setInt(3, quiz_id);
            deleteStm.executeUpdate();

            // Thêm lại lựa chọn mới
            String insertSql = "INSERT INTO Student_Choice (student_id, student_choice, question_id, quiz_id) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStm = connection.prepareStatement(insertSql);
            for (int choice_id : choice_ids) {
                insertStm.setInt(1, student_id);
                insertStm.setInt(2, choice_id);
                insertStm.setInt(3, question_id);
                insertStm.setInt(4, quiz_id);
                insertStm.addBatch();
            }
            insertStm.executeBatch();
        } catch (SQLException e) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public int countTotalNumberQuestion(int quiz_id) {
        int totalQuestions = 0;
        try {
            String sql = "SELECT COUNT(*) AS total_questions\n"
                    + "FROM Quiz_Question\n"
                    + "WHERE quiz_id = ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quiz_id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                totalQuestions = rs.getInt("total_questions");
            }
            rs.close();
            stm.close();
        } catch (SQLException e) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return totalQuestions;
    }

    public void deleteAllStudentChoice(int student_id, int quiz_id) {
        try {
            String sql = "delete  from Student_Choice where student_id = ? and quiz_id =?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, student_id);
            stm.setInt(2, quiz_id);
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void deleteTeacherScore(int teacher_id, int quiz_id) {
        try {
            String sql = "delete  from Quiz_Result where student_id = ? and quiz_id =?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, teacher_id);
            stm.setInt(2, quiz_id);
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

//    public ArrayList<Choice> getListChoice(int quizId) {
//        ArrayList<Choice> choice = new ArrayList<>();
//        try {
//            String sql = "Select qq.question_type, qq.question_text, qq.explanation, qq.question_img, \n"
//                    + "c.question_id, c.choice_text, c.image, c.is_correct,c.weight from dbo.Quiz q\n"
//                    + "inner join dbo.Quiz_Question qq\n"
//                    + "on qq.quiz_id = q.quiz_id\n"
//                    + "inner join dbo.Choice c \n"
//                    + "on c.question_id = qq.question_id\n"
//                    + "where q.quiz_id = ?";
//            PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setInt(1, quizId);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//
//                Quiz_Question qq = new Quiz_Question();
//                qq.setQuestion_id(rs.getInt("question_id"));
//                qq.setQuestion_type(rs.getBoolean("question_type"));
//                qq.setQuestion_text(rs.getString("question_text"));
//                qq.setExplanation(rs.getString("explanation"));
//                qq.setQuestion_img(rs.getBytes("question_img"));
//
//                Choice c = new Choice();
//                c.setQuestion_id(qq);
//                c.setChoice_text(rs.getString("choice_text"));
//                c.setImage(rs.getBytes("image"));
//                c.setIs_correct(rs.getBoolean("is_correct"));
//                c.setWeight(rs.getInt("weight"));
//
//                choice.add(c);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return choice;
//    }
    public ArrayList<Choice> getListChoice(int quizId) {
        ArrayList<Choice> c = new ArrayList<>();
        try {
            String sql = "select qq.question_id, c.choice_id, c.choice_text, c.is_correct, c.weight from dbo.Choice c\n"
                    + "inner join dbo.Quiz_Question qq\n"
                    + "on qq.question_id = c.question_id\n"
                    + "inner join dbo.Quiz q \n"
                    + "on q.quiz_id = qq.quiz_id\n"
                    + "where q.quiz_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quizId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Quiz_Question qq = new Quiz_Question();
                qq.setQuestion_id(rs.getInt("question_id"));

                Choice c1 = new Choice();
                c1.setQuestion_id(qq);
                c1.setChoice_id(rs.getInt("choice_id"));
                c1.setChoice_text(rs.getString("choice_text"));
                c1.setIs_correct(rs.getBoolean("is_correct"));
                c1.setWeight(rs.getInt("weight"));
                c1.setWeight(rs.getInt("weight"));
                c.add(c1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    public void deleteQuestion(int quetionId, int studentId) {
        try {
            String sql0 = "DELETE FROM [dbo].[Student_Choice]\n"
                    + "      WHERE student_id = ?";
            PreparedStatement stm0 = connection.prepareStatement(sql0);
            stm0.setInt(1, studentId);
            stm0.executeUpdate();

            String sql1 = "DELETE FROM [dbo].[Quiz_Result]\n"
                    + "      WHERE student_id = ? and is_valid = 0";
            PreparedStatement stm1 = connection.prepareStatement(sql1);
            stm1.setInt(1, studentId);
            stm1.executeUpdate();
            
            String sql = "DELETE FROM [dbo].[Choice]\n"
                    + "      WHERE question_id = ? ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quetionId);
            stm.executeUpdate();

            String sql2 = "DELETE FROM [dbo].[Quiz_Question]\n"
                    + "      WHERE question_id = ?";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setInt(1, quetionId);
            stm2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateQuestion(Quiz_Question qq, ArrayList<Choice> choice) {
        try {
            String sql = "UPDATE [dbo].[Quiz_Question]\n"
                    + "   SET [question_text] = ?\n"
                    + "      ,[explanation] = ?\n"
                    + "      ,[question_type] = ?\n"
                    + " WHERE question_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, qq.getQuestion_text());
            stm.setString(2, qq.getExplanation());
            stm.setBoolean(3, qq.isQuestion_type());
            stm.setInt(4, qq.getQuestion_id());
            stm.executeUpdate();
            stm.close(); // Đóng PreparedStatement sau khi sử dụng

            String sql2 = "DELETE FROM [dbo].[Choice]\n"
                    + "      WHERE question_id = ?";
            PreparedStatement stm2 = connection.prepareStatement(sql2); // Sửa tên biến thành stm2
            stm2.setInt(1, qq.getQuestion_id());
            stm2.executeUpdate();
            stm2.close(); // Đóng PreparedStatement sau khi sử dụng

            for (Choice choices : choice) {
                String sql3 = "INSERT INTO [dbo].[Choice]\n"
                        + "           ([question_id]\n"
                        + "           ,[choice_text]\n"
                        + "           ,[is_correct]\n"
                        + "           ,[weight])\n"
                        + "     VALUES\n"
                        + "           (?,?,?,?)";
                PreparedStatement stm3 = connection.prepareStatement(sql3);
                stm3.setInt(1, qq.getQuestion_id());
                stm3.setString(2, choices.getChoice_text());
                stm3.setBoolean(3, choices.isIs_correct());
                stm3.setInt(4, choices.getWeight());
                stm3.executeUpdate();
                stm3.close(); // Đóng PreparedStatement sau khi sử dụng
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        QuestionDAO q = new QuestionDAO();
        Quiz_Question qq = new Quiz_Question(133, "ABCDEFGHIJKLMNOPQRSTUV", "iie", true);
        Quiz_Question qq1 = new Quiz_Question(133);
        Choice c1 = new Choice(qq1, "ABC", false, -100);
        Choice c2 = new Choice(qq1, "ABCDEF", false, -100);
        Choice c3 = new Choice(qq1, "ABCDEFGHIJKLMNOP", true, 100);
        Choice c4 = new Choice(qq1, "ABCDEFddddddddd", false, -100);
        ArrayList<Choice> cho = new ArrayList<>();
        cho.add(c1);
        cho.add(c2);
        cho.add(c3);
        cho.add(c4);
        q.updateQuestion(qq, cho);
    }

    public Quiz_Question getQuestionToEdit(int question_id) {
        try {
            String sql = "SELECT [question_id]\n"
                    + "      ,[question_text]\n"
                    + "      ,[explanation]\n"
                    + "      ,[question_type]\n"
                    + "  FROM [dbo].[Quiz_Question]\n"
                    + "  where question_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, question_id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Quiz_Question qq = new Quiz_Question();
                qq.setQuestion_id(question_id);
                qq.setQuestion_text(rs.getString(2));
                qq.setExplanation(rs.getString(3));
                qq.setQuestion_type(rs.getBoolean(4));
                return qq;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Choice> getChoicesOfQuestion(int question_id) {
        ArrayList<Choice> choice = new ArrayList<>();
        try {
            String sql = "SELECT choice_id, [choice_text]\n"
                    + "      ,[is_correct]\n"
                    + "      ,[weight]\n"
                    + "  FROM [dbo].[Choice]\n"
                    + "  where question_id =? ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, question_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Choice c = new Choice();
                c.setChoice_id(rs.getInt(1));
                c.setChoice_text(rs.getString(2));
                c.setIs_correct(rs.getBoolean(3));
                c.setWeight(rs.getInt(4));
                choice.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return choice;
    }

    public void addQuestionToQuestionBank(int course_id, Quiz_Question quizQuestion, ArrayList<Choice> choices) {
        try {
            String questionBankSql = "INSERT INTO [dbo].[Question_Bank] (course_id, question_text, explanation, question_type, is_visible) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement questionBankStm = connection.prepareStatement(questionBankSql, Statement.RETURN_GENERATED_KEYS);

            // Process a single Quiz_Question
            questionBankStm.setInt(1, course_id);
            questionBankStm.setString(2, quizQuestion.getQuestion_text());
            questionBankStm.setString(3, quizQuestion.getExplanation());
            questionBankStm.setBoolean(4, quizQuestion.isQuestion_type());
            questionBankStm.setBoolean(5, true);

            int insertedRows = questionBankStm.executeUpdate();

            if (insertedRows > 0) {
                ResultSet generatedKeys = questionBankStm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int question_id = generatedKeys.getInt(1);
                    System.out.println(question_id);//9
                    // Process each Choice for the current Quiz_Question
                    for (Choice c : choices) {
                        // Instead of comparing with Quiz_Question ID, compare with the Question_Bank ID
//                        if (c.getQuestion_id().getQuestion_id() == question_id) {
                        // Print Choice_Bank details for debugging
                        System.out.println("Choice Details:");
                        System.out.println("Question Bank ID: " + question_id);
                        System.out.println("Choice Bank Text: " + c.getChoice_text());
                        System.out.println("Is Correct: " + c.isIs_correct());
                        System.out.println("Weight: " + c.getWeight());

                        String choiceSql = "INSERT INTO [dbo].[Choice_Bank] (question_bank_id, choice_bank_text, is_correct, weight, is_visible) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement choiceStm = connection.prepareStatement(choiceSql);
                        choiceStm.setInt(1, question_id);
                        choiceStm.setString(2, c.getChoice_text());
                        choiceStm.setBoolean(3, c.isIs_correct());
                        choiceStm.setInt(4, c.getWeight());
                        choiceStm.setBoolean(5, true);
                        choiceStm.executeUpdate();
//                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
