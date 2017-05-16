package com.iusofts.blades.sys.common.util.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
@Documented
/**
 * 电话校验
 * 
 * @author：Ivan
 * @date： 2016年3月4日 下午5:07:42
 */
public @interface Phone {
	String message() default "{com.mycompany.constraints.checkcase}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
	PhoneMode value();
}