package sk.tomas.servant.annotation;

/**
 * Created by Tomas Pachnik on 27-Apr-17.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Inject {

    String value() default "";

}
