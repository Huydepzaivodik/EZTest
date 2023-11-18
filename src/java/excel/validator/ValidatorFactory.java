package excel.validator;

public class ValidatorFactory {

    public static final EmailValidator EMAIL_VALIDATOR = new EmailValidator();
    public static final NotNullOrBlankValidator NOT_NULL_OR_BLANK_VALIDATOR = new NotNullOrBlankValidator();

    private static ValidatorFactory instance;

    public static ValidatorFactory getInstance() {
        return instance == null ? (instance = new ValidatorFactory()) : instance;
    }

    public NotNullOrBlankValidator notNullOrBlankValidator() {
        return NOT_NULL_OR_BLANK_VALIDATOR;
    }

    public EmailValidator emailValidator() {
        return EMAIL_VALIDATOR;
    }

    public MaxLengthValidator maxLengthValidator(int maxLength) {
        return new MaxLengthValidator()
                .maxlength(maxLength);
    }

}
