package org.zephyr.orm.binding;

import org.zephyr.orm.SqlSession;
import org.zephyr.orm.annotation.Delete;
import org.zephyr.orm.annotation.Insert;
import org.zephyr.orm.annotation.Select;
import org.zephyr.orm.annotation.Update;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yu.zhang
 * @date 2019-09-17
 */
public class MapperMethod {
    private Method method;

    public MapperMethod(Method method) {
        this.method = method;
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        List<Class> annotations = Arrays.stream(method.getDeclaredAnnotations()).map(Annotation::annotationType).collect(Collectors.toList());
        if (annotations.contains(Select.class)) {
            Select select = method.getAnnotation(Select.class);
            return sqlSession.selectOne(method.getReturnType(), select.value()[0], args);
        }
        if (annotations.contains(Delete.class)) {
            Delete delete = method.getAnnotation(Delete.class);
            return sqlSession.delete(delete.value()[0], args);
        }
        if (annotations.contains(Update.class)) {
            Update update = method.getAnnotation(Update.class);
            return sqlSession.update(update.value()[0], args);
        }
        if (annotations.contains(Insert.class)) {
            Insert insert = method.getAnnotation(Insert.class);
            return sqlSession.insert(insert.value()[0], args);
        }
        throw new RuntimeException("method no support!");
    }
}
