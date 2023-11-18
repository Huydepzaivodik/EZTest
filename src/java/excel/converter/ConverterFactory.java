package excel.converter;


public class ConverterFactory {//lớp khởi tạo các converter

    private static ConverterFactory instance;

    public static ConverterFactory getInstance() {
        return instance == null ? (instance = new ConverterFactory()) : instance;
    }

    public DefaultConverter defaultConverter() {
        return new DefaultConverter();
    }

    public BooleanConverter booleanConverter() {
        return new BooleanConverter();
    }

    public DateConverter dateConverter(String format) {
        return new DateConverter()
                .format(format);
    }

    public StringConverter stringConverter() {
        return new StringConverter();
    }
}
