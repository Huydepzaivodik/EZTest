package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;
import model.Major;
import model.Question_Bank;
import model.Semester;

public class CourseDAO extends DBContext {

    public ArrayList<Course> getListCourse() {
        ArrayList<Course> courseList = new ArrayList<>();
        try {
            String sql = "select * from Course";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Semester s = new Semester();
                Major m = new Major();
                Course c = new Course();

                c.setCourse_id(rs.getInt(1));
                c.setCourse_name(rs.getString(2));

                s.setSemester_id(rs.getInt(3));
                c.setSemester_id(s);

                m.setMajor_id(rs.getInt(4));
                c.setMajor_id(m);

                c.setIs_visible(rs.getBoolean(5));
                courseList.add(c);
            }
        } catch (SQLException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return courseList;
    }

    public Course addNewCourse(Course c) {
        try {
            String sql = "INSERT INTO [dbo].[Course]\n"
                    + "           ([course_name]\n"
                    + "           ,[semester_id]\n"
                    + "           ,[major_id]\n"
                    + "           ,[is_visible])\n"
                    + "     VALUES\n"
                    + "           (?,?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, c.getCourse_name());
            stm.setInt(2, c.getSemester_id().getSemester_id());
            stm.setInt(3, c.getMajor_id().getMajor_id());
            stm.setBoolean(4, c.isIs_visible());
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public Course getCourseByName(String name) {
        try {
            String sql = "select * from Course where course_name=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Semester s = new Semester();
                Major m = new Major();
                Course c = new Course();

                c.setCourse_id(rs.getInt(1));
                c.setCourse_name(rs.getString(2));

                s.setSemester_id(rs.getInt(3));
                c.setSemester_id(s);

                m.setMajor_id(rs.getInt(4));
                c.setMajor_id(m);

                c.setIs_visible(rs.getBoolean(5));
                return c;
            }
        } catch (SQLException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public Course getCourseById(int id) {
        try {
            String sql = "Select * from Course where course_id=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Semester s = new Semester();
                Major m = new Major();
                Course c = new Course();

                c.setCourse_id(rs.getInt(1));
                c.setCourse_name(rs.getString(2));

                s.setSemester_id(rs.getInt(3));
                c.setSemester_id(s);

                m.setMajor_id(rs.getInt(4));
                c.setMajor_id(m);

                c.setIs_visible(rs.getBoolean(5));
                return c;
            }
        } catch (SQLException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public void updateCourse(int course_id, String course_name, int semester_id, int major_id) {
        try {
            String sql = "update Course \n"
                    + "set course_name = ?,semester_id =? ,major_id =?\n"
                    + "where course_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, course_name);
            stm.setInt(2, semester_id);
            stm.setInt(3, major_id);
            stm.setInt(4, course_id);

            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public ArrayList<Course> getListCourseByTeacher(int teacher_id, int alter_teacher_id) {
        ArrayList<Course> courseList = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT c.course_id, c.course_name, c.semester_id, c.major_id "
                    + "FROM Course c "
                    + "INNER JOIN Class cl ON cl.course_id = c.course_id "
                    + "WHERE (cl.teacher_id = ? OR cl.alter_teacher_id = ?) AND c.is_visible = 'True'";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, teacher_id);
            stm.setInt(2, alter_teacher_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Semester s = new Semester();
                Major m = new Major();
                Course c = new Course();

                c.setCourse_id(rs.getInt("course_id"));
                c.setCourse_name(rs.getString("course_name"));

                s.setSemester_id(rs.getInt("semester_id"));
                c.setSemester_id(s);

                m.setMajor_id(rs.getInt("major_id"));
                c.setMajor_id(m);

                courseList.add(c);
            }
        } catch (SQLException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return courseList;
    }

    public ArrayList<Course> getListCourseByStudent(int student_id) {
        ArrayList<Course> courseList = new ArrayList<>();
        try {
            String sql = "select distinct c.course_id ,c.course_name, c.semester_id, c.major_id from Course c \n"
                    + "inner join Class cl on cl.course_id= c.course_id \n"
                    + "inner join Student_Class scl on scl.class_id= cl.class_id\n"
                    + "where student_id=? and c.is_visible = 'True'";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, student_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Semester s = new Semester();
                Major m = new Major();
                Course c = new Course();

                c.setCourse_id(rs.getInt(1));
                c.setCourse_name(rs.getString(2));

                s.setSemester_id(rs.getInt(3));
                c.setSemester_id(s);

                m.setMajor_id(rs.getInt(4));
                c.setMajor_id(m);

                courseList.add(c);
            }
        } catch (SQLException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return courseList;
    }

    public void deactivateCourse(int course_id) {
        try {
            String sql1 = "UPDATE Course SET is_visible = 0 WHERE course_id = ?";
            PreparedStatement stm1 = connection.prepareStatement(sql1);
            stm1.setInt(1, course_id);
            stm1.executeUpdate();

            String sql2 = "UPDATE Class SET is_visible = 0 WHERE course_id = ?";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setInt(1, course_id);
            stm2.executeUpdate();

            String sql3 = "UPDATE Quiz SET is_visible = 0 WHERE class_id IN (SELECT class_id FROM Class WHERE course_id = ?)";
            PreparedStatement stm3 = connection.prepareStatement(sql3);
            stm3.setInt(1, course_id);
            stm3.executeUpdate();

            String sql4 = "UPDATE Quiz_Question SET is_visible = 0 "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id IN (SELECT class_id FROM Class WHERE course_id = ?))";
            PreparedStatement stm4 = connection.prepareStatement(sql4);
            stm4.setInt(1, course_id);
            stm4.executeUpdate();

            String sql5 = "UPDATE Choice SET is_visible = 0 WHERE question_id IN (SELECT question_id FROM Quiz_Question "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id IN (SELECT class_id FROM Class WHERE course_id = ?)))";
            PreparedStatement stm5 = connection.prepareStatement(sql5);
            stm5.setInt(1, course_id);
            stm5.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void activateCourse(int course_id) {
        try {
            String sql1 = "UPDATE Course SET is_visible = 1 WHERE course_id = ?";
            PreparedStatement stm1 = connection.prepareStatement(sql1);
            stm1.setInt(1, course_id);
            stm1.executeUpdate();

            String sql2 = "UPDATE Class SET is_visible = 1 WHERE course_id = ?";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setInt(1, course_id);
            stm2.executeUpdate();

            String sql3 = "UPDATE Quiz SET is_visible = 1 WHERE class_id IN (SELECT class_id FROM Class WHERE course_id = ?)";
            PreparedStatement stm3 = connection.prepareStatement(sql3);
            stm3.setInt(1, course_id);
            stm3.executeUpdate();

            String sql4 = "UPDATE Quiz_Question SET is_visible = 1 "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id IN (SELECT class_id FROM Class WHERE course_id = ?))";
            PreparedStatement stm4 = connection.prepareStatement(sql4);
            stm4.setInt(1, course_id);
            stm4.executeUpdate();

            String sql5 = "UPDATE Choice SET is_visible = 1 WHERE question_id IN (SELECT question_id FROM Quiz_Question "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id IN (SELECT class_id FROM Class WHERE course_id = ?)))";
            PreparedStatement stm5 = connection.prepareStatement(sql5);
            stm5.setInt(1, course_id);
            stm5.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void deleteCourse(int course_id) {
        try {
            String sql1 = "delete from Student_Choice "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id IN (SELECT class_id FROM Class WHERE course_id = ?))";
            PreparedStatement stm1 = connection.prepareStatement(sql1);
            stm1.setInt(1, course_id);
            stm1.executeUpdate();

            String sql2 = "delete from Choice WHERE question_id IN (SELECT question_id FROM Quiz_Question "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id IN (SELECT class_id FROM Class WHERE course_id = ?)))";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setInt(1, course_id);
            stm2.executeUpdate();

            String sql3 = "delete from Quiz_Question "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id IN (SELECT class_id FROM Class WHERE course_id = ?))";
            PreparedStatement stm3 = connection.prepareStatement(sql3);
            stm3.setInt(1, course_id);
            stm3.executeUpdate();

            String sql4 = "delete from Quiz_Result "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id IN (SELECT class_id FROM Class WHERE course_id = ?))";
            PreparedStatement stm4 = connection.prepareStatement(sql4);
            stm4.setInt(1, course_id);
            stm4.executeUpdate();

