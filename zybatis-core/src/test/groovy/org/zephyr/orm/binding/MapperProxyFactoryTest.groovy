package org.zephyr.orm.binding

import org.zephyr.orm.datasource.MysqlDataSource
import org.zephyr.orm.executor.MysqlExecutor
import org.zephyr.orm.impl.SqlSessionImpl
import org.zephyr.orm.test.mapper.OrderDataMapper
import org.zephyr.orm.util.PropertyUtils
import spock.lang.Specification

/**
 * @author yu.zhang* @date 2019-09-17
 */
class MapperProxyFactoryTest extends Specification {
    def "NewInstance"() {
        given:
        def sqlSession = new SqlSessionImpl(
                mysqlExecutor: new MysqlExecutor(
                        mysqlDataSource: new MysqlDataSource(
                                dataSource: PropertyUtils.buildConfig().dataSource
                        )
                )
        )
        def mapperProxyFactory = new MapperProxyFactory(OrderDataMapper.class)
        when:
        def orderDataMapper = mapperProxyFactory.newInstance(sqlSession) as OrderDataMapper
        def result = orderDataMapper.selectByOrderId(1)
        then:
        result != null
        println(result)
    }
}
