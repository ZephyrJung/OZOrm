package org.zephyr.orm.model;

import lombok.Data;
import org.zephyr.orm.binding.MapperRegistry;
import org.zephyr.orm.executor.Executor;
import org.zephyr.orm.executor.impl.MysqlExecutor;
import org.zephyr.orm.session.SqlSession;

import javax.sql.DataSource;

/**
 * @author yu.zhang
 * @date 2019-09-06
 * 与zybatis.yml的属性值相对应
 */
@Data
public class Configuration {
    final private MapperRegistry mapperRegistry = new MapperRegistry();
    private DataSource dataSource;

    public Configuration() {
    }

    public Configuration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Executor newExecutor() {
        return new MysqlExecutor(dataSource);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public <T> boolean hasMapper(Class<T> type) {
        return mapperRegistry.hasMapper(type);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }
}
