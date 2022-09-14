package com.jaramgroupware.mms.utils.validation.attendance;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BulkAddAttendanceValidator.class)
@Target( { ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface BulkAddAttendanceValid {
    String message() default "중복된 ID가 있습니다!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
