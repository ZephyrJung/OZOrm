package org.zephyr.orm.model;

import lombok.Data;

/**
 * @author yu.zhang
 * @date 2019-09-06
 * 与zybatis.yml的属性值相对应
 */
@Data
public class Configuration {
    private DataSource dataSource;
}
