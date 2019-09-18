package org.zephyr.orm.util

import spock.lang.Specification

/**
 * @author yu.zhang* @date 2019-09-06
 */
class PropertyUtilsTest extends Specification {
    def "BuildConfig"() {
        when:
        def configuration = PropertyUtils.buildConfig(null)
        then:
        configuration.dataSource.username == "root"
        configuration.dataSource.password == "abcd1234"
        configuration.dataSource.url == "jdbc:mysql://127.0.0.1:3306/localtest"
        configuration.dataSource.driverClassName == "com.mysql.jdbc.Driver"
    }
}
