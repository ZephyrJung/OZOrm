package org.zephyr.orm.executor

import org.zephyr.orm.datasource.MysqlDataSource
import org.zephyr.orm.test.po.OrderDataPO
import org.zephyr.orm.util.PropertyUtils
import spock.lang.Specification

/**
 * @author yu.zhang* @date 2019-09-17
 */
class MysqlExecutorTest extends Specification {
    def "select"() {
        given:
        def configuration = PropertyUtils.buildConfig()
        def mysqlDataSource = new MysqlDataSource(
                dataSource: configuration.getDataSource()
        )
        when:
        def ptmt = "select * from order_data"
        def executor = new MysqlExecutor(
                mysqlDataSource: mysqlDataSource
        )
        def result = executor.select(OrderDataPO.class, ptmt)
        then:
        println(result)
        result.forEach({ r ->
            println(r.class)
            r instanceof OrderDataPO
        })
    }

    def "insert"() {
        given:
        def configuration = PropertyUtils.buildConfig()
        def mysqlDataSource = new MysqlDataSource(
                dataSource: configuration.getDataSource()
        )
        when:
        def ptmt = "insert into order_data(order_id,pin) values(?,?)"
        def executor = new MysqlExecutor(
                mysqlDataSource: mysqlDataSource
        )
        def result = executor.update(ptmt, 3, "zephyr")
        then:
        println(result)
    }

    def "update"() {
        given:
        def configuration = PropertyUtils.buildConfig()
        def mysqlDataSource = new MysqlDataSource(
                dataSource: configuration.getDataSource()
        )
        when:
        def ptmt = "update order_data set pin = ? where order_id = ?"
        def executor = new MysqlExecutor(
                mysqlDataSource: mysqlDataSource
        )
        def result = executor.update(ptmt, "zephyr", 2)
        then:
        println(result)
    }

    def "delete"() {
        given:
        def configuration = PropertyUtils.buildConfig()
        def mysqlDataSource = new MysqlDataSource(
                dataSource: configuration.getDataSource()
        )
        when:
        def ptmt = "delete from order_data where order_id = ?"
        def executor = new MysqlExecutor(
                mysqlDataSource: mysqlDataSource
        )
        def result = executor.update(ptmt, 3)
        then:
        println(result)
    }
}
