package sk.tomas.servant.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Config
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PackageScan {
    String value() default "";
}

