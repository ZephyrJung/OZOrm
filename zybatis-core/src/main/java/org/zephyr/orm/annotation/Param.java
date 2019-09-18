package org.zephyr.orm.annotation;

import java.lang.annotation.*;

/**
 * @author yu.zhang
 * @date 2019-09-05
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {
    String value();
}
