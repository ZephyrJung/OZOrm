package org.zephyr.orm.util

import groovy.sql.Sql
import org.zephyr.orm.test.po.OrderDataPO
import spock.lang.Specification

/**
 * @author yu.zhang* @date 2019-09-12
 */
class ReflectUtilsTest extends Specification {
    def "BuildInstance"() {
        given:
        def sql = Sql.newInstance("jdbc:mysql://127.0.0.1:3306/localtest", "root", "abcd1234", "com.mysql.jdbc.Driver")
        when:
        def result = null
        sql.eachRow("select * from order_data") { row ->
            result = ReflectUtils.buildInstance(OrderDataPO.class, row)
        }
        then:
        sql.close()
        result != null
        println(result)
    }
}
