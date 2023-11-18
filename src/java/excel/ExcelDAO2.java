package excel;

import com.google.gson.Gson;
import dal.DBContext;
import excel.converter.ConverterFactory;
import excel.converter.ICellConverter;
import excel.validator.IDataValidator;
import excel.validator.ValidatorFactory;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Account;

public class ExcelDAO2 extends DBContext {

    public static final String MM_DD_YYYY = "MM/dd/yyyy";

    public static IDataValidator[][] buildValidators() {

        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();

        IDataValidator[][] iDataValidators = new IDataValidator[8][];

        //column1:email
        iDataValidators[0] = new IDataValidator[]{
            validatorFactory.notNullOrBlankValidator(),
            validatorFactory.emailValidator()
        };

        //column2:password
        iDataValidators[1] = new IDataValidator[]{
            validatorFactory.notNullOrBlankValidator(),
            validatorFactory.maxLengthValidator(32),
        };

        //column3:name
        iDataValidators[2] = new IDataValidator[]{
            validatorFactory.notNullOrBlankValidator(),};

        //column4:date of birth
        iDataValidators[3] = new IDataValidator[]{
            validatorFactory.notNullOrBlankValidator(),};

        //column5:gender
        iDataValidators[4] = new IDataValidator[]{
            validatorFactory.notNullOrBlankValidator(),};

        //column6:phone
        iDataValidators[5] = new IDataValidator[]{
            validatorFactory.notNullOrBlankValidator(),
            validatorFactory.maxLengthValidator(10),};

        //column7:address
        iDataValidators[6] = new IDataValidator[]{};

        //column8:image
        iDataValidators[7] = new IDataValidator[]{};

        return iDataValidators;
    }

    public static ICellConverter<?>[] buildConverters() {
        ConverterFactory instance = ConverterFactory.getInstance();
        return new ICellConverter<?>[]{
            //column1:email
            instance.stringConverter(),
            //column2:password
            instance.stringConverter(),
            //column3:name
            instance.stringConverter(),
            //column4:date of birth
            instance.dateConverter(MM_DD_YYYY),
            //column5:gender
            instance.booleanConverter(),
            //column6:phone
            instance.stringConverter(),
            //column7:address
            instance.stringConverter(),
            //column8:image
            instance.stringConverter(),};
    }

    public List<ImportAccountResult> importAccountsFromExcel(String excelFilePath) {
        System.out.println("");
        ImportExcelLogic importExcelLogic = new ImportExcelLogic()
                .filePath(excelFilePath)
                .columnCount(8)
                .fieldNames(buildFieldNames())
                .converters(buildConverters())
                .validators(buildValidators());
        ExcelDAO2 sample = new ExcelDAO2();
        List<ImportAccountResult> importAccountResults = new ArrayList<>();
        try {
            importExcelLogic.doImport();
            List<ReadRowResult> readRowResults = importExcelLogic.getReadRowResults();

            for (ReadRowResult readRowResult : readRowResults) {
                if (!isRowError(readRowResult)) {
                    Account account;
                    try {
                        if (!isDuplicate(readRowResult)) {
                            account = sample.importToDb(readRowResult);
                            importAccountResults.add(
                                    new ImportAccountResult()
                                            .id(account.getId())
                            );
                        } else {
                            importAccountResults.add(
                                    new ImportAccountResult()
                                            .errorMessage("error.import.duplicateRow")
                            );
                        }
                    } catch (SQLException ex) {
                        importAccountResults.add(
                                new ImportAccountResult()
                                        .errorMessage("error.import.dbError")
                        );
                    }

                } else {
                    importAccountResults.add(
                            new ImportAccountResult()
                                    .errorMessage(sample.buildErrorMessage(readRowResult))
                    );
                }
            }

            System.out.println(
                    "result: importAccountResults=" + new Gson().toJson(importAccountResults)
            );

        } catch (ImportExcelException e) {
            handleImportExcelException(e);
        }

        return importAccountResults;
    }

