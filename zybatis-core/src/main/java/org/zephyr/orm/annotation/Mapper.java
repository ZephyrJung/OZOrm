package org.zephyr.orm.annotation;

import java.lang.annotation.*;

/**
 * @author yu.zhang
 * @date 2019-09-05
 * 这个应该是用于IOC框架中，供MapperRegistry注册所有mapper接口以代理
 * @see org.zephyr.orm.binding.MapperRegistry
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
public @interface Mapper {
    // Interface Mapper
}