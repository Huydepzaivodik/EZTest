
package excel.validator;

import static excel.Constants.VALIDATOR.TYPE.MAX_LENGTH_VALIDATOR;

public class MaxLengthValidator implements IDataValidator {

    private int maxLength;

    public MaxLengthValidator maxlength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    @Override
    public ConstrainsError validate(Object value) {
        System.out.println("MaxLengthValidator: validate");
        if (value == null) {
            return null;
        }
        if (!(value instanceof String)) {
            return new ConstrainsError()
                    .type(MAX_LENGTH_VALIDATOR)
                    .params(value, maxLength);//sai định dạng
        }
        final int length = ((String) value).length();
        System.out.println("MaxLengthValidator: validate | length=" + length);
        if (length > maxLength) {
            return new ConstrainsError()
                    .type(MAX_LENGTH_VALIDATOR)
                    .params(value, maxLength);// giá trị phải nhập yêu cầu ...
        }
        return null;
    }

}
