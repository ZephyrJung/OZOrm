package org.zephyr.orm.datasource;

import lombok.Data;
import org.zephyr.orm.model.Zybatis;

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
 * <p>
 * 暂时按照最简单的形式实现
 * <p>
 * 最终结果应当是支持数据库连接池的，并且可配置大小，作为唯一实现，其他的数据源形式暂不考虑
 */
@Data
public class MysqlDataSource implements javax.sql.DataSource {
    private static Map<String, Driver> registeredDrivers = new ConcurrentHashMap<>();
    private Zybatis zybatis;

    public MysqlDataSource(Zybatis zybatis) {
        this.zybatis = zybatis;
    }

    public Connection getConnection() throws SQLException {
        return doGetConnection(zybatis.toProperties());
    }

    public Connection getConnection(String username, String password) throws SQLException {
        zybatis.setUsername(username);
        zybatis.setPassword(password);
        return doGetConnection(zybatis.toProperties());
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
            Driver driver = registeredDrivers.get(zybatis.getDriverClassName());
            if (driver == null) {
                driver = (Driver) Class.forName(zybatis.getDriverClassName()).getDeclaredConstructor().newInstance();
                DriverManager.registerDriver(driver);
                registeredDrivers.put(zybatis.getDriverClassName(), driver);
            }
            return DriverManager.getConnection(zybatis.getUrl(), properties);
        } catch (Exception e) {
            throw new SQLException("Error setting driver on get connection: " + e);
        }
    }
}
