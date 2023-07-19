package gr.hua.dit.minigoodreads.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class StringRangeValidator implements ConstraintValidator<StringRange, String> {

    private String[] range;

    @Override
    public void initialize(StringRange constraintAnnotation) {
        this.range = constraintAnnotation.range();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.asList(range).contains(value);
    }
}
