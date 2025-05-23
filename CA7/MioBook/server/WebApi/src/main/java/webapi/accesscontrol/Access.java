package webapi.accesscontrol;

import domain.entities.user.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Access {
    Role[] roles() default {};
    boolean isWhiteList() default false;
}