            String sql5 = "delete from Quiz WHERE class_id IN (SELECT class_id FROM Class WHERE course_id = ?)";
            PreparedStatement stm5 = connection.prepareStatement(sql5);
            stm5.setInt(1, course_id);
            stm5.executeUpdate();

            String sql6 = "delete from Student_Class WHERE class_id IN (SELECT class_id FROM Class WHERE course_id = ?)";
            PreparedStatement stm6 = connection.prepareStatement(sql6);
            stm6.setInt(1, course_id);
            stm6.executeUpdate();

            String sql7 = "delete from Class WHERE course_id = ?";
            PreparedStatement stm7 = connection.prepareStatement(sql7);
            stm7.setInt(1, course_id);
            stm7.executeUpdate();

            String sql8 = "delete from Choice_Bank WHERE question_bank_id IN (SELECT question_bank_id FROM Question_Bank WHERE course_id = ?)";
            PreparedStatement stm8 = connection.prepareStatement(sql8);
            stm8.setInt(1, course_id);
            stm8.executeUpdate();

            String sql9 = "delete from Question_Bank WHERE course_id = ?";
            PreparedStatement stm9 = connection.prepareStatement(sql9);
            stm9.setInt(1, course_id);
            stm9.executeUpdate();

            String sql10 = "delete from Course WHERE course_id = ?";
            PreparedStatement stm10 = connection.prepareStatement(sql10);
            stm10.setInt(1, course_id);
            stm10.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public int getCourseIdByClassId(int classId) {
         // Initialize to a default value, you might want to use a different default value based on your requirements.

        try {
            String sql = "SELECT course_id FROM Class WHERE class_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, classId);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
}
