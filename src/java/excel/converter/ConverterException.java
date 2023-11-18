package excel.converter;

public class ConverterException extends Exception {

    private String type;

    public ConverterException(String message) {
        super(message);
    }

    public ConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ConverterException type(String type) {
        setType(type);
        return this;
    }
}
