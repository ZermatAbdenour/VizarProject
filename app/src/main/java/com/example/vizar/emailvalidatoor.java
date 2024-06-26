package com.example.vizar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class emailvalidatoor {
    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_REGEX =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public emailvalidatoor() {
        pattern = Pattern.compile(EMAIL_REGEX);
    }

    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }
}


