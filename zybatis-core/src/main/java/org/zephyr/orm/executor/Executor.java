package org.zephyr.orm.executor;

import java.sql.SQLException;
import java.util.List;

/**
 * @author yu.zhang
 * @date 2019-09-23
 */
public interface Executor {
    <T> List<T> select(Class<T> clazz, String statement, Object... parameters) throws SQLException;

    int update(String statement, Object... parameters) throws SQLException;
}
