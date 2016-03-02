package org.apache.atlas.report;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ METHOD, TYPE, CONSTRUCTOR })
public @interface TestInfo {

    public String qcName() default "";

    public String author() default "";

    public String coder() default "";
}
