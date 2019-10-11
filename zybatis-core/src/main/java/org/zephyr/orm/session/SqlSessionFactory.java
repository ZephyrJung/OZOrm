package org.zephyr.orm.session;

import org.zephyr.orm.model.Configuration;

/**
 * @author yu.zhang
 * @date 2019-09-19
 */
public interface SqlSessionFactory {
    SqlSession openSession();

    /**
     * 工厂的配置
     * 在SqlSession中也有getConfiguration，如果是非工厂生产的实例，配置自行指定，如果是工厂生产的实例，则需要工厂配置
     *
     * @return
     */
    Configuration getConfiguration();
}
