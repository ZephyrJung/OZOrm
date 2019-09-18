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
package org.zephyr.orm;

import java.util.List;

/**
 * The primary Java interface for working with MyBatis.
 * Through this interface you can execute commands, get mappers and manage transactions.
 *
 * @author Clinton Begin
 */
public interface SqlSession {

    <T> T selectOne(Class<T> clazz, String statement, Object... parameters);

    <E> List<E> selectList(Class<E> clazz, String statement, Object... parameter);

    int insert(String statement, Object... parameters);

    int update(String statement, Object... parameters);

    int delete(String statement, Object... parameters);

    <T> T getMapper(Class<T> type);
}
