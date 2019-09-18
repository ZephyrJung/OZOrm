package org.zephyr.orm.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yu.zhang
 * @date 2019-09-12
 * 此处用到了泛型方法
 * 泛型方法其实就是返回了任意类型（可以是任意具体的类型，而非Object）
 */
public class ReflectUtils {
    /**
     * 将ResultSet的结果转换为指定clazz的实例
     *
     * @param clazz
     * @param resultSet
     * @param <T>
     * @return
     */
    public static <T> T buildInstance(Class<T> clazz, ResultSet resultSet) {
        try {
            T t = clazz.newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(t, resultSet.getObject(StringUtils.convertToUnderScore(field.getName())));
            }
            return t;
        } catch (SQLException | InstantiationException | IllegalAccessException ignored) {
            return null;
        }
    }
}
