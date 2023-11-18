package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;

public class AccountDAO extends DBContext {

    public Account getAccount(String email, String password) {
        try {
            String sql = "Select * from dbo.Account where email= ? and password= ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Account ac = new Account();
                ac.setId(rs.getInt(1));
                ac.setEmail(rs.getString(2));
                ac.setPassword(rs.getString(3));
                ac.setName(rs.getString(4));
                ac.setDob(rs.getDate(5));
                ac.setGender(rs.getBoolean(6));
                ac.setPhone(rs.getString(7));
                ac.setAddress(rs.getString(8));
                ac.setRole_id(rs.getInt(9));
                ac.setImg(rs.getString(10));
                ac.setIs_valid(rs.getBoolean(11));
                ac.setIs_login(rs.getBoolean(12));
                return ac;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Account getUsername(String email) {
        try {
            String sql = "Select * from dbo.Account where email= ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Account ac = new Account();
                ac.setId(rs.getInt(1));
                ac.setEmail(rs.getString(2));
                ac.setPassword(rs.getString(3));
                ac.setName(rs.getString(4));
                ac.setDob(rs.getDate(5));
                ac.setGender(rs.getBoolean(6));
                ac.setPhone(rs.getString(7));
                ac.setAddress(rs.getString(8));
                ac.setRole_id(rs.getInt(9));
                ac.setImg(rs.getString(10));
                ac.setIs_valid(rs.getBoolean(11));
                ac.setIs_login(rs.getBoolean(12));
                return ac;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getPhone(String phone) {
        try {
            String sql = "Select phone from dbo.Account where phone= ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, phone);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString("phone");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Account> getListAccount() {
        ArrayList<Account> accountList = new ArrayList<>();
        try {
            String sql = "Select * from Account";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account ac = new Account();
                ac.setId(rs.getInt(1));
                ac.setEmail(rs.getString(2));
                ac.setPassword(rs.getString(3));
                ac.setName(rs.getString(4));
                ac.setDob(rs.getDate(5));
                ac.setGender(rs.getBoolean(6));
                ac.setPhone(rs.getString(7));
                ac.setAddress(rs.getString(8));
                ac.setRole_id(rs.getInt(9));
                ac.setImg(rs.getString(10));
                ac.setIs_valid(rs.getBoolean(11));
                accountList.add(ac);
            }
        } catch (Exception e) {
        }
        return accountList;
    }

    public Account getAccountNotValid(String email, Date dob, Boolean gender, String phone) {
        try {
            String sql = "Select * from dbo.Account where email = ? and dob=? and gender=? and phone=? and is_valid = 0";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.setDate(2, dob);
            stm.setBoolean(3, gender);
            stm.setString(4, phone);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Account ac = new Account();
                ac.setId(rs.getInt(1));
                ac.setEmail(rs.getString(2));
                ac.setPassword(rs.getString(3));
                ac.setName(rs.getString(4));
                ac.setDob(rs.getDate(5));
                ac.setGender(rs.getBoolean(6));
                ac.setPhone(rs.getString(7));
                ac.setAddress(rs.getString(8));
                ac.setRole_id(rs.getInt(9));
                ac.setImg(rs.getString(10));
                ac.setIs_valid(rs.getBoolean(11));
                return ac;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Account addNewAccount(Account s) {
        try {
            String sql = "INSERT INTO [dbo].[Account]\n"
                    + "           ([email]\n"
                    + "           ,[password]\n"
                    + "           ,[name]\n"
                    + "           ,[dob]\n"
                    + "           ,[gender]\n"
                    + "           ,[phone]\n"
                    + "           ,[address]\n"
                    + "           ,[role_id]\n"
                    + "           ,[img]\n"
                    + "           ,[is_valid]\n" 
                    + "           ,[is_login])"
                    + "     VALUES\n"
                    + "           (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, s.getEmail());
            stm.setString(2, s.getPassword());
            stm.setString(3, s.getName());
            stm.setDate(4, s.getDob());
            stm.setBoolean(5, s.isGender());
            stm.setString(6, s.getPhone());
            stm.setString(7, s.getAddress());
            stm.setInt(8, s.getRole_id());
            stm.setString(9, s.getImg());
            stm.setBoolean(10, false);
            stm.setBoolean(11, true);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void confirmAccountInfo(String email) {
        try {
            String sql = "UPDATE [dbo].[Account]\n"
                    + "   SET [is_valid] = 1\n"
                    + " WHERE email= ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeAccountPass(Account acc) {
        try {
            String sql = "update Account set password=? where email= ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, acc.getPassword());
            stm.setString(2, acc.getEmail());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Account> getListStudentByName(String nameInput) {
        ArrayList<Account> stuList = new ArrayList<>();
        try {
            String sql = "select * from Account where role_id=3 and name like ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + nameInput + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String email = rs.getString(2);
                String password = rs.getString(3);
                String name = rs.getString(4);
                Date dob = rs.getDate(5);
                Boolean gender = rs.getBoolean(6);
                String phone = rs.getString(7);
                String address = rs.getString(8);
                String img = rs.getString(10);
                Account student = new Account(id, email, password, name, dob, gender, phone, address, 3, img);
                stuList.add(student);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return stuList;
    }

    public ArrayList<Account> getListStudentByName(String nameInput, int class_id) {
        ArrayList<Account> stuList = new ArrayList<>();
        try {
            String sql = "select a.id,a.email,a.name,a.dob,a.gender,a.address,a.phone from Student_Class scl\n"
                    + "	inner join Account a on a.id = scl.student_id\n"
                    + "where class_id = ? and a.name like ? and a.role_id = 3";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, class_id);
            stm.setString(2, "%" + nameInput + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String email = rs.getString(2);
                String name = rs.getString(3);
                Date dob = rs.getDate(4);
                Boolean gender = rs.getBoolean(5);
                String address = rs.getString(6);
                String phone = rs.getString(7);
                Account student = new Account(id, email, name, gender, phone, address, 3);
                stuList.add(student);

            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return stuList;
    }

    public ArrayList<Account> getListTeacherByName(String nameInput) {
        ArrayList<Account> teacherList = new ArrayList<>();
        try {
            String sql = "select * from Account where role_id=2 and is_valid=1 and name like ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + nameInput + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String email = rs.getString(2);
                String password = rs.getString(3);
                String name = rs.getString(4);
                Date dob = rs.getDate(5);
                Boolean gender = rs.getBoolean(6);
                String phone = rs.getString(7);
                String address = rs.getString(8);
                String img = rs.getString(10);
                Account student = new Account(id, email, password, name, dob, gender, phone, address, 2, img);
                teacherList.add(student);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return teacherList;
    }

    public Account getAdminById(int id) {
        try {
            String sql = "Select * from Account where id =? and role_id =1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String email = rs.getString(2);
                String pass = rs.getString(3);
                String name = rs.getString(4);
                Date date = rs.getDate(5);
                Boolean gender = rs.getBoolean(6);
                String phone = rs.getString(7);
                String address = rs.getString(8);
                String img = rs.getString(10);
                boolean valid = rs.getBoolean(11);
                return new Account(id, email, pass, name, date, gender, phone, address, 1, img, valid);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public ArrayList<Account> getListTeacher() {
        ArrayList<Account> teacherList = new ArrayList<>();
        try {
            String sql = "select * from Account where role_id=2 and is_valid=1 ";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String email = rs.getString(2);
                String password = rs.getString(3);
                String name = rs.getString(4);
                Date dob = rs.getDate(5);
                Boolean gender = rs.getBoolean(6);
                String phone = rs.getString(7);
                String address = rs.getString(8);
                String img = rs.getString(10);
                Account teacher = new Account(id, email, password, name, dob, gender, phone, address, 2, img);
                teacherList.add(teacher);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return teacherList;
    }

    public ArrayList<Account> getListStudent() {
        ArrayList<Account> stuList = new ArrayList<>();
        try {
            String sql = "select * from Account where role_id=3 and is_valid=1";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String email = rs.getString(2);
                String password = rs.getString(3);
                String name = rs.getString(4);
                Date dob = rs.getDate(5);
                Boolean gender = rs.getBoolean(6);
                String phone = rs.getString(7);
                String address = rs.getString(8);
                String img = rs.getString(10);
                Account student = new Account(id, email, password, name, dob, gender, phone, address, 3, img);
                stuList.add(student);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return stuList;
    }

    public ArrayList<Account> getListStudentNotInCourse(int course_id) {
        ArrayList<Account> stuList = new ArrayList<>();
        try {
            String sql = "Select * from Account a \n"
                    + "where a.id  NOT IN\n"
                    + "(select a1.id from Account a1\n"
                    + " join dbo.Student_Class sc \n"
                    + "on sc.student_id = a1.id\n"
                    + "join  dbo.Class c \n"
                    + "on c.class_id=sc.class_id\n"
                    + "where c.course_id =?) and a.role_id = 3 and a.is_valid=1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, course_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String email = rs.getString(2);
                String password = rs.getString(3);
                String name = rs.getString(4);
                Date dob = rs.getDate(5);
                Boolean gender = rs.getBoolean(6);
                String phone = rs.getString(7);
                String address = rs.getString(8);
                String img = rs.getString(10);
                Account student = new Account(id, email, password, name, dob, gender, phone, address, 3, img);
                stuList.add(student);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return stuList;
    }

    public Account getTeacherById(int id) {
        try {
            String sql = "Select * from Account where id =? and role_id =2 and is_valid=1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String email = rs.getString(2);
                String pass = rs.getString(3);
                String name = rs.getString(4);
                Date date = rs.getDate(5);
                Boolean gender = rs.getBoolean(6);
                String phone = rs.getString(7);
                String address = rs.getString(8);
                String img = rs.getString(10);
                boolean valid = rs.getBoolean(11);
                return new Account(id, email, pass, name, date, gender, phone, address, 2, img, valid);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public Account getStudentById(int id) {
        try {
            String sql = "Select * from Account where id =? and role_id =3";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String email = rs.getString(2);
                String pass = rs.getString(3);
                String name = rs.getString(4);
                Date date = rs.getDate(5);
                Boolean gender = rs.getBoolean(6);
                String phone = rs.getString(7);
                String address = rs.getString(8);
                String img = rs.getString(10);
                boolean valid = rs.getBoolean(11);
                return new Account(id, email, pass, name, date, gender, phone, address, 3, img, valid);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public void deleteAccountStudent(int id) {
        try {

            String sql1 = "delete Quiz_Result where student_id =?";
            PreparedStatement stm1 = connection.prepareStatement(sql1);
            stm1.setInt(1, id);
            stm1.executeUpdate();

            String sql2 = "delete Student_Choice where student_id =?";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setInt(1, id);
            stm2.executeUpdate();

            String sql3 = "delete Student_Class where student_id =?";
            PreparedStatement stm3 = connection.prepareStatement(sql3);
            stm3.setInt(1, id);
            stm3.executeUpdate();

            String sql4 = "delete Account where id =?";
            PreparedStatement stm4 = connection.prepareStatement(sql4);
            stm4.setInt(1, id);
            stm4.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void deleteAccountTeacher(int id) {
        try {
            String sql1 = "UPDATE [dbo].[Class]\n"
                    + "   SET [teacher_id] =[alter_teacher_id],[alter_teacher_id] = NULL\n"
                    + " WHERE teacher_id =?";
            PreparedStatement stm1 = connection.prepareStatement(sql1);
            stm1.setInt(1, id);
            stm1.executeUpdate();

            String sql2 = "UPDATE [dbo].[Class] SET [alter_teacher_id] = NULL WHERE alter_teacher_id = ?";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            stm2.setInt(1, id);
            stm2.executeUpdate();

            String sql3 = "UPDATE Account SET is_valid = 0, is_login = 0 WHERE id = ?";
            PreparedStatement stm3 = connection.prepareStatement(sql3);
            stm3.setInt(1, id);
            stm3.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateAccount(int id, String email, String name, Date dob, Boolean gender, String phone, String address) {
        try {
            String sql = "update Account\n"
                    + "set email=?,name=?,dob=?,gender=?,phone=?,address=?\n"
                    + "where id =?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, name);
            stm.setDate(3, dob);
            stm.setBoolean(4, gender);
            stm.setString(5, phone);
            stm.setString(6, address);
            stm.setInt(7, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public int countEmail(String email) {
        int count = 0;

        try {
            String sql = "SELECT COUNT(*) FROM Account WHERE email = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);

            // Thực thi câu lệnh SQL và nhận kết quả
            ResultSet rs = stm.executeQuery();

            // Di chuyển con trỏ đến dòng đầu tiên của kết quả
            if (rs.next()) {
                // Lấy giá trị đếm từ cột đầu tiên (COUNT(*))
                count = rs.getInt(1);
            }

            // Đóng tài nguyên
            rs.close();
            stm.close();

        } catch (SQLException e) {
            // Xử lý ngoại lệ SQL
            e.printStackTrace();
        }

        return count;
    }

    public void updatePhone(int id, String phone) {
        try {
            String sql = "Update Account set phone=? where id =?";
            PreparedStatement stm = connection.prepareCall(sql);
            stm.setString(1, phone);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public ArrayList<Account> getListStudentByQuizId(int quiz_id) {
        ArrayList<Account> listName = new ArrayList<>();
        try {
            String sql = "select \n"
                    + "Account.id, Account.name from Quiz INNER JOIN Class on Class.class_id = Quiz.class_id \n"
                    + "INNER JOIN Student_Class on Student_Class.class_id = Class.class_id\n"
                    + "INNER JOIN Account on Account.id = Student_Class.student_id\n"
                    + "where quiz_id = ? and role_id = 3 ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quiz_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setId(rs.getInt("id"));
                acc.setName(rs.getString("name"));
                listName.add(acc);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return listName;
    }

    public ArrayList<Account> getListMailtByQuizId(int quiz_id) {
        ArrayList<Account> listName = new ArrayList<>();
        try {
            String sql = "select \n"
                    + "Account.id, Account.name,  Account.email from Quiz INNER JOIN Class on Class.class_id = Quiz.class_id \n"
                    + "INNER JOIN Student_Class on Student_Class.class_id = Class.class_id\n"
                    + "INNER JOIN Account on Account.id = Student_Class.student_id\n"
                    + "where quiz_id = ? and role_id = 3 ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, quiz_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setId(rs.getInt("id"));
                acc.setName(rs.getString("name"));
                acc.setEmail(rs.getString("email"));
                listName.add(acc);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return listName;
    }

    public ArrayList<Account> getListMailtByClassId(int classId) {
        ArrayList<Account> listName = new ArrayList<>();
        try {
            String sql = "select  Account.id, Account.name,  Account.email from Account\n"
                    + "INNER JOIN Student_Class on Student_Class.student_id = Account.id\n"
                    + "where class_id = ? and Account.is_valid = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, classId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setId(rs.getInt("id"));
                acc.setName(rs.getString("name"));
                acc.setEmail(rs.getString("email"));
                listName.add(acc);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return listName;
    }

    public Account getAccountById(int id) {
        try {
            String sql = "Select * from Account where id =?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String email = rs.getString(2);
                String pass = rs.getString(3);
                String name = rs.getString(4);
                Date date = rs.getDate(5);
                Boolean gender = rs.getBoolean(6);
                String phone = rs.getString(7);
                String address = rs.getString(8);
                String img = rs.getString(10);
                boolean valid = rs.getBoolean(11);
                return new Account(id, email, pass, name, date, gender, phone, address, 3, img, valid);
            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public void updateImage(int id, String img) {
        try {
            String sql = "Update Account set img=? where id =?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, img);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
