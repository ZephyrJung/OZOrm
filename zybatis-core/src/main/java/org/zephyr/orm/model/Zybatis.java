package org.zephyr.orm.model;

import lombok.Data;

import java.util.Properties;

/**
 * @author yu.zhang
 * @date 2019-09-06
 * properties的设置与com.zybatis.jdbc.NonRegisteringDriver中的配置有关
 */
@Data
public class Zybatis {
    private String url;
    private String username;
    private String password;
    private String driverClassName;

    public Properties toProperties() {
        Properties properties = new Properties();
        properties.setProperty("url", url);
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        properties.setProperty("driverClassName", driverClassName);
        return properties;
    }
}
