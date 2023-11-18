package excel.validator;

import static excel.Constants.VALIDATOR.TYPE.EMAIL_VALIDATOR;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements IDataValidator {

	public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        
	@Override
	public ConstrainsError validate(Object value) {
		if (value == null) return null;
		if (!(value instanceof String) || !validateEmail((String) value)) {
			return new ConstrainsError()
					.type(EMAIL_VALIDATOR)
					.params(value);
		}
		return null;
	}

	public static boolean validateEmail(String email) {
		// Create a Pattern object
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		// Create matcher object
		Matcher matcher = pattern.matcher(email);
		// Return true if the email matches the pattern, otherwise false
		return matcher.matches();
	}
}
