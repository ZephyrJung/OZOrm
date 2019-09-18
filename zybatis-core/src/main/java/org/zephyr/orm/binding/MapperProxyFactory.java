package org.zephyr.orm.binding;

import lombok.Data;
import org.zephyr.orm.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yu.zhang
 * @date 2019-09-17
 */
@Data
public class MapperProxyFactory<T> {
    private final Class<T> mapperInterface;
    /**
     * 缓存对于工厂下的多个实例应该是可以共享的
     */
    private final Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<>();

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @SuppressWarnings("unchecked")
    public T newInstance(SqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, new MapperProxy(sqlSession, methodCache));
    }
}
