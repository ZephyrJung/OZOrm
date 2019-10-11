package org.zephyr.orm.binding;

import org.zephyr.orm.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yu.zhang
 * @date 2019-09-17
 * @see org.zephyr.orm.annotation.Mapper
 */
public class MapperRegistry {
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }
    }

    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    public <T> void addMapper(Class<T> type) {
        if (!type.isInterface()) {
            return;
        }
        if (hasMapper((type))) {
            throw new RuntimeException("Type " + type + "is already known to the MapperRegistry");
        }
        boolean loadCompleted = false;
        try {
            knownMappers.put(type, new MapperProxyFactory<>(type));
            loadCompleted = true;
        } finally {
            if (!loadCompleted) {
                knownMappers.remove(type);
            }
        }
    }
}
