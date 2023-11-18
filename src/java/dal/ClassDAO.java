package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Course;
import model.Class;
import model.Student_Class;

public class ClassDAO extends DBContext {

    public ArrayList<Class> getListClass(int course_id) {
        ArrayList<Class> classList = new ArrayList<>();
        try {
            String sql = "select * from Class where course_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, course_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                Class cl = new Class();
                Account a = new Account();

                cl.setClass_id(rs.getInt(1));
                c.setCourse_id(rs.getInt(2));
                cl.setCourse_id(c);
                cl.setClass_name(rs.getString(3));
                a.setId(rs.getInt(4));
                cl.setTeacher_id(a);
                cl.setStatus(rs.getBoolean(5));
                cl.setIs_visible(rs.getBoolean(6));

                classList.add(cl);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return classList;
    }

    public ArrayList<Class> getListAlterTeacher(int course_id) {
        ArrayList<Class> classList = new ArrayList<>();
        try {
            String sql = "select class_id, alter_teacher_id from Class where course_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, course_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                Class cl = new Class();
                Account a = new Account();

                cl.setClass_id(rs.getInt(1));
                a.setId(rs.getInt(2));
                cl.setAlter_teacher_id(a);

                classList.add(cl);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return classList;
    }

    public int getAlterTeacherByClass(String class_name) {
        try {
            String sql = "select alter_teacher_id from Class where class_name = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, class_name);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("alter_teacher_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void updateAlter_teacher_id(int alter_teacher_id, int class_id) {
        try {
            String sql = "Update Class set alter_teacher_id=? where class_id=?";
            PreparedStatement stm = connection.prepareCall(sql);
            stm.setInt(1, alter_teacher_id);
            stm.setInt(2, class_id);
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Class addNewClass(Class cl) {
        try {
            String sql = "INSERT INTO [dbo].[Class]\n"
                    + "                             ([course_id]\n"
                    + "                              ,[class_name]\n"
                    + "                              ,[teacher_id])\n"
                    + "                        VALUES\n"
                    + "                            (?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, cl.getCourse_id().getCourse_id());
            stm.setString(2, cl.getClass_name());
            stm.setInt(3, cl.getTeacher_id().getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public Class getClassByName(int course_id, String class_name) {
        try {
            String sql = "select* from Class \n"
                    + "where course_id = ? and class_name = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, course_id);
            stm.setString(2, class_name);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Course c = new Course();
                Class cl = new Class();
                Account a1 = new Account();
                Account a2 = new Account();

                cl.setClass_id(rs.getInt(1));
                c.setCourse_id(rs.getInt(2));
                cl.setCourse_id(c);
                cl.setClass_name(rs.getString(3));
                a1.setId(rs.getInt(4));
                cl.setTeacher_id(a1);
                cl.setStatus(rs.getBoolean(5));
                cl.setIs_visible(rs.getBoolean(6));
                a2.setId(rs.getInt(7));
                cl.setAlter_teacher_id(a2);
                return cl;
            }
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public Class getClassByName(String name) {
        try {
            String sql = "select * from Class where class_name=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Course c = new Course();
                Class cl = new Class();
                Account a = new Account();

                cl.setClass_id(rs.getInt(1));
                c.setCourse_id(rs.getInt(2));
                cl.setCourse_id(c);
                cl.setClass_name(rs.getString(3));
                a.setId(rs.getInt(4));
                cl.setTeacher_id(a);
                cl.setStatus(rs.getBoolean(5));
                cl.setIs_visible(rs.getBoolean(6));

                return cl;
            }
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public ArrayList<Account> getListStudentInClass(int classId) {
        ArrayList<Account> stdList = new ArrayList<>();
        try {
            String sql = "select a.id,a.email,a.name,a.dob,a.gender,a.address,a.phone from Account a\n"
                    + "	inner join Student_Class sc on a.id = sc.student_id\n"
                    + "	inner join Class c on c.class_id = sc.class_id\n"
                    + "where c.class_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, classId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account a = new Account();
                a.setId(rs.getInt(1));
                a.setEmail(rs.getString(2));
                a.setName(rs.getString(3));
                a.setDob(rs.getDate(4));
                a.setGender(rs.getBoolean(5));
                a.setAddress(rs.getString(6));
                a.setPhone(rs.getString(7));
                stdList.add(a);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return stdList;
    }

    public void updateClass(int class_id, int course_id, String class_name, int teacher_id) {
        try {
            String sql = "update Class \n"
                    + "set course_id =?, class_name = ?, teacher_id =?\n"
                    + "where class_id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, course_id);
            stm.setString(2, class_name);
            stm.setInt(3, teacher_id);
            stm.setInt(4, class_id);
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public ArrayList<Class> getListClassByTeacher(int course_id, int teacher_id, int alter_teacher_id) {
        ArrayList<Class> classList = new ArrayList<>();
        try {
            String sql = "select * from Class where course_id = ? and (teacher_id=? or alter_teacher_id=?) and is_visible='True'";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, course_id);
            stm.setInt(2, teacher_id);
            stm.setInt(3, alter_teacher_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                Class cl = new Class();
                Account a = new Account();

                cl.setClass_id(rs.getInt(1));
                c.setCourse_id(rs.getInt(2));
                cl.setCourse_id(c);
                cl.setClass_name(rs.getString(3));
                a.setId(rs.getInt(4));
                cl.setTeacher_id(a);
                cl.setStatus(rs.getBoolean(5));
                cl.setIs_visible(rs.getBoolean(6));
                a.setId(rs.getInt(7));
                cl.setAlter_teacher_id(a);

                classList.add(cl);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return classList;
    }

    public Class getClassByStudent(int course_id, int student_id) {
        try {
            String sql = "select cl.class_id, cl.course_id, cl.class_name, cl.teacher_id from Class cl \n"
                    + "inner join Student_Class scl on scl.class_id = cl.class_id\n"
                    + "where course_id=? and student_id=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, course_id);
            stm.setInt(2, student_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                Class cl = new Class();
                Account a = new Account();

                cl.setClass_id(rs.getInt(1));
                c.setCourse_id(rs.getInt(2));
                cl.setCourse_id(c);
                cl.setClass_name(rs.getString(3));
                a.setId(rs.getInt(4));
                cl.setTeacher_id(a);

                return cl;
            }
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public void assignStudentInClass(int class_id, int student_id) {
        try {
            String sql = "Insert into Student_Class (class_id,student_id)\n"
                    + "values(?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, class_id);
            stm.setInt(2, student_id);
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void deleteAssignStudent(int class_id, int student_id) {
        try {
            String sql = "Delete from Student_Class where class_id=? and student_id=? ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, class_id);
            stm.setInt(2, student_id);
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Student_Class getStudentClassById(int class_id, int student_id) {
        try {
            String sql = "select *from Student_Class where class_id=? and student_id=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, class_id);
            stm.setInt(2, student_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account a = new Account();
                Class cl = new Class();

                Student_Class scl = new Student_Class();
                scl.setStudentclass_id(rs.getInt(1));
                cl.setClass_id(rs.getInt(2));
                scl.setClass_id(cl);
                a.setId(rs.getInt(3));
                scl.setStudent_id(a);
                return scl;
            }
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public void deactivateClass(int class_id) {
        try {
            String sql1 = "UPDATE Class SET is_visible = 0 WHERE class_id = ?";
            PreparedStatement stm1 = connection.prepareStatement(sql1);
            stm1.setInt(1, class_id);
            stm1.executeUpdate();

            String sql2 = "UPDATE Student_Class SET is_visible = 0 WHERE class_id = ?";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setInt(1, class_id);
            stm2.executeUpdate();

            String sql3 = "UPDATE Quiz SET is_visible = 0 WHERE class_id = ?";
            PreparedStatement stm3 = connection.prepareStatement(sql3);
            stm3.setInt(1, class_id);
            stm3.executeUpdate();

            String sql4 = "UPDATE Quiz_Question SET is_visible = 0 "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id =?)";
            PreparedStatement stm4 = connection.prepareStatement(sql4);
            stm4.setInt(1, class_id);
            stm4.executeUpdate();

            String sql5 = "UPDATE Choice SET is_visible = 0 WHERE question_id IN (SELECT question_id FROM Quiz_Question "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id =?))";
            PreparedStatement stm5 = connection.prepareStatement(sql5);
            stm5.setInt(1, class_id);
            stm5.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void activateClass(int class_id) {
        try {
            String sql1 = "UPDATE Class SET is_visible = 1 WHERE class_id = ?";
            PreparedStatement stm1 = connection.prepareStatement(sql1);
            stm1.setInt(1, class_id);
            stm1.executeUpdate();

            String sql2 = "UPDATE Student_Class SET is_visible = 1 WHERE class_id = ?";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setInt(1, class_id);
            stm2.executeUpdate();

            String sql3 = "UPDATE Quiz SET is_visible = 1 WHERE class_id = ?";
            PreparedStatement stm3 = connection.prepareStatement(sql3);
            stm3.setInt(1, class_id);
            stm3.executeUpdate();

            String sql4 = "UPDATE Quiz_Question SET is_visible = 1 "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id =?)";
            PreparedStatement stm4 = connection.prepareStatement(sql4);
            stm4.setInt(1, class_id);
            stm4.executeUpdate();

            String sql5 = "UPDATE Choice SET is_visible = 1 WHERE question_id IN (SELECT question_id FROM Quiz_Question "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id =?))";
            PreparedStatement stm5 = connection.prepareStatement(sql5);
            stm5.setInt(1, class_id);
            stm5.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Class getClassById(int id) {
        try {
            String sql = "Select * from Class where class_id=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Class cl = new Class();
                Course c = new Course();
                Account a = new Account();
                cl.setClass_id(rs.getInt(1));
                c.setCourse_id(rs.getInt(2));
                cl.setCourse_id(c);
                cl.setClass_name(rs.getString(3));
                a.setId(rs.getInt(4));
                cl.setTeacher_id(a);
                cl.setStatus(rs.getBoolean(5));
                cl.setIs_visible(rs.getBoolean(6));
                return cl;
            }
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public void deleteClass(int class_id) {
        try {
            String sql1 = "delete from Student_Choice "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id =?)";
            PreparedStatement stm1 = connection.prepareStatement(sql1);
            stm1.setInt(1, class_id);
            stm1.executeUpdate();

            String sql2 = "delete from Choice WHERE question_id IN (SELECT question_id FROM Quiz_Question "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id =?))";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setInt(1, class_id);
            stm2.executeUpdate();

            String sql3 = "delete from Quiz_Question "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id =?)";
            PreparedStatement stm3 = connection.prepareStatement(sql3);
            stm3.setInt(1, class_id);
            stm3.executeUpdate();

            String sql4 = "delete from Quiz_Result "
                    + "WHERE quiz_id IN (SELECT quiz_id FROM Quiz WHERE class_id =?)";
            PreparedStatement stm4 = connection.prepareStatement(sql4);
            stm4.setInt(1, class_id);
            stm4.executeUpdate();

            String sql5 = "delete from Quiz WHERE class_id=?";
            PreparedStatement stm5 = connection.prepareStatement(sql5);
            stm5.setInt(1, class_id);
            stm5.executeUpdate();

            String sql6 = "delete from Student_Class WHERE class_id=?";
            PreparedStatement stm6 = connection.prepareStatement(sql6);
            stm6.setInt(1, class_id);
            stm6.executeUpdate();

            String sql7 = "delete from Class WHERE class_id = ?";
            PreparedStatement stm7 = connection.prepareStatement(sql7);
            stm7.setInt(1, class_id);
            stm7.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public ArrayList<Class> selectListClassByTeacherID(int teacher_id) {
        ArrayList<Class> classList = new ArrayList<>();
        try {
            String sql = "select class_id, teacher_id, alter_teacher_id from Class where teacher_id=?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, teacher_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Class cl = new Class();
                Account a = new Account();
                Account a1 = new Account();

                cl.setClass_id(rs.getInt(1));
                a.setId(rs.getInt(2));
                cl.setTeacher_id(a);
                a1.setId(rs.getInt(3));
                cl.setAlter_teacher_id(a1);

                classList.add(cl);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return classList;
    }

}
