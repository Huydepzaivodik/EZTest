package dal;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.File;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelDAO extends DBContext {

    public List<Integer> importDataFromExcel(String excelFilePath, int quiz_id) {
        List<Integer> questionIds = new ArrayList<>();

        try {
            String insertQuizQuestionQuery = "INSERT INTO Quiz_Question (quiz_id, question_text, explanation, question_img, question_type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement questionStatement = connection.prepareStatement(insertQuizQuestionQuery, Statement.RETURN_GENERATED_KEYS);

            FileInputStream excelFile = new FileInputStream(excelFilePath);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell cellQuestionText = row.getCell(0);
                Cell cellExplanation = row.getCell(1);
                Cell cellQuestionImg = row.getCell(2);
                Cell cellQuestionType = row.getCell(3);

                if (cellQuestionText != null && cellExplanation != null && cellQuestionImg != null && cellQuestionType != null) {
                    String question_text = cellQuestionText.getStringCellValue();
                    String explanation = cellExplanation.getStringCellValue();
                    String imgPath = cellQuestionImg.getStringCellValue();
                    Boolean question_type = cellQuestionType.getBooleanCellValue();

                    Path path = Paths.get(imgPath);
                    byte[] imgBytes = Files.readAllBytes(path);

                    questionStatement.setInt(1, quiz_id);
                    questionStatement.setString(2, question_text);
                    questionStatement.setString(3, explanation);
                    questionStatement.setBytes(4, imgBytes);
                    questionStatement.setBoolean(5, question_type);

                    int affectedRows = questionStatement.executeUpdate();

                    if (affectedRows > 0) {
                        ResultSet generatedKeys = questionStatement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int question_id = generatedKeys.getInt(1);
                            questionIds.add(question_id);
                            System.out.println("Inserted question with ID: " + question_id);
                        }
                    }
                }
            }

            questionStatement.close();
            excelFile.close();
            workbook.close();

            System.out.println("Imported data from Excel successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questionIds;
    }

    public void importAnswersFromExcel(String excelFilePath, List<Integer> questionIds) {
        try {
            FileInputStream excelFile = new FileInputStream(excelFilePath);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(1); // Chọn sheet chứa đáp án

            String insertChoiceQuery = "INSERT INTO Choice (question_id, choice_text, is_correct, weight, image) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertChoiceQuery);

            int questionIndex = 0;

            for (Row row : sheet) {
                int columnIndex = 0;

                for (int i = 0; i < 3; i++) {
                    Cell cell = row.getCell(columnIndex);
                    if (cell != null) {
                        String choice_text = cell.getStringCellValue();
                        boolean is_correct = row.getCell(columnIndex + 1).getBooleanCellValue();
                        int weight = (int) row.getCell(columnIndex + 2).getNumericCellValue();
                        int question_id = questionIds.get(questionIndex);
                        // Điều chỉnh dữ liệu ảnh thành mảng byte tại đây
                        Cell cellImg = row.getCell(3);
                        String imgPath = cellImg.getStringCellValue();
                        Path path = Paths.get(imgPath);
                        byte[] imgBytes = Files.readAllBytes(path);

                        preparedStatement.setInt(1, question_id);
                        preparedStatement.setString(2, choice_text);
                        preparedStatement.setBoolean(3, is_correct);
                        preparedStatement.setInt(4, weight);
                        preparedStatement.setBytes(5, imgBytes);

                        preparedStatement.executeUpdate();
                    }

                    columnIndex += 4;
                }
                questionIndex++;
            }

            preparedStatement.close();
            excelFile.close();
            workbook.close();

            System.out.println("Imported data from Excel successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getExcelFilePath(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = getSubmittedFileName(filePart);
            String savePath = request.getServletContext().getRealPath("/uploads");
            File saveDir = new File(savePath);

            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            String filePath = savePath + File.separator + fileName;
            InputStream inputStream = null;
            try {
                inputStream = filePart.getInputStream();
                Files.copy(inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            } finally {
                closeIs(inputStream);
            }

            return filePath;
        } else {
            return null;
        }
    }

    private String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName;
            }
        }
        return null;
    }

    //Hàm check email tồn tồn
    public boolean checkEmailExists(String email) {
        try {
            String query = "SELECT COUNT(*) FROM Account WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Hàm check phone tồn tồn
    public boolean checkPhoneExists(String phone) {
        try {
            String query = "SELECT COUNT(*) FROM Account WHERE phone = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, phone);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Integer> importStudentDataFromExcel(String excelFilePath) {
        AccountDAO aDAO = new AccountDAO();
        List<Integer> ids = new ArrayList<>();
        //CHECK EMAIL VÀ PHONE
        //CHẠY HÀM LẤY LIST EMAIL LIST EMAIL
        try {
            String sql = "INSERT INTO Account (email, password, name, dob, gender, phone, address, role_id, img, is_valid)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement studentStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            FileInputStream excelFile = new FileInputStream(excelFilePath);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);

            int role_id = 3; // Import học sinh
            boolean is_valid = true; // Mới thêm nên là active
            for (Row row : sheet) {
                Cell cellEmail = row.getCell(0);
                Cell cellPassword = row.getCell(1);
                Cell cellName = row.getCell(2);
                Cell cellDob = row.getCell(3);
                Cell cellGender = row.getCell(4);
                Cell cellPhone = row.getCell(5);
                Cell cellAddress = row.getCell(6);
                Cell cellImg = row.getCell(7);//

//                System.out.println("Email Cell Type: " + cellEmail.getCellType());
//                System.out.println("Password Cell Type: " + cellPassword.getCellType());
                try {
                    if (cellEmail == null || cellEmail.getCellType() == CellType.BLANK || cellEmail.getStringCellValue().trim().isEmpty()) {
                        System.out.println("Skipping row due to missing email.");
                    } else {
                        // Process the valid data here
                        System.out.println("Valid email: " + cellEmail.getStringCellValue());
                    }
                } catch (IllegalStateException e) {
                    // Handle the case when the cell contains invalid data format
                    System.out.println("Error reading cell value. Invalid data format.");
                    e.printStackTrace(); // This prints the stack trace for more details on the error
                } catch (Exception e) {
                    // Handle other exceptions
                    System.out.println("An error occurred: " + e.getMessage());
                    e.printStackTrace(); // This prints the stack trace for more details on the error
                }
                try {
                    if (cellPassword == null || cellPassword.getCellType() == CellType.BLANK || cellPassword.getStringCellValue().trim().isEmpty()) {
                        System.out.println("Skipping row due to missing password.");
                    } else {
                        // Process the valid data here
                        System.out.println("Valid password: " + cellPassword.getStringCellValue());
                    }
                } catch (IllegalStateException e) {
                    // Handle the case when the cell contains invalid data format
                    System.out.println("Error reading cell value. Invalid data format.");
                    e.printStackTrace(); // This prints the stack trace for more details on the error
                } catch (Exception e) {
                    // Handle other exceptions
                    System.out.println("An error occurred: " + e.getMessage());
                    e.printStackTrace(); // This prints the stack trace for more details on the error
                }
                try {
                    if (cellName == null || cellName.getCellType() == CellType.BLANK || cellName.getStringCellValue().trim().isEmpty()) {
                        System.out.println("Skipping row due to missing name.");
                    } else {
                        // Process the valid data here
                        System.out.println("Valid name: " + cellName.getStringCellValue());
                    }
                } catch (IllegalStateException e) {
                    // Handle the case when the cell contains invalid data format
                    System.out.println("Error reading cell value. Invalid data format.");
                    e.printStackTrace(); // This prints the stack trace for more details on the error
                } catch (Exception e) {
                    // Handle other exceptions
                    System.out.println("An error occurred: " + e.getMessage());
                    e.printStackTrace(); // This prints the stack trace for more details on the error
                }

                if (cellDob != null) {
                    String dob;
                    if (cellDob.getCellType() == CellType.STRING) {
                        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
                        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = inputFormat.parse(cellDob.getStringCellValue());
                        dob = outputFormat.format(date);
                    } else if (cellDob.getCellType() == CellType.NUMERIC) {
                        if (DateUtil.isCellDateFormatted(cellDob)) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            dob = dateFormat.format(cellDob.getDateCellValue());
                        } else {
                            dob = String.valueOf(cellDob.getNumericCellValue());
                        }
                    } else {
                        System.out.println("date of birth bị lỗi");
                        continue; // hoặc return hoặc thông báo lỗi tùy thuộc vào yêu cầu cụ thể
                    }

                    if (cellEmail != null && cellPassword != null && cellName != null && dob != null && cellGender != null
                            && cellPhone != null && cellAddress != null && cellImg != null) {//
                        String email = "";
                        if (cellEmail.getCellType() == CellType.STRING) {
                            email = cellEmail.getStringCellValue();
                        } else if (cellEmail.getCellType() == CellType.NUMERIC) {
                            email = String.valueOf(cellEmail.getNumericCellValue());
                        } else {
                            System.out.println("mail bị lỗi");
                            continue; // hoặc return hoặc thông báo lỗi tùy thuộc vào yêu cầu cụ thể
                        }

                        String password = cellPassword.getStringCellValue();
                        String name = cellName.getStringCellValue();

                        // Check xem email này tồn tại chưa?
                        if (checkEmailExists(email)) {
                            continue;
                        }

                        Boolean gender = cellGender.getBooleanCellValue();

                        String phone = "";
                        if (cellPhone.getCellType() == CellType.STRING) {
                            phone = cellPhone.getStringCellValue();
                        } else if (cellPhone.getCellType() == CellType.NUMERIC) {
                            phone = String.valueOf((long) cellPhone.getNumericCellValue());
                        }
                        if (checkPhoneExists(phone)) {
                            System.out.println("Phone bị trùng");
                            continue;
                        }

                        String address = cellAddress.getStringCellValue();

                        String img = cellImg.getStringCellValue();

                        studentStatement.setString(1, email);
                        studentStatement.setString(2, password);
                        studentStatement.setString(3, name);
                        studentStatement.setString(4, dob);
                        studentStatement.setBoolean(5, gender);
                        studentStatement.setString(6, phone);
                        studentStatement.setString(7, address);
                        studentStatement.setInt(8, role_id);
                        studentStatement.setString(9, img);
                        studentStatement.setBoolean(10, is_valid);

                        int affectedRows = studentStatement.executeUpdate();

                        if (affectedRows > 0) {
                            ResultSet generatedKeys = studentStatement.getGeneratedKeys();
                            if (generatedKeys.next()) {
                                int id = generatedKeys.getInt(1);
                                ids.add(id);

                                System.out.println("Inserted student with ID: " + id);
                            }
                        }
                    }
                }
            }

            studentStatement.close();
            excelFile.close();
            workbook.close();

            System.out.println("Imported data from Excel successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public void addStudentToStudentClassFromExcel(List<Integer> ids, int classId) {
        try {
            String sql = "INSERT INTO Student_Class (class_id, student_id) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (Integer studentId : ids) {
                preparedStatement.setInt(1, classId);
                preparedStatement.setInt(2, studentId);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

            preparedStatement.close();
            System.out.println("Imported data into Student_Class table successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String ExportMarkClass(int class_id, int quiz_id, String course_name, String class_name, String quiz_name) {
        String filePath = "";
        try {
            String exportQuery = ("SELECT DISTINCT Quiz_Result.result_id, Quiz.quiz_name, Account.id as student_id, Account.name as student_name, Class.class_name as class_name, \n"
                    + "                    Quiz_Result.score, Quiz.start_time, Quiz.create_date\n"
                    + "                    FROM Choice\n"
                    + "                    INNER JOIN Quiz_Question ON Quiz_Question.question_id = Choice.question_id\n"
                    + "                    INNER JOIN Quiz ON Quiz.quiz_id = Quiz_Question.quiz_id\n"
                    + "                    INNER JOIN Quiz_Result ON Quiz_Result.quiz_id = Quiz.quiz_id\n"
                    + "                    INNER JOIN Account ON Account.id = Quiz_Result.student_id\n"
                    + "                    INNER JOIN Student_Class ON Student_Class.student_id = Account.id\n"
                    + "                    INNER JOIN Class ON Class.class_id = Student_Class.class_id\n"
                    + "                    where Class.class_id=? and Quiz.quiz_id=?");
            PreparedStatement preparedStatement = connection.prepareStatement(exportQuery);
            preparedStatement.setInt(1, class_id);
            preparedStatement.setInt(2, quiz_id);
            ResultSet rs = preparedStatement.executeQuery();

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Dữ liệu từ SQL");

            int rowNum = 0;

            // Tạo một mảng chứa tên của từng cột
            String[] columnNames = {"Result ID", "Quiz Name", "Student ID", "Student Name", "Class Name", "Score", "Start Time", "Create Date"};

            // Tạo hàng đầu tiên và thêm tên cột
            Row headerRow = sheet.createRow(rowNum);
            for (int i = 0; i < columnNames.length; i++) {
                headerRow.createCell(i).setCellValue(columnNames[i]);
            }
            rowNum++; // Tăng giá trị của rowNum lên 1 để dữ liệu bắt đầu từ hàng 1

            // Ghi dữ liệu từ result set vào các hàng tiếp theo
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnNames.length; i++) {
                    row.createCell(i - 1).setCellValue(rs.getString(i));
                }
            }

            // Thêm hàng chú thích
            Row additionalRow = sheet.createRow(rowNum + 2);
            additionalRow.createCell(0).setCellValue("Note: ");
            additionalRow.createCell(1).setCellValue("This is student score data in specific classes and tests.");

            String outputDirectory = "";

// Kiểm tra xem ổ đĩa D có sẵn hay không
            File dDrive = new File("D:");
            if (dDrive.exists() && dDrive.isDirectory()) {
                outputDirectory = "D:";
            } else {
                // Nếu ổ đĩa D không tồn tại, sử dụng ổ đĩa E
                outputDirectory = "E:";
            }
            File directory = new File(outputDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = outputDirectory + "/" + course_name + "_" + class_name + "_" + quiz_name + "_Score.xlsx";
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();

            File file = new File(course_name + "_" + class_name + "_" + quiz_name + "_Score.xlsx");
            String absolutePath = file.getAbsolutePath();
            filePath = absolutePath;
            System.out.println("Path to file: " + absolutePath);

            System.out.println("The point data was successfully exported to Excel!");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return filePath;
    }

    public String exportStudentList(int class_id, String class_name) {
        String filePath = "";
        try {
            String exportQuery = ("SELECT DISTINCT Account.id, Account.name, Account.email, Class.class_name "
                    + "FROM Account "
                    + "INNER JOIN Student_Class ON Student_Class.student_id = Account.id "
                    + "INNER JOIN Class ON Class.class_id = Student_Class.class_id "
                    + "WHERE Class.class_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(exportQuery);
            preparedStatement.setInt(1, class_id);
            ResultSet rs = preparedStatement.executeQuery();

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh sách sinh viên");

            int rowNum = 0;

            // Tạo một mảng chứa tên của từng cột
            String[] columnNames = {"Student ID", "Name", "Email", "Class Name"};

            // Tạo hàng đầu tiên và thêm tên cột
            Row headerRow = sheet.createRow(rowNum);
            for (int i = 0; i < columnNames.length; i++) {
                headerRow.createCell(i).setCellValue(columnNames[i]);
            }
            rowNum++; // Tăng giá trị của rowNum lên 1 để dữ liệu bắt đầu từ hàng 1

            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnNames.length; i++) {
                    row.createCell(i - 1).setCellValue(rs.getString(i));
                }
            }

            Row additionalRow = sheet.createRow(rowNum + 2);
            additionalRow.createCell(0).setCellValue("Note: ");
            additionalRow.createCell(1).setCellValue("This is the data of list student in class" + class_name + ".");

            String outputDirectory = "";

// Kiểm tra xem ổ đĩa D có sẵn hay không
            File dDrive = new File("D:");
            if (dDrive.exists() && dDrive.isDirectory()) {
                outputDirectory = "D:";
            } else {
                // Nếu ổ đĩa D không tồn tại, sử dụng ổ đĩa E
                outputDirectory = "E:";
            }
            File directory = new File(outputDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = outputDirectory + "/" + "student_list_" + class_name + ".xlsx";
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();

            File file = new File("student_list_" + class_name + ".xlsx");
            String absolutePath = file.getAbsolutePath();
            System.out.println("Path to file: " + absolutePath);

            System.out.println("List of students of the class " + class_name + " was successfully exported into Excel!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }
//list all student

    public String exportAllStudentList() {
        String filePath = "";
        try {
            String exportQuery = ("SELECT DISTINCT Account.id, Account.email, Account.password, Account.name, Account.dob, Account.gender, Account.phone, Account.address, Account.role_id, Account.is_valid\n"
                    + "                    FROM Account where role_id = 3");
            PreparedStatement preparedStatement = connection.prepareStatement(exportQuery);
            ResultSet rs = preparedStatement.executeQuery();

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh sách sinh viên");

            int rowNum = 0;

            // Tạo một mảng chứa tên của từng cột
            String[] columnNames = {"Student ID", "Email", "Password", "Name", "Date of Birth", "Gender", "Phone", "Address", "Role ID", "Is Valid"};

            // Tạo hàng đầu tiên và thêm tên cột
            Row headerRow = sheet.createRow(rowNum);
            for (int i = 0; i < columnNames.length; i++) {
                headerRow.createCell(i).setCellValue(columnNames[i]);
            }
            rowNum++; // Tăng giá trị của rowNum lên 1 để dữ liệu bắt đầu từ hàng 1

            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnNames.length; i++) {
                    row.createCell(i - 1).setCellValue(rs.getString(i));
                }
            }

            Row additionalRow = sheet.createRow(rowNum + 2);
            additionalRow.createCell(0).setCellValue("Note: ");
            additionalRow.createCell(1).setCellValue("This is the data of list student.");
            String outputDirectory = "";

// Kiểm tra xem ổ đĩa D có sẵn hay không
            File dDrive = new File("D:");
            if (dDrive.exists() && dDrive.isDirectory()) {
                outputDirectory = "D:";
            } else {
                // Nếu ổ đĩa D không tồn tại, sử dụng ổ đĩa E
                outputDirectory = "E:";
            }
            File directory = new File(outputDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = outputDirectory + "/" + "all_student_list.xlsx";
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();

            File file = new File("all_student_list.xlsx");
            String absolutePath = file.getAbsolutePath();
            System.out.println("Path to file: " + absolutePath);

            System.out.println("Student lists from all classes have been successfully exported into Excel!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }
//list teacher

    public String exportAllTeacherList() {
        String filePath = "";
        try {
            String exportQuery = ("SELECT DISTINCT Account.id, Account.email, Account.password, Account.name, Account.dob, Account.gender, Account.phone, Account.address, Account.role_id, Account.is_valid\n"
                    + "                    FROM Account where role_id = 2");
            PreparedStatement preparedStatement = connection.prepareStatement(exportQuery);
            ResultSet rs = preparedStatement.executeQuery();

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh sách giáo viên");

            int rowNum = 0;

            // Tạo một mảng chứa tên của từng cột
            String[] columnNames = {"Teacher ID", "Email", "Password", "Name", "Date of Birth", "Gender", "Phone", "Address", "Role ID", "Is Valid"};

            // Tạo hàng đầu tiên và thêm tên cột
            Row headerRow = sheet.createRow(rowNum);
            for (int i = 0; i < columnNames.length; i++) {
                headerRow.createCell(i).setCellValue(columnNames[i]);
            }
            rowNum++; // Tăng giá trị của rowNum lên 1 để dữ liệu bắt đầu từ hàng 1

            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnNames.length; i++) {
                    row.createCell(i - 1).setCellValue(rs.getString(i));
                }
            }

            // Thêm hàng chú thích
            Row additionalRow = sheet.createRow(rowNum + 2);
            additionalRow.createCell(0).setCellValue("Note: ");
            additionalRow.createCell(1).setCellValue("This is the data of list teacher.");

            String outputDirectory = "";

// Kiểm tra xem ổ đĩa D có sẵn hay không
            File dDrive = new File("D:");
            if (dDrive.exists() && dDrive.isDirectory()) {
                outputDirectory = "D:";
            } else {
                // Nếu ổ đĩa D không tồn tại, sử dụng ổ đĩa E
                outputDirectory = "E:";
            }
            File directory = new File(outputDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = outputDirectory + "/" + "all_teacher_list.xlsx";
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();

            File file = new File("all_teacher_list.xlsx");
            String absolutePath = file.getAbsolutePath();
            System.out.println("Path to file: " + absolutePath);

            System.out.println("Teacher lists from all classes have been successfully exported into Excel!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    private void closeIs(InputStream input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (Exception ignored) {
        }
    }
}
