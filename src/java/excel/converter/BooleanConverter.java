package excel.converter;

import static excel.Constants.CONVERTER.ERROR.ERROR_CONVERTER_DATA_INVALID_FORMAT;
import static excel.Constants.CONVERTER.TYPE.BOOLEAN_CONVERTER;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class BooleanConverter implements ICellConverter<Boolean> {

    @Override
    public Boolean convert(Cell cell) throws ConverterException {
        if (cell == null) {
            return null;
        }
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case NUMERIC:
            case STRING:
            case FORMULA: {
                throw new ConverterException(ERROR_CONVERTER_DATA_INVALID_FORMAT)
                        .type(BOOLEAN_CONVERTER);
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
