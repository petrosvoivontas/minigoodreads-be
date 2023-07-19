package gr.hua.dit.minigoodreads.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringRangeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringRange {
	String[] range();

	String message();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
