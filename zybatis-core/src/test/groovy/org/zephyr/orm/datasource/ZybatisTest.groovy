package org.zephyr.orm.datasource


import org.zephyr.orm.util.PropertyUtils
import spock.lang.Specification

import java.sql.DriverManager

/**
 * @author yu.zhang* @date 2019-09-06
 */
class ZybatisTest extends Specification {
    def "testGetConnection"() {
        when:
        def configuration = PropertyUtils.buildConfig()
        def mysqlDataSource = new MysqlDataSource(
                dataSource: configuration.getDataSourceConfig()
        )
        then:
        def connection = mysqlDataSource.getConnection()
        connection.close()
    }

    def "testConnection"() {
        when:
        def connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/localtest", "root", "abcd1234")
        then:
        connection != null
        connection.close()
    }
}
