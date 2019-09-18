package org.zephyr.orm.datasource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zephyr.orm.model.DataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author yu.zhang
 * @date 2019-09-06
 *
 * 暂时按照最简单的形式实现
 *
 * 最终结果应当是支持数据库连接池的，并且可配置大小，作为唯一实现，其他的数据源形式暂不考虑
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MysqlDataSource implements javax.sql.DataSource {
    private static Map<String, Driver> registeredDrivers = new ConcurrentHashMap<>();
    private DataSource dataSource;

    public Connection getConnection() throws SQLException {
        return doGetConnection(dataSource.toProperties());
    }

    public Connection getConnection(String username, String password) throws SQLException {
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return doGetConnection(dataSource.toProperties());
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException(getClass().getName() + " is not a wrapper.");
    }

    public boolean isWrapperFor(Class<?> iface) {
        return false;
    }

    public PrintWriter getLogWriter() {
        return DriverManager.getLogWriter();
    }

    public void setLogWriter(PrintWriter out) {
        DriverManager.setLogWriter(out);
    }

    public void setLoginTimeout(int seconds) {
        DriverManager.setLoginTimeout(seconds);
    }

    public int getLoginTimeout() {
        return DriverManager.getLoginTimeout();
    }

    public Logger getParentLogger() {
        return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }


    private Connection doGetConnection(Properties properties) throws SQLException {
        try {
            Driver driver = registeredDrivers.get(dataSource.getDriverClassName());
            if (driver == null) {
                driver = (Driver) Class.forName(dataSource.getDriverClassName()).getDeclaredConstructor().newInstance();
                DriverManager.registerDriver(driver);
                registeredDrivers.put(dataSource.getDriverClassName(), driver);
            }
            return DriverManager.getConnection(dataSource.getUrl(), properties);
        } catch (Exception e) {
            throw new SQLException("Error setting driver on get connection: " + e);
        }
    }
}
