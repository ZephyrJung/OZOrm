/**
 * Copyright 2009-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zephyr.orm.impl;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.zephyr.orm.SqlSession;
import org.zephyr.orm.binding.MapperRegistry;
import org.zephyr.orm.executor.MysqlExecutor;

import java.sql.SQLException;
import java.util.List;

/**
 * The primary Java interface for working with MyBatis.
 * Through this interface you can execute commands, get mappers and manage transactions.
 *
 * @author Clinton Begin
 * <p>
 * copied from mybatis
 * <p>
 * 实现的时候暂时先忽略这里，当做不存在，只考虑用MysqlExecutor实现功能
 */
@Data
public class SqlSessionImpl implements SqlSession {

    private MapperRegistry mapperRegistry;
    private MysqlExecutor mysqlExecutor;

    public <T> T selectOne(Class<T> clazz, String statement, Object... parameters) {
        List<T> result = selectList(clazz, statement, parameters);
        return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
    }

    public <E> List<E> selectList(Class<E> clazz, String statement, Object... parameters) {
        try {
            return mysqlExecutor.select(clazz, statement, parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insert(String statement, Object... parameters) {
        try {
            return mysqlExecutor.update(statement, parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int update(String statement, Object... parameters) {
        try {
            return mysqlExecutor.update(statement, parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int delete(String statement, Object... parameters) {
        try {
            return mysqlExecutor.update(statement, parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegistry.getMapper(type);
    }
}
