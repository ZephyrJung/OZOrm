package org.zephyr.orm.spring;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.zephyr.orm.model.Configuration;
import org.zephyr.orm.session.SqlSessionFactory;
import org.zephyr.orm.session.defaults.DefaultSqlSessionFactory;

import javax.sql.DataSource;

/**
 * @author yu.zhang
 * http://mybatis.org/spring/zh/getting-started.html
 * @date 2019-09-19
 */
@Data
public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>, InitializingBean, ApplicationListener<ApplicationEvent> {
    private Configuration configuration;
    private SqlSessionFactory sqlSessionFactory;
    private DataSource dataSource;

    public SqlSessionFactory getObject() throws Exception {
        if (this.sqlSessionFactory == null) {
            afterPropertiesSet();
        }
        return this.sqlSessionFactory;
    }

    public Class<?> getObjectType() {
        return null;
    }

    public void afterPropertiesSet() throws Exception {
        this.sqlSessionFactory = buildSqlSessionFactory();
    }

    public void onApplicationEvent(ApplicationEvent applicationEvent) {
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    private SqlSessionFactory buildSqlSessionFactory() {
        if (this.configuration != null) {
            return new DefaultSqlSessionFactory(this.configuration);
        } else {
            Configuration configuration = new Configuration();
            configuration.setDataSource(dataSource);
            return new DefaultSqlSessionFactory(configuration);
        }
    }
}
