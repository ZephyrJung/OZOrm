package org.zephyr.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yu.zhang
 * @date 2019-09-27
 */
@ConfigurationProperties(prefix = ZybatisProperties.ZYBATIS_PREFIX)
public class ZybatisProperties {
    public static final String ZYBATIS_PREFIX = "zybatis";
}
