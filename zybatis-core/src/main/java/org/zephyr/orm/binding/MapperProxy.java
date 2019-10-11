package org.zephyr.orm.binding;

import org.zephyr.orm.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author yu.zhang
 * @date 2019-09-17
 */
public class MapperProxy<T> implements InvocationHandler {
    private SqlSession sqlSession;
    private final Map<Method, MapperMethod> methodCache;

    public MapperProxy(SqlSession sqlSession,Map<Method, MapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final MapperMethod mapperMethod = cachedMapperMethod(method);
        return mapperMethod.execute(sqlSession,args);
    }

    private MapperMethod cachedMapperMethod(final Method method){
        return methodCache.computeIfAbsent(method,k->new MapperMethod(method));
    }
}
