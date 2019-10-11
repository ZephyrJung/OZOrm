package org.zephyr.spring.boot.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import org.zephyr.orm.annotation.Mapper;
import org.zephyr.orm.session.SqlSessionFactory;
import org.zephyr.orm.spring.SqlSessionFactoryBean;
import org.zephyr.orm.spring.SqlSessionTemplate;
import org.zephyr.orm.spring.mapper.ClassPathMapperScanner;
import org.zephyr.orm.spring.mapper.MapperFactoryBean;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author yu.zhang
 * @date 2019-09-17
 */
@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@ConditionalOnSingleCandidate(DataSource.class)
@EnableConfigurationProperties(ZybatisProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@Slf4j
public class ZybatisAutoConfiguration implements InitializingBean {
    private final ZybatisProperties properties;

    public ZybatisAutoConfiguration(ZybatisProperties properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() {
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        return factory.getObject();
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    public static class AutoConfiguredMapperScannerRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware {
        private BeanFactory beanFactory;

        private ResourceLoader resourceLoader;

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            if (!AutoConfigurationPackages.has(this.beanFactory)) {
                return;
            }
            ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry, Mapper.class);
            try {
                if (this.resourceLoader != null) {
                    scanner.setResourceLoader(this.resourceLoader);
                }
                List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
                if (log.isDebugEnabled()) {
                    packages.forEach(pkg -> log.debug("Using auto-configuration base package '{}'", pkg));
                }
                scanner.registerFilters();
                scanner.doScan(StringUtils.toStringArray(packages));
            } catch (IllegalStateException e) {
                log.debug("Could not determine auto-configuration package, automatic mapper scanning disabled.", e);
            }
        }

        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }
    }

    @Configuration
    @Import(AutoConfiguredMapperScannerRegistrar.class)
    @ConditionalOnMissingBean({MapperFactoryBean.class})
    public static class MapperScannerRegistarNotFoundConfiguration implements InitializingBean {
        @Override
        public void afterPropertiesSet() throws Exception {
            log.debug("Not found configuration for registering mapper bean using @MapperScan, MapperFactoryBean and MapperScannerConfigurer.");
        }
    }
}