    public List<ImportAccountResult> importTeachersFromExcel(String excelFilePath) {
        System.out.println("");
        ImportExcelLogic importExcelLogic = new ImportExcelLogic()
                .filePath(excelFilePath)
                .columnCount(8)
                .fieldNames(buildFieldNames())
                .converters(buildConverters())
                .validators(buildValidators());
        ExcelDAO2 sample = new ExcelDAO2();
        List<ImportAccountResult> importAccountResults = new ArrayList<>();
        try {
            importExcelLogic.doImport();
            List<ReadRowResult> readRowResults = importExcelLogic.getReadRowResults();

            for (ReadRowResult readRowResult : readRowResults) {
                if (!isRowError(readRowResult)) {
                    Account account;
                    try {
                        if (!isDuplicate(readRowResult)) {
                            account = sample.importTeacherToDb(readRowResult);
                            importAccountResults.add(
                                    new ImportAccountResult()
                                            .id(account.getId())
                            );
                        } else {
                            importAccountResults.add(
                                    new ImportAccountResult()
                                            .errorMessage("error.import.duplicateRow")
                            );
                        }
                    } catch (SQLException ex) {
                        importAccountResults.add(
                                new ImportAccountResult()
                                        .errorMessage("error.import.dbError")
                        );
                    }

                } else {
                    importAccountResults.add(
                            new ImportAccountResult()
                                    .errorMessage(sample.buildErrorMessage(readRowResult))
                    );
                }
            }

            System.out.println(
                    "result: importAccountResults=" + new Gson().toJson(importAccountResults)
            );

        } catch (ImportExcelException e) {
            handleImportExcelException(e);
        }

        return importAccountResults;
    }

    public String buildErrorMessage(ReadRowResult readRowResult) {

        StringBuilder errorMessage = new StringBuilder();

        for (ReadCellResult cellResult : readRowResult.getReadCellResults()) {
            if (isCellError(cellResult)) {
                errorMessage.append("\n").append(buildErrorMessage(cellResult));
            }
        }

        return errorMessage.toString();
    }

    public String buildErrorMessage(ReadCellResult cellResult) {

        if (cellResult.getConvertError() != null) {
            return cellResult.getConvertError().toString();
        }

        if (cellResult.getConstrainsError() != null) {
            return cellResult.getConstrainsError().toString();
        }

        return null;
    }

    public Account importToDb(ReadRowResult readRowResult) throws SQLException {
        //ghep vao doan code insert to db;
        ReadCellResult[] readCellResults = readRowResult.getReadCellResults();
        Account account = new Account();
        account.setEmail((String) readCellResults[0].getCellValue());
        account.setPassword((String) readCellResults[1].getCellValue());
        account.setName((String) readCellResults[2].getCellValue());
        java.util.Date date = (java.util.Date) readCellResults[3].getCellValue();
        account.setDob(date == null ? null : new Date(date.getTime()));
        account.setGender((Boolean) readCellResults[4].getCellValue());
        account.setPhone((String) readCellResults[5].getCellValue());
        account.setAddress((String) readCellResults[6].getCellValue());
        account.setImg(((String) readCellResults[7].getCellValue()));

        return importToDb(account);
    }

    /**
     * insert vao bang account & gan id vao cho account;
     */
    public Account importToDb(Account account) throws SQLException {

        int id = 0; // ket qua tra ra cua ham;
        System.out.println("importToDb: account=" + account);
        String sql = "INSERT INTO Account (email, password, name, dob, gender, phone, address, role_id, img, is_valid)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement studentStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

//        String img = account.getImg();
        int role_id = 3;

        studentStatement.setString(1, account.getEmail());
        studentStatement.setString(2, account.getPassword());
        studentStatement.setString(3, account.getName());
        studentStatement.setDate(4, account.getDob());
        studentStatement.setBoolean(5, account.isGender());
        studentStatement.setString(6, account.getPhone());
        studentStatement.setString(7, account.getAddress());
        studentStatement.setInt(8, role_id);
        studentStatement.setString(9, account.getImg());
        studentStatement.setBoolean(10, true);

        int affectedRows = studentStatement.executeUpdate();

        if (affectedRows > 0) {
            ResultSet generatedKeys = studentStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
                System.out.println("Inserted student with ID: " + id);
            }
        }

