package excel.converter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class DefaultConverter implements ICellConverter<Object> {

    @Override
    public Object convert(Cell cell) {
        if (cell == null) return null;
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case NUMERIC: {
                return cell.getNumericCellValue();
            }
            case STRING: {
                return cell.getStringCellValue();
            }
            case FORMULA: {
                return cell.getCellFormula();
            }
            case BOOLEAN: {
                return cell.getBooleanCellValue();
            }
            default: {
                return null;
            }
        }
    }
}
