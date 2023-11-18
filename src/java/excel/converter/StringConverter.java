package excel.converter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class StringConverter implements ICellConverter<String> {

    @Override
    public String convert(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case NUMERIC: {
                return String.valueOf(cell.getNumericCellValue());
            }
            case STRING: {
                return cell.getStringCellValue();
            }
            case FORMULA: {
                return cell.getCellFormula();
            }
            case BOOLEAN: {
                return String.valueOf(cell.getBooleanCellValue());
            }
            default: {
                return null;
            }
        }
    }
}
