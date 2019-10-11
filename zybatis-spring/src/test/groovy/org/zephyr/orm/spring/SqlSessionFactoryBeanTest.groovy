package org.zephyr.orm.spring

import org.zephyr.orm.binding.MapperRegistry
import org.zephyr.orm.datasource.MysqlDataSource
import org.zephyr.orm.model.Configuration

import spock.lang.Specification
import test.mapper.OrderDataMapper

/**
 * @author yu.zhang* @date 2019-09-21
 */
class SqlSessionFactoryBeanTest extends Specification {
    def "factoryBean"() {
        given:
        def factoryBean = new SqlSessionFactoryBean()
        def configuration = new Configuration()
        def mapperRegistry = new MapperRegistry()
        def dataSource = new MysqlDataSource()
        when:
        dataSource.setZybatis(new Zybatis(
                url: "jdbc:mysql://127.0.0.1:3306/localtest",
                username: "root",
                password: "abcd1234",
                driverClassName: "com.mysql.jdbc.Driver"
        ))
        mapperRegistry.addMapper(OrderDataMapper.class)
        configuration.setDataSource(dataSource)
        configuration.setMapperRegistry(mapperRegistry)
        factoryBean.setConfiguration(configuration)
        def sqlSessionFactory = factoryBean.getObject()
        def sqlSession = sqlSessionFactory.openSession().getMapper(OrderDataMapper.class) as OrderDataMapper
        def result = sqlSession.selectByOrderId(1)
        then:
        result != null
        println(result)
    }
}
