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
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import model.Report;

public class ExportExcel extends javax.swing.JFrame {

    ArrayList<Report> arr = new ArrayList<Report>();

    public void LoadDataToArrayList() {
        arr.clear();
        try {
            String user = "sa";
            String pass = "123";
            java.lang.Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://DESKTOP-D3I3VKE\\SQLEXPRESS:1433;databaseName=QuizPractice";
            Connection con = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT DISTINCT Quiz_Result.result_id, Quiz.quiz_id, Account.id, Account.name, Class.class_name, \n"
                    + "Quiz_Result.score, Quiz.start_time, Quiz.create_date\n"
                    + "                                        FROM Choice\n"
                    + "                                        INNER JOIN Quiz_Question ON Quiz_Question.question_id = Choice.question_id\n"
                    + "                                       INNER JOIN Quiz ON Quiz.quiz_id = Quiz_Question.quiz_id\n"
                    + "                                       INNER JOIN Quiz_Result ON Quiz_Result.quiz_id = Quiz.quiz_id\n"
                    + "                                        INNER JOIN Account ON Account.id = Quiz_Result.student_id\n"
                    + "                                        INNER JOIN Student_Class ON Student_Class.student_id = Account.id\n" +
            "                                        INNER JOIN Class ON Class.class_id = Student_Class.class_id";/////////
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int result_id = rs.getInt("result_id");
                int account_id = rs.getInt("id");
                String account_name = rs.getString("name");
                String class_name = rs.getString("class_name");
                float score = rs.getFloat("score");
                Timestamp start_time = rs.getTimestamp("start_time");
                Timestamp create_date = rs.getTimestamp("create_date");
                Report report = new Report(result_id, account_id, account_name, class_name, score, start_time, create_date);
                arr.add(report);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadDataArrayListToTable() {
        DefaultTableModel model = (DefaultTableModel) tbldanhsach.getModel();
        model.setRowCount(0);
        for (Report report : arr) {
            model.addRow(new Object[]{report.getResult_id(), report.getAccount_id(), report.getAccount_name(), report.getClass_name(),
                report.getScore(), report.getStart_time(), report.getCreate_date()
            });
        }
    }

    public ExportExcel() {
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
                    "Result_ID", "Student_ID", "Student_Name", "Class_Name", "Score", "Start_Time", "Create_Date"
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

        jButton1.setText("UPDATE DIEM");
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
            String url = "jdbc:sqlserver://DESKTOP-D3I3VKE\\SQLEXPRESS:1433;databaseName=QuizPractice";
            Connection con = DriverManager.getConnection(url, user, pass);

            String searchText = txttim.getText().trim();

            // Construct the SQL query to search using the LIKE operator with wildcard %
            String sql = "SELECT DISTINCT Quiz_Result.result_id, Account.id, Account.name, Class.class_name, Quiz_Result.score, Quiz.start_time, Quiz.create_date\n"
                    + "FROM Choice\n"
                    + "INNER JOIN Quiz_Question ON Quiz_Question.question_id = Choice.question_id\n"
                    + "INNER JOIN Quiz ON Quiz.quiz_id = Quiz_Question.quiz_id\n"
                    + "INNER JOIN Quiz_Result ON Quiz_Result.quiz_id = Quiz.quiz_id\n"
                    + "INNER JOIN Account ON Account.id = Quiz_Result.student_id\n"
                    + "INNER JOIN Student_Class ON Student_Class.student_id = Account.id\n"
                    + "INNER JOIN Class ON Class.class_id = Student_Class.class_id\n"
                    + "WHERE Account.name LIKE N'%" + searchText + "%' OR CAST(Quiz_Result.score AS NVARCHAR(50)) LIKE N'%" + searchText + "%'";

            System.out.println(sql);

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int result_id = rs.getInt("result_id");
                int account_id = rs.getInt("id");
                String account_name = rs.getString("name");
                String class_name = rs.getString("class_name");
                float score = rs.getFloat("score");
                Timestamp start_time = rs.getTimestamp("start_time");
                Timestamp create_date = rs.getTimestamp("create_date");
                Report report = new Report(result_id, account_id, account_name, class_name, score, start_time, create_date);
                arr.add(report);
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
        //  JOptionPane.showMessageDialog(this, "ban muon xoa khong");
    }//GEN-LAST:event_tbldanhsachMouseReleased

    public void xoa(int resultId) {
        int rows = 0;
        try {
            java.lang.Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://DESKTOP-D3I3VKE\\SQLEXPRESS:1433;databaseName=QuizPractice";
            Connection con = DriverManager.getConnection(url, "sa", "123");

            String sql = "DELETE FROM Quiz_Result WHERE result_id=?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, resultId);

            rows = stm.executeUpdate();

            if (rows >= 1) {
                System.out.println("Xóa báo cáo kiểm tra thành công");
                System.out.println(rows);
            } else {
                System.out.println(rows);
                System.out.println("Thất bại");
            }

            stm.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Cập nhật thất bại");
            System.out.println(rows);
        }
    }

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void xoaxoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xoaxoaMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_xoaxoaMouseClicked

    private void xoaxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaxoaActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(this, "Bạn Muốn Xoa Hay không?", "thông bao", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            int idxoa;
            int dong;
            dong = tbldanhsach.getSelectedRow();
            idxoa = (int) tbldanhsach.getValueAt(tbldanhsach.getSelectedRow(), 0);
            System.out.println(" " + idxoa);
            xoa(idxoa);
            LoadDataToArrayList();
            LoadDataArrayListToTable();
            JOptionPane.showMessageDialog(this, "xoa thanh cong");
        }
    }//GEN-LAST:event_xoaxoaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int rows = 0;
        try {
            java.lang.Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://DESKTOP-D3I3VKE\\SQLEXPRESS:1433;databaseName=QuizPractice";
            Connection con = DriverManager.getConnection(url, "sa", "123");
            System.out.println(tbldanhsach.getRowCount());
            //  System.out.println(tbldanhsach.getValueAt(0, 2));
            for (int i = 0; i < tbldanhsach.getRowCount(); i++) {
                String sql = "UPDATE Quiz_Result\n"
                        + "SET score = ?\n"
                        + "WHERE result_id = ?";
                PreparedStatement stm = con.prepareStatement(sql);
                float diem;
                int result_id;
                diem = Float.parseFloat(tbldanhsach.getValueAt(i, 4).toString());
                result_id = Integer.parseInt(tbldanhsach.getValueAt(i, 0).toString());
                stm.setFloat(1, diem);
                stm.setInt(2, result_id);
                System.out.println(result_id);
                System.out.println(diem);
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
            JOptionPane.showMessageDialog(this, "update diem thanh cong");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("update thất bại");

            System.out.println(rows);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            XSSFWorkbook wordkreport = new XSSFWorkbook();
            XSSFSheet sheet = wordkreport.createSheet("baocaoquy");
            XSSFRow row = null;
            Cell cell = null;
            row = sheet.createRow(2);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("BAO CAO QUY");

            row = sheet.createRow(3);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Result id");

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Student id");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Student name");

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Class name");

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Score");

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Start time");

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("Create date");

            for (int i = 0; i < arr.size(); i++) {
                //Report report =arr.get(i);
                row = sheet.createRow(4 + i);

                cell = row.createCell(0, CellType.NUMERIC);
                cell.setCellValue(i + 1);

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(arr.get(i).getResult_id());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(arr.get(i).getAccount_id());

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(arr.get(i).getAccount_name());

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(arr.get(i).getClass_name());

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(arr.get(i).getScore());

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(arr.get(i).getStart_date_string());

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(arr.get(i).getCreate_date_string());
            }
            File f = new File("E://baocaoquy.csv");
            try {
                FileOutputStream fis = new FileOutputStream(f);
                wordkreport.write(fis);
                fis.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "in thanh cong E:\\baocaoquy");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Loi mo file");
        }

    }//GEN-LAST:event_jButton3ActionPerformed
    int dongxoa = -1;

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_jButton4ActionPerformed
        int idxoa;
        dongxoa = tbldanhsach.getSelectedRow();
        if (dongxoa == -1) {
            JOptionPane.showMessageDialog(this, "bạn phai chon dong de xoa");
        } else {
            int ret = JOptionPane.showConfirmDialog(this, "Bạn Muốn Xoa Hay không?", "thông bao", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                idxoa = (int) tbldanhsach.getValueAt(tbldanhsach.getSelectedRow(), 0);

                System.out.println(" " + idxoa);
                xoa(idxoa);
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
            java.util.logging.Logger.getLogger(ExportExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExportExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExportExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExportExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ExportExcel().setVisible(true);
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
    //GEN-END:variables
}
