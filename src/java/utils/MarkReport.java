package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import model.Quiz_Report;

public class MarkReport extends javax.swing.JFrame {

    ArrayList<Quiz_Report> arr = new ArrayList<Quiz_Report>();

    public void LoadDataToArrayList() {
        arr.clear();
        try {
            String user = "sa";
            String pass = "123";
            java.lang.Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://BUT-CHI:1433;databaseName=QuizPractice";
            Connection con = DriverManager.getConnection(url, user, pass);
            String sql = "Select Account.id, Account.name, Quiz.quiz_id, Quiz.quiz_name, Choice.question_id, Student_Choice.student_choice, Choice.is_correct \n"
                    + "                     from Account INNER JOIN Student_Choice on Account.id = Student_Choice.student_id\n"
                    + "                     INNER JOIN Choice on Choice.choice_id = Student_Choice.student_choice\n"
                    + "                     INNER JOIN Quiz on Quiz.quiz_id = Student_Choice.quiz_id";/////////
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int account_id = rs.getInt("id");
                String account_name = rs.getString("name");
                int quiz_id = rs.getInt("quiz_id");
                String quiz_name = rs.getString("quiz_name");
                int question_id = rs.getInt("question_id");
                int student_choice = rs.getInt("student_choice");
                Boolean is_correct = rs.getBoolean("is_correct");
                Quiz_Report quiz_report = new Quiz_Report(account_id, account_name, quiz_id, quiz_name, question_id, student_choice, is_correct);
                arr.add(quiz_report);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadDataArrayListToTable() {
        DefaultTableModel model = (DefaultTableModel) tbldanhsach.getModel();
        model.setRowCount(0);
        for (Quiz_Report quiz_report : arr) {
            model.addRow(new Object[]{quiz_report.getAccount_id(), quiz_report.getAccount_name(), quiz_report.getQuiz_id(), quiz_report.getQuiz_name(), quiz_report.getQuestion_id(),
                quiz_report.getStudent_choice(), quiz_report.getIs_correct()
            });
        }
    }

    public MarkReport() {
        initComponents();
        LoadDataToArrayList();
        LoadDataArrayListToTable();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpopomenu = new javax.swing.JPopupMenu();
        xoaxoa = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        pupxoa1 = new javax.swing.JMenuItem();
        txttim = new javax.swing.JTextField();
        btnsearch = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldanhsach = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        xoaxoa.setText("Xoa");
        xoaxoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                xoaxoaMouseClicked(evt);
            }
        });
        xoaxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaxoaActionPerformed(evt);
            }
        });
        jpopomenu.add(xoaxoa);

        jMenuItem2.setText("Sua");
        jpopomenu.add(jMenuItem2);

        pupxoa1.setText("jMenuItem1");
        jPopupMenu1.add(pupxoa1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        txttim.setText("jTextField1");
        txttim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttimKeyReleased(evt);
            }
        });

        btnsearch.setText("Search");

        jButton2.setText("Exit");

        tbldanhsach.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                },
                new String[]{
                    "Student_ID", "Student_Name", "Quiz_ID", "Quiz_Name", "Question_ID", "Student_Choice", "Is_Correct"
                }
        ));
        tbldanhsach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbldanhsachMouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbldanhsachMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                tbldanhsachMouseExited(evt);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbldanhsachMousePressed(evt);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbldanhsachMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbldanhsach);

        jButton1.setText("UPDATE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("IN");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("xoa");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(115, 115, 115)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(txttim, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(61, 61, 61)
                                                                .addComponent(btnsearch)
                                                                .addGap(37, 37, 37)
                                                                .addComponent(jButton2))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jButton4)
                                                                .addGap(18, 18, 18))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(131, 131, 131)
                                                .addComponent(jButton1)
                                                .addGap(82, 82, 82)
                                                .addComponent(jButton3)))
                                .addContainerGap(87, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txttim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnsearch)
                                        .addComponent(jButton2))
                                .addGap(89, 89, 89)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4)
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton3))
                                .addContainerGap(94, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txttimKeyReleased(java.awt.event.KeyEvent evt) {
        arr.clear();

        try {
            String user = "sa";
            String pass = "123";
            java.lang.Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://BUT-CHI:1433;databaseName=QuizPractice";
            Connection con = DriverManager.getConnection(url, user, pass);

            String searchQuery = txttim.getText().trim();

            Boolean isCorrect = null;

            if (searchQuery.matches("(?i)(t(r(u(e)?)?)?)")) {
                isCorrect = true;
            } else if (searchQuery.matches("(?i)(f(a(l(s(e)?)?)?)?)")) {
                isCorrect = false;
            }

            // Truy vấn SQL sử dụng giá trị isCorrect hoặc không tìm kiếm is_correct nếu không có giá trị
            String sql = "Select Account.id, Account.name,Quiz.quiz_id, Quiz.quiz_name, Choice.question_id, Student_Choice.student_choice, Choice.is_correct \n"
                    + "                     from Account INNER JOIN Student_Choice on Account.id = Student_Choice.student_id\n"
                    + "                     INNER JOIN Choice on Choice.choice_id = Student_Choice.student_choice\n"
                    + "                     INNER JOIN Quiz on Quiz.quiz_id = Student_Choice.quiz_id";

            if (isCorrect != null) {
                sql += " WHERE (Choice.is_correct = ? OR Choice.is_correct IS NULL)";
            }

            PreparedStatement st = con.prepareStatement(sql);
            if (isCorrect != null) {
                st.setBoolean(1, isCorrect);
            }

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int account_id = rs.getInt("id");
                String account_name = rs.getString("name");
                int quiz_id = rs.getInt("quiz_id");
                String quiz_name = rs.getString("quiz_name");
                int question_id = rs.getInt("question_id");
                int student_choice = rs.getInt("student_choice");
                Boolean is_correct = rs.getBoolean("is_correct");
                if (!rs.wasNull()) {
                    Quiz_Report quiz_report = new Quiz_Report(account_id, account_name, quiz_id, quiz_name, question_id, student_choice, is_correct);
                    arr.add(quiz_report);
                }
            }

            con.close();
            LoadDataArrayListToTable();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_txttimKeyReleased

    private void tbldanhsachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldanhsachMouseClicked

    }//GEN-LAST:event_tbldanhsachMouseClicked

    private void tbldanhsachMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldanhsachMouseEntered
        // JOptionPane.showMessageDialog(this, "bạn muốn xóa không");

    }//GEN-LAST:event_tbldanhsachMouseEntered

    private void tbldanhsachMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldanhsachMousePressed

    }//GEN-LAST:event_tbldanhsachMousePressed

    private void tbldanhsachMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldanhsachMouseExited
        // JOptionPane.showMessageDialog(this, "ban muon xoa khong");
    }//GEN-LAST:event_tbldanhsachMouseExited

    private void tbldanhsachMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldanhsachMouseReleased
        
    }//GEN-LAST:event_tbldanhsachMouseReleased

    public void xoa(int question_id, int quiz_id, int student_id) {
        int rows = 0;
        try {
            java.lang.Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://BUT-CHI:1433;databaseName=QuizPractice";
            Connection con = DriverManager.getConnection(url, "sa", "123");
            String sql = "DELETE FROM Student_Choice WHERE question_id = ? AND quiz_id = ? AND student_id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, question_id);
            stm.setInt(2, quiz_id);
            stm.setInt(3, student_id);
            rows = stm.executeUpdate();

            if (rows >= 1) {
                System.out.println("Xóa thành công");
                System.out.println(rows);
            } else {
                System.out.println("Xóa thất bại");
            }

            stm.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Cập nhật thất bại");
        }
    }

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void xoaxoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xoaxoaMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_xoaxoaMouseClicked

    private void xoaxoaActionPerformed(java.awt.event.ActionEvent evt) {
        int ret = JOptionPane.showConfirmDialog(this, "Bạn Muốn Xoa Hay không?", "thông bao", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            int idxoa;
            int dong;
            dong = tbldanhsach.getSelectedRow();
            idxoa = (int) tbldanhsach.getValueAt(tbldanhsach.getSelectedRow(), 0);

            int questionId = (int) tbldanhsach.getValueAt(tbldanhsach.getSelectedRow(), 4); // Thay đổi giá trị questionId thành giá trị thích hợp
            int quizId = (int) tbldanhsach.getValueAt(tbldanhsach.getSelectedRow(), 2); // Thay đổi giá trị quizId thành giá trị thích hợp
            int studentId = (int) tbldanhsach.getValueAt(tbldanhsach.getSelectedRow(), 0); // Thay đổi giá trị studentId thành giá trị thích hợp
            xoa(questionId, quizId, studentId);
            LoadDataToArrayList();
            LoadDataArrayListToTable();
            JOptionPane.showMessageDialog(this, "xoa thanh cong");
        }
    }
