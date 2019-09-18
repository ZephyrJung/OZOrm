package org.zephyr.orm.executor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.zephyr.orm.datasource.MysqlDataSource;
import org.zephyr.orm.util.ReflectUtils;

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
public class MysqlExecutor {
    private MysqlDataSource mysqlDataSource;

    public <T> List<T> select(Class<T> clazz, String statement, Object... parameters) throws SQLException {
        Connection connection = mysqlDataSource.getConnection();
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
        Connection connection = mysqlDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        if (ArrayUtils.isNotEmpty(parameters)) {
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setString(i + 1, parameters[i].toString());
            }
        }
        return preparedStatement.executeUpdate();
    }
}
