package com.jezh.sessionFactoryConfig;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

// FIXME
//@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.jezh.springmvcjpa")
@PropertySource(value = { "classpath:test.properties", "classpath:application.properties" })
public class SessionFactoryConfig {

    @Autowired
    private Environment env;

// FIXME
//    @Bean
// todo: NB: (??) autowiring: org.hibernate.SessionFactory == org.springframework.orm.hibernate5.LocalSessionFactoryBean
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(testDataSource());
        sessionFactoryBean.setPackagesToScan("com.jezh.springmvcjpa.model");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    @Bean
    public DataSource testDataSource() {
// Simple implementation of the standard JDBC DataSource interface... This class is not an actual connection pool;
// it does not actually pool Connections.
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("hibernate.connection.driver_class"));
        dataSource.setUrl(env.getRequiredProperty("hibernate.connection.url"));
        dataSource.setUsername(env.getRequiredProperty("hibernate.connection.username"));
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    @Autowired
// todo: NB: (??) autowiring: org.hibernate.SessionFactory == org.springframework.orm.hibernate5.LocalSessionFactoryBean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactoryBean) {
        HibernateTransactionManager txManager
                = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactoryBean);
        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }


    private Properties hibernateProperties() {
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
//         properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.test.hbm2ddl.auto"));
//        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
//        properties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
        return new Properties() {
            {
                setProperty("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
                setProperty("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.test.hbm2ddl.auto"));
                setProperty("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
                setProperty("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
// To avoid "java.sql.BatchUpdateException: You have an error in your SQL syntax; check the manual that corresponds
// to your MySQL server version for the right syntax to use near... " - Make hibernate backquote '`' all table / column names
                setProperty("hibernate.globally_quoted_identifiers",
                        env.getRequiredProperty("hibernate.globally_quoted_identifiers"));
            }
        };
    }
}
