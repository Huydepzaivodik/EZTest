package excel.converter;

import org.apache.poi.ss.usermodel.Cell;

public interface ICellConverter<T> {// T là kiểu dữ liệu trả về sau convert
//ICellConverter là kiểu generic class
    T convert(Cell cell) throws ConverterException;
}
