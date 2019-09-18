package org.zephyr.orm.impl

import org.zephyr.orm.datasource.MysqlDataSource
import org.zephyr.orm.executor.MysqlExecutor
import org.zephyr.orm.test.po.OrderDataPO
import org.zephyr.orm.util.PropertyUtils
import spock.lang.Specification

/**
 * @author yu.zhang* @date 2019-09-09
 */
class SqlSessionImplTest extends Specification {
    def "selectList"() {
        when:
        def sqlSession = new SqlSessionImpl(
                mysqlExecutor: new MysqlExecutor(
                        mysqlDataSource: new MysqlDataSource(
                                dataSource: PropertyUtils.buildConfig().dataSource
                        )
                )
        )
        then:
        def result = sqlSession.selectList(OrderDataPO.class, "select * from order_data")
        result != null
    }
}
