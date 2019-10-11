package org.zephyr.orm.spring.mapper;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;
import org.zephyr.orm.model.Configuration;
import org.zephyr.orm.spring.support.SqlSessionDaoSupport;

import static org.springframework.util.Assert.notNull;

/**
 * @author yu.zhang
 * @date 2019-09-23
 */
@Data
public class MapperFactoryBean<T> extends SqlSessionDaoSupport implements FactoryBean<T> {
    private Class<T> mapperInterface;

    public MapperFactoryBean() {
    }

    public MapperFactoryBean(Class<T> tClass) {
        this.mapperInterface = tClass;
    }

    @Override
    public T getObject() throws Exception {
        return getSqlSession().getMapper(this.mapperInterface);
    }

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {
        super.checkDaoConfig();
        notNull(this.mapperInterface, "Property 'mapperInterface' is required");
        Configuration configuration = getSqlSession().getConfiguration();
        if (!configuration.hasMapper(this.mapperInterface)) {
            configuration.addMapper(this.mapperInterface);
        }
    }

    @Override
    public Class<?> getObjectType() {
        return this.mapperInterface;
    }
}
