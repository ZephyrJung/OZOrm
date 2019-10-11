package org.zephyr.orm.session.defaults


import org.zephyr.orm.datasource.MysqlDataSource
import org.zephyr.orm.model.Configuration
import org.zephyr.orm.test.mapper.OrderDataMapper
import org.zephyr.orm.util.PropertyUtils
import spock.lang.Specification

/**
 * @author yu.zhang* @date 2019-09-09
 */
class DefaultSqlSessionTest extends Specification {
    def "getMapper"() {
        given:
        def mysqlDataSource = new MysqlDataSource(PropertyUtils.buildConfig())
        def configuration = new Configuration()
        configuration.setDataSource(mysqlDataSource)
        configuration.getMapperRegistry().addMapper(OrderDataMapper.class)
        def sqlSession = new DefaultSqlSession(configuration, configuration.newExecutor())
        when:
        def orderDataMapper = sqlSession.getMapper(OrderDataMapper.class) as OrderDataMapper
        def result = orderDataMapper.selectByOrderId(1)
        then:
        result != null
        println(result)
    }
}
