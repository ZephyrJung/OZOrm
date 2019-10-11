package org.zephyr.orm.spring;

import org.springframework.beans.factory.DisposableBean;
import org.zephyr.orm.model.Configuration;
import org.zephyr.orm.session.SqlSession;
import org.zephyr.orm.session.SqlSessionFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author yu.zhang
 * @date 2019-09-23
 */
public class SqlSessionTemplate implements SqlSession, DisposableBean {
    private final SqlSession sqlSessionProxy;
    private final SqlSessionFactory sqlSessionFactory;

    public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        sqlSessionProxy = (SqlSession) Proxy.newProxyInstance(SqlSessionFactory.class.getClassLoader(), new Class[]{SqlSession.class}, new SqlSessionInterceptor());
    }

    @Override
    public void destroy() throws Exception {

    }

    public SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSessionFactory;
    }

    @Override
    public <T> T selectOne(Class<T> clazz, String statement, Object... parameters) {
        return this.sqlSessionProxy.selectOne(clazz, statement, parameters);
    }

    @Override
    public <E> List<E> selectList(Class<E> clazz, String statement, Object... parameter) {
        return this.sqlSessionProxy.selectList(clazz, statement, parameter);
    }

    @Override
    public int insert(String statement, Object... parameters) {
        return this.sqlSessionProxy.insert(statement, parameters);
    }

    @Override
    public int update(String statement, Object... parameters) {
        return this.sqlSessionProxy.update(statement, parameters);
    }

    @Override
    public int delete(String statement, Object... parameters) {
        return this.sqlSessionProxy.delete(statement, parameters);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return this.getConfiguration().getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return this.sqlSessionFactory.getConfiguration();
    }

    private class SqlSessionInterceptor implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            return method.invoke(sqlSession, args);
        }
    }
}
