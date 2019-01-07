package com.quartz.monitor.conf.anno;

import java.lang.annotation.*;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/7 20:36
 * @Description:
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Manualwired {
    boolean required() default true;
}
