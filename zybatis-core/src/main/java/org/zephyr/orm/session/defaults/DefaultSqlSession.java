package org.zephyr.orm.session.defaults;
/**
 * 发生了奇怪的现象，如果包名为impl，MapperProxyFactoryTest单元测试无法找到这个包，也无法找到这个类
 * 换成了与mybatis一致的defaults，就可以……
 */

import org.apache.commons.collections4.CollectionUtils;
import org.zephyr.orm.executor.Executor;
import org.zephyr.orm.model.Configuration;
import org.zephyr.orm.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

/**
 * The primary Java interface for working with MyBatis.
 * Through this interface you can execute commands, get mappers and manage transactions.
 *
 * @author Clinton Begin
 * <p>
 * copied from mybatis
 * <p>
 * 实现的时候暂时先忽略这里，当做不存在，只考虑用MysqlExecutor实现功能
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T selectOne(Class<T> clazz, String statement, Object... parameters) {
        List<T> result = selectList(clazz, statement, parameters);
        return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;

    }

    public <E> List<E> selectList(Class<E> clazz, String statement, Object... parameters) {
        try {
            return executor.select(clazz, statement, parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insert(String statement, Object... parameters) {
        try {
            return executor.update(statement, parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int update(String statement, Object... parameters) {
        try {
            return executor.update(statement, parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int delete(String statement, Object... parameters) {
        try {
            return executor.update(statement, parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }
}
