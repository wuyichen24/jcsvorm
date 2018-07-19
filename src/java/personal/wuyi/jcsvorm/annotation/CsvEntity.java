package personal.wuyi.jcsvorm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CSV entity annotation for marking the Java class used for ORM.
 * 
 * <p>No parameter for this annotation.
 * 
 * @author  Wuyi Chen
 * @date    03/14/2017
 * @version 1.1
 * @since   1.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CsvEntity {

}
