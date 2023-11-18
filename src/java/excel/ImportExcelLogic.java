package excel;

import excel.converter.ConvertError;
import excel.converter.ConverterException;
import excel.converter.ConverterFactory;
import excel.converter.ICellConverter;
import excel.validator.ConstrainsError;
import excel.validator.IDataValidator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * cach thuc hien doc 1 file excel:
 *
 * - check 1 so dieu kien lien quan toi file
 * - duyet tung row cua file, output:
 *   > voi moi row, duyet tung column
 *   	> voi moi cell, thuc hien:
 *   		. doc du lieu cell (dua vao cau hinh converter)
 *   		. validate du lieu cua file (dua vao cau hinh validator). Chu y 1 cell co the co nhieu validator
 *   		.output sau khi doc file: ReadCellResult
 *   - output sau khi doc tung row: ReadCellResult
 *
 *  */
public class ImportExcelLogic {//phục vụ nghiệp vụ đọc excel (đầu vào là file) (đầu ra là danh sách các kết quả của từng dòng)
    // sử dụng lại (đọc các cấu hình truyền vào)
    public static final String ERROR_IMPORT_EXCEL_FILE_NOT_FOUND = "error.importExcel.fileNotFound";
    public static final String ERROR_IMPORT_EXCEL_READ_FILE_FAILED = "error.importExcel.readFileFailed";
    public static final String ERROR_IMPORT_EXCEL_FILE_CONTENT_NOT_SUPPORTED = "error.importExcel.fileContentNotSupported";
    //
    private String filePath;
    private int columnCount; //so luong cot can doc;
    private IDataValidator[][] validators; //quy định 1 tập các rule liên quan đến ràng buộc dữ liệu trên từng cột
    private ICellConverter<?>[] converters; //phục vụ chuyển đổi dữ liệu của từng cell thành dữ liệu primative(String, int, boolean,..)
    private List<ReadRowResult> readRowResults; //danh sách kết quả đọc của dòng; mỗi dòng bao gồm cuả từng cột 
    private List<String> fieldNames;

    public ImportExcelLogic fieldNames(List<String> fieldNames) {
        this.fieldNames = fieldNames;
        return this;
    }

    public ImportExcelLogic doImport() throws ImportExcelException {

        File file = new File(filePath);
        if (!file.exists()) {
            throw new ImportExcelException(ERROR_IMPORT_EXCEL_FILE_NOT_FOUND);
        }

        FileInputStream excelFileIs = null;

        try {

            try {
                excelFileIs = new FileInputStream(filePath);
            } catch (FileNotFoundException e) {
                throw new ImportExcelException(ERROR_IMPORT_EXCEL_FILE_NOT_FOUND, e);
            }

            Workbook workbook = null;

            try {
                try {
                    workbook = new XSSFWorkbook(excelFileIs);
                } catch (IOException e) {
                    throw new ImportExcelException(ERROR_IMPORT_EXCEL_READ_FILE_FAILED, e);
                } catch (Exception e) {
                    throw new ImportExcelException(ERROR_IMPORT_EXCEL_FILE_CONTENT_NOT_SUPPORTED,
                            e);
                }

                Sheet sheet = workbook.getSheetAt(0);

                int rowIndex = 0;
                readRowResults = new ArrayList<>();

                //doc tung row;
                readRows(sheet, rowIndex);
            } finally {
                closeWorkbook(workbook);
            }
        } finally {
            closeIs(excelFileIs);
        }

        return this;
    }

    private static void closeWorkbook(Workbook workbook) {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (Exception ignored) {
            }
        }
    }

    private void readRows(Sheet sheet, int rowIndex) {
        for (Row row : sheet) {
            //doc tung column;
            ReadRowResult readRowResult = readColumns(rowIndex, row);
            readRowResults.add(readRowResult);
            rowIndex++;
        }
    }

    private ReadRowResult readColumns(int rowIndex, Row row) {
        ReadCellResult[] readCellResults = new ReadCellResult[columnCount];
        for (int colIndex = 0; colIndex < columnCount; colIndex++) {
            Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Object cellValue = null;
            ConstrainsError constrainsError = null;
            ConvertError convertError = null;
            try {
                cellValue = getCellValue(cell,
                        colIndex);// đọc dữ liệu từ cell và convert thành kiểu dữ liệu mong muốn trong java
                constrainsError = validateCell(colIndex,
                        cellValue);//kiểm tra so với ràng buộc của mình đúng ko
            } catch (ConverterException e) {
                convertError = new ConvertError()
                        .params(fieldNames.get(colIndex));
            }
            ReadCellResult readCellResult = new ReadCellResult()
                    .rowIndex(rowIndex)
                    .cellValue(cellValue)
                    .constrainsError(constrainsError)
                    .convertError(convertError);
            readCellResults[colIndex] = readCellResult;
        }
        return new ReadRowResult()
                .readCellResults(readCellResults);
    }

    private ConstrainsError validateCell(int colIndex, Object cellValue) {
        IDataValidator[] validatorsByCol = getValidatorsForColumn(
                colIndex);//lấy danh sách các rule trên 1 côt dữ liệu
        if (validatorsByCol == null) {//nếu null ko cần validate
            return null;
        }
        for (IDataValidator validator : validatorsByCol) {// chạy các rule theo thứ tự và validate
            final ConstrainsError constrainsError = validator.validate(cellValue);
            if (constrainsError != null) {//nếu 1 rule bị vi phạm thì sẽ trả về lỗi luôn
                return toCustomError(constrainsError, fieldNames.get(colIndex));
            }
        }
        return null;
    }

    //excel tự định dạng dữ liệu trong cột. object của mình muốn nhận đúng định dạng thì phải convert
    private Object getCellValue(Cell cell, Integer colIndex) throws ConverterException {
        ConverterFactory converterFactory = ConverterFactory.getInstance();
        ICellConverter<?> converter = converters == null ? null : converters[colIndex];
        if (converter == null) {
            converter = converterFactory.defaultConverter();
        }
        return converter.convert(cell);
    }

    private IDataValidator[] getValidatorsForColumn(int cellIndex) {
        return validators == null ? null : validators[cellIndex];
    }

    public ImportExcelLogic filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public ImportExcelLogic columnCount(int columnCount) {
        this.columnCount = columnCount;
        return this;
    }

    public ImportExcelLogic validators(IDataValidator[]... validators) {
        this.validators = validators;
        return this;
    }

    public List<ReadRowResult> getReadRowResults() {
        return readRowResults;
    }

    public ImportExcelLogic converters(ICellConverter<?>... converters) {
        this.converters = converters;
        return this;
    }

    private void closeIs(FileInputStream excelFileIs) {
        try {
            if (excelFileIs != null) {
                excelFileIs.close();
            }
        } catch (Exception ignored) {
        }
    }

    private ConstrainsError toCustomError(ConstrainsError constrainsError, String fieldName) {
        if (constrainsError == null) {
            return null;
        }
        Object[] params = constrainsError.getParams();
        final int length = params == null ? 0 : params.length;
        Object[] newArray = params == null ? new Object[1]
                : Arrays.copyOf(constrainsError.getParams(), length + 1);
        newArray[length] = fieldName;
        return new ConstrainsError()
                .type(constrainsError.getType())
                .params(newArray);
    }
}
