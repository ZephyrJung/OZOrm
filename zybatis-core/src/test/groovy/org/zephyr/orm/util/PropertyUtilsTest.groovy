package org.zephyr.orm.util

import org.zephyr.orm.model.Zybatis
import spock.lang.Specification

/**
 * @author yu.zhang* @date 2019-09-06
 */
class PropertyUtilsTest extends Specification {
    def "BuildConfig"() {
        when:
        def zybatis = PropertyUtils.buildConfig(null) as Zybatis
        then:
        println(zybatis)
        zybatis.username == "root"
        zybatis.password == "abcd1234"
        zybatis.url == "jdbc:mysql://127.0.0.1:3306/localtest"
        zybatis.driverClassName == "com.mysql.jdbc.Driver"
    }
}
