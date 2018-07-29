package com.jezh.springmvcjpa.configuration;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

//@Configuration
//@EnableTransactionManagement
// @ComponentScan see AppConfig
public class SessionFactoryConfig {

//    private AppProperties appProperties;
//    private DataSource dataSource;
//
//    @Autowired
//    public SessionFactoryConfig(AppProperties appProperties, DataSource dataSource) {
//        this.appProperties = appProperties;
//        this.dataSource = dataSource;
//    }
//
//    @Bean
//    public LocalSessionFactoryBean sessionFactory(){
//
//        // create session factory
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//
//        // set the properties
//        sessionFactory.setDataSource(dataSource);
//        sessionFactory.setPackagesToScan("com.jezh.springmvcjpa");
//        sessionFactory.setHibernateProperties(appProperties);
//        return sessionFactory;
//    }
//
////    @Bean
////    public SessionFactory sessionFactory() {
////        return sessionFactory()
////                .getConfiguration()
////                .addProperties(jpaProperties())
////                .buildSessionFactory(new StandardServiceRegistryBuilder()/*.applySetting(jpaProperties())*/.build());
////    }
//
//
//    @Bean
//    @Autowired
//    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
//
//        // setup transaction manager based on session factory
//        HibernateTransactionManager txManager = new HibernateTransactionManager();
//        txManager.setSessionFactory(sessionFactory);
//
//        return txManager;
//    }
}
