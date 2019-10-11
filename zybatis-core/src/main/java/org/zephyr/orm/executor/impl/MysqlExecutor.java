package org.zephyr.orm.executor.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.zephyr.orm.executor.Executor;
import org.zephyr.orm.util.ReflectUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yu.zhang
 * @date 2019-09-09
 */
@Data
@Slf4j
public class MysqlExecutor implements Executor {
    // 依赖于接口而非具体的实现类
    private DataSource dataSource;

    public MysqlExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> List<T> select(Class<T> clazz, String statement, Object... parameters) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(statement);

        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setString(i + 1, parameters[i].toString());
            }
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            List<T> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(ReflectUtils.buildInstance(clazz, resultSet));
            }
            return result;
        } catch (Exception e) {
            return null;
        } finally {
            preparedStatement.close();
        }
    }

    public int update(String statement, Object... parameters) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        if (ArrayUtils.isNotEmpty(parameters)) {
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setString(i + 1, parameters[i].toString());
            }
        }
        return preparedStatement.executeUpdate();
    }
}
