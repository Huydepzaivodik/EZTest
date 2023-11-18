package excel;
// đăt tên cho dễ hiểu và sửa cho dễ
public class Constants {

    public interface CONVERTER {

        interface ERROR {

            String ERROR_CONVERTER_DATA_INVALID_FORMAT = "error.converter.dataInvalidFormat";
        }

        interface TYPE {

            String BOOLEAN_CONVERTER = "BooleanConverter";
            String DATE_CONVERTER = "DateConverter";
        }
    }

    public interface VALIDATOR {

        interface TYPE {

            String NOT_NULL_OR_BLANK_VALIDATOR = "NotNullOrBlankValidator";
            String EMAIL_VALIDATOR = "EmailValidator";
            String MAX_LENGTH_VALIDATOR = "MaxLengthValidator";
        }
    }
}