        account.setId(id);
        return account;
    }

    /**
     * row khong loi khi khong co cell nao bi loi;
     */
    public static boolean isRowError(ReadRowResult readRowResult) {
        for (ReadCellResult cellResult : readRowResult.getReadCellResults()) {
            if (isCellError(cellResult)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCellError(ReadCellResult cellResult) {
        return cellResult.getConvertError() != null || cellResult.getConstrainsError() != null;
    }

    public static void handleImportExcelException(ImportExcelException e) {
        e.printStackTrace(System.err);
    }

    public void addStudentToStudentClassFromExcel(List<ImportAccountResult> importAccountResults, int classId) {
        try {
            String sql = "INSERT INTO Student_Class (class_id, student_id) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            try {
                for (ImportAccountResult importAccountResult : importAccountResults) {
                    if (importAccountResult.getId() != null) {
                        preparedStatement.setInt(1, classId);
                        preparedStatement.setInt(2, importAccountResult.getId());
                        preparedStatement.addBatch();
                        System.out.println(importAccountResult.getId());
                    } else {
                        System.out.println("Skipping entry with null student ID");
                    }
                }
                preparedStatement.executeBatch();
            } finally {
                close(preparedStatement);
            }
            System.out.println("Imported data into Student_Class table successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Account importTeacherToDb(Account account) throws SQLException {

        int id = 0; // ket qua tra ra cua ham;
        System.out.println("importTeacherToDb: account=" + account);
        String sql = "INSERT INTO Account (email, password, name, dob, gender, phone, address, role_id, img, is_valid)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement studentStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

//        String img = account.getImg();
        int role_id = 2;

        studentStatement.setString(1, account.getEmail());
        studentStatement.setString(2, account.getPassword());
        studentStatement.setString(3, account.getName());
        studentStatement.setDate(4, account.getDob());
        studentStatement.setBoolean(5, account.isGender());
        studentStatement.setString(6, account.getPhone());
        studentStatement.setString(7, account.getAddress());
        studentStatement.setInt(8, role_id);
        studentStatement.setString(9, account.getImg());
        studentStatement.setBoolean(10, true);

        int affectedRows = studentStatement.executeUpdate();

        if (affectedRows > 0) {
            ResultSet generatedKeys = studentStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
                System.out.println("Inserted student with ID: " + id);
            }
        }

        account.setId(id);
        return account;
    }

    public Account importTeacherToDb(ReadRowResult readRowResult) throws SQLException {
        //ghep vao doan code insert to db;
        ReadCellResult[] readCellResults = readRowResult.getReadCellResults();
        Account account = new Account();
        account.setEmail((String) readCellResults[0].getCellValue());
        account.setPassword((String) readCellResults[1].getCellValue());
        account.setName((String) readCellResults[2].getCellValue());
        java.util.Date date = (java.util.Date) readCellResults[3].getCellValue();
        account.setDob(date == null ? null : new Date(date.getTime()));
        account.setGender((Boolean) readCellResults[4].getCellValue());
        account.setPhone((String) readCellResults[5].getCellValue());
        account.setAddress((String) readCellResults[6].getCellValue());
        account.setImg(((String) readCellResults[7].getCellValue()));

        return importTeacherToDb(account);
    }

    private void close(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (Exception ignored) {
        }
    }

    private boolean isDuplicate(ReadRowResult readRowResult) {
        final ReadCellResult[] readCellResults = readRowResult.getReadCellResults();
        String email = (String) readCellResults[0].getCellValue();
        String phone = (String) readCellResults[5].getCellValue();
        return checkEmailExists(email) || checkPhoneExists(phone);
    }

    /**
     *
     * @param email
     * @return true neu exists;
     */
    public boolean checkEmailExists(String email) {
        try {
            String query = "SELECT COUNT(*) FROM Account WHERE lower(email) = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email.toLowerCase());
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
    /**
     *
     * @param phone
     * @return true neu exits;
     */
    public boolean checkPhoneExists(String phone) {
        try {
            String query = "SELECT COUNT(*) FROM Account WHERE lower(phone) = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, phone.toLowerCase());
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

    private List<String> buildFieldNames() {
        List<String> fieldsNames = new ArrayList<>();
        fieldsNames.add("email");
        fieldsNames.add("password");
        fieldsNames.add("name");
        fieldsNames.add("date of birth");
        fieldsNames.add("gender");
        fieldsNames.add("phone");
        fieldsNames.add("address");
        fieldsNames.add("image");
        return fieldsNames;
    }
}
