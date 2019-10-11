package org.zephyr.orm.spring.support;

import org.springframework.dao.support.DaoSupport;
import org.zephyr.orm.session.SqlSession;
import org.zephyr.orm.session.SqlSessionFactory;
import org.zephyr.orm.spring.SqlSessionTemplate;

import static org.springframework.util.Assert.notNull;

/**
 * @author yu.zhang
 * @date 2019-09-23
 */
public class SqlSessionDaoSupport extends DaoSupport {
    private SqlSessionTemplate sqlSessionTemplate;

    public SqlSession getSqlSession() {
        return this.sqlSessionTemplate;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        if (this.sqlSessionTemplate == null || sqlSessionFactory != this.sqlSessionTemplate.getSqlSessionFactory()) {
            this.sqlSessionTemplate = createSqlSessionTemplate(sqlSessionFactory);
        }
    }

    protected SqlSessionTemplate createSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {
        notNull(this.sqlSessionTemplate, "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
    }
}
