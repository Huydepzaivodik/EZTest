package excel.converter;

import static excel.Constants.CONVERTER.ERROR.ERROR_CONVERTER_DATA_INVALID_FORMAT;
import static excel.Constants.CONVERTER.TYPE.DATE_CONVERTER;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements ICellConverter<Date> {

	private String format;

	@Override
	public Date convert(Cell cell) throws ConverterException {
		if (cell == null) return null;
		CellType cellType = cell.getCellType();
		switch (cellType) {
			case NUMERIC: {
				if (DateUtil.isCellDateFormatted(cell)) {
					return cell.getDateCellValue();
				} else {
					throw new ConverterException(ERROR_CONVERTER_DATA_INVALID_FORMAT)
							.type(DATE_CONVERTER);
				}
			}
			case STRING: {
				String dateStr = cell.getStringCellValue();
				SimpleDateFormat inputFormat = new SimpleDateFormat(format);
				try {
					return inputFormat.parse(dateStr);
				} catch (ParseException e) {
					throw new ConverterException(ERROR_CONVERTER_DATA_INVALID_FORMAT)
							.type(DATE_CONVERTER);
				}
			}
			default: {
				throw new ConverterException(ERROR_CONVERTER_DATA_INVALID_FORMAT)
						.type(DATE_CONVERTER);
			}
		}
	}

	public DateConverter format(String format) {
		this.format = format;
		return this;
	}
}
