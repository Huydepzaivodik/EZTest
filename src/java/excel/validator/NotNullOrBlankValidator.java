package excel.validator;

import static excel.Constants.VALIDATOR.TYPE.NOT_NULL_OR_BLANK_VALIDATOR;

public class NotNullOrBlankValidator implements IDataValidator {

    @Override
    public ConstrainsError validate(Object value) {
        if (value == null) {
            return new ConstrainsError()
                    .type(NOT_NULL_OR_BLANK_VALIDATOR);
        }
        if ((value instanceof String) && ((String) value).trim().isEmpty()) {
            return new ConstrainsError()
                    .type(NOT_NULL_OR_BLANK_VALIDATOR);
        }
        return null;
    }
}
