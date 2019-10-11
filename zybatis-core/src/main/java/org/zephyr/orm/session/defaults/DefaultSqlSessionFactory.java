package org.zephyr.orm.session.defaults;

import org.zephyr.orm.model.Configuration;
import org.zephyr.orm.session.SqlSession;
import org.zephyr.orm.session.SqlSessionFactory;

/**
 * @author yu.zhang
 * @date 2019-09-19
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration, configuration.newExecutor());
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }
}