//GEN-LAST:event_xoaxoaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int rows = 0;
        try {
            java.lang.Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://BUT-CHI:1433;databaseName=QuizPractice";
            Connection con = DriverManager.getConnection(url, "sa", "123");
            System.out.println(tbldanhsach.getRowCount());
            //  System.out.println(tbldanhsach.getValueAt(0, 2));
            for (int i = 0; i < tbldanhsach.getRowCount(); i++) {
                String sql = "update Books set price=? where id=?";
                PreparedStatement stm = con.prepareStatement(sql);
                float gia;
                int id;
                gia = Float.parseFloat(tbldanhsach.getValueAt(i, 2).toString());
                id = Integer.parseInt(tbldanhsach.getValueAt(i, 0).toString());
                stm.setFloat(1, gia);
                stm.setInt(2, id);
                System.out.println(gia);
                System.out.println(id);
                rows = stm.executeUpdate();
            }
            if (rows >= 1) {
                System.out.println("update thành công");
                System.out.println(rows);
            } else {
                System.out.println(rows);
                System.out.println("chưa update được");
            }
            con.close();
            LoadDataToArrayList();
            LoadDataArrayListToTable();
            JOptionPane.showMessageDialog(this, "update gia thanh cong");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("update thất bại");
            System.out.println(rows);
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    //In kiểu 1

    private void jButton3ActionPerformed3(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            XSSFWorkbook wordkquiz_report = new XSSFWorkbook();
            XSSFSheet sheet = wordkquiz_report.createSheet("baocaoquiz");
            XSSFRow row = null;
            Cell cell = null;
            row = sheet.createRow(2);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("BAO CAO QUIZ");

            row = sheet.createRow(3);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("ID hoc sinh");

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Ten hoc sinh");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Ten quiz");

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Cau hoi so");

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Dap an hoc sinh");

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Ket qua");

            for (int i = 0; i < arr.size(); i++) {
                //Report quiz_report =arr.get(i);
                row = sheet.createRow(4 + i);

                cell = row.createCell(0, CellType.NUMERIC);
                cell.setCellValue(i + 1);

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(arr.get(i).getAccount_id());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(arr.get(i).getAccount_name());

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(arr.get(i).getQuiz_name());

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(arr.get(i).getQuestion_id());

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(arr.get(i).getStudent_choice());

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(arr.get(i).getIs_correct());
            }

            File f = new File("E://baocaoquiz.csv");
            try {
                FileOutputStream fis = new FileOutputStream(f);
                wordkquiz_report.write(fis);
                fis.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "in thanh cong E:\\baocaoquiz");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Loi mo file");
        }
    }

    //In kiểu2
    private void jButton3ActionPerformed2(java.awt.event.ActionEvent evt) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("baocaoquiz");

            // Create headers
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0, CellType.STRING).setCellValue("Student Name");

            // Fill headers with question numbers
            int maxQuestionId = 0;
            for (Quiz_Report quizReport : arr) {
                if (quizReport.getQuestion_id() > maxQuestionId) {
                    maxQuestionId = quizReport.getQuestion_id();
                }
            }
            for (int i = 1; i <= maxQuestionId; i++) {
                headerRow.createCell(i, CellType.STRING).setCellValue("Q" + i);
            }

            // Fill data
            int rowNum = 1;
            for (Quiz_Report quizReport : arr) {
                XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0, CellType.STRING).setCellValue(quizReport.getAccount_name());
                row.createCell(quizReport.getQuestion_id(), CellType.STRING).setCellValue(quizReport.getIs_correct() ? "Correct" : "Incorrect");
            }

            File outputFile = new File("E://baocaoquiz.csv");
            try ( FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                workbook.write(fileOutputStream);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "Export successful. File saved at E://baocaoquiz.csv");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error exporting data");
        }
    }//GEN-LAST:event_jButton3ActionPerformed
    

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//In final
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("baocaoquiz");

            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0, CellType.STRING).setCellValue("Student Name");

            Set<Integer> uniqueQuestionIds = new HashSet<>();
            for (Quiz_Report quizReport : arr) {
                uniqueQuestionIds.add(quizReport.getQuestion_id());
            }

            int colNum = 1;
            for (Integer questionId : uniqueQuestionIds) {
                headerRow.createCell(colNum++, CellType.STRING).setCellValue("Q" + questionId);
            }

            // Group data by student
            Map<String, Map<Integer, String>> studentData = new HashMap<>();
            for (Quiz_Report quizReport : arr) {
                String studentName = quizReport.getAccount_name();
                int questionId = quizReport.getQuestion_id();
                String result = quizReport.getIs_correct() ? "Correct" : "Incorrect";

                studentData.computeIfAbsent(studentName, k -> new HashMap<>()).put(questionId, result);
            }

            // Fill data
            int rowNum = 1;
            for (Map.Entry<String, Map<Integer, String>> entry : studentData.entrySet()) {
                XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0, CellType.STRING).setCellValue(entry.getKey());

                Map<Integer, String> questionResults = entry.getValue();
                colNum = 1;
                for (Integer questionId : uniqueQuestionIds) {
                    String result = questionResults.getOrDefault(questionId, "");
                    row.createCell(colNum++, CellType.STRING).setCellValue(result);
                }
            }

            File outputFile = new File("E://baocaoquiz.csv");
            try ( FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                workbook.write(fileOutputStream);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "Export successful. File saved at E://baocaoquiz.csv");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error exporting data");
        }
    }//GEN-LAST:event_jButton3ActionPerformed
    int dongxoa = -1;

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        int idxoa;
        dongxoa = tbldanhsach.getSelectedRow();
        if (dongxoa == -1) {
            JOptionPane.showMessageDialog(this, "bạn phai chon dong de xoa");
        } else {
            int ret = JOptionPane.showConfirmDialog(this, "Bạn Muốn Xoa Hay không?", "thông bao", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                idxoa = (int) tbldanhsach.getValueAt(tbldanhsach.getSelectedRow(), 0);
                int questionId = (int) tbldanhsach.getValueAt(tbldanhsach.getSelectedRow(), 4); // Thay đổi giá trị questionId thành giá trị thích hợp
                int quizId = (int) tbldanhsach.getValueAt(tbldanhsach.getSelectedRow(), 2); // Thay đổi giá trị quizId thành giá trị thích hợp
                int studentId = (int) tbldanhsach.getValueAt(tbldanhsach.getSelectedRow(), 0); // Thay đổi giá trị studentId thành giá trị thích hợp
                xoa(questionId, quizId, studentId);
                LoadDataToArrayList();
                LoadDataArrayListToTable();
                JOptionPane.showMessageDialog(this, "xoa thanh cong");
            }
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MarkReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MarkReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MarkReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MarkReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MarkReport().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnsearch;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu jpopomenu;
    private javax.swing.JMenuItem pupxoa1;
    private javax.swing.JTable tbldanhsach;
    private javax.swing.JTextField txttim;
    private javax.swing.JMenuItem xoaxoa;
    // End of variables declaration//GEN-END:variables
}
