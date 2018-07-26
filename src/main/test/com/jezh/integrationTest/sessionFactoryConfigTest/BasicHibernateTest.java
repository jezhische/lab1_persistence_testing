package com.jezh.integrationTest.sessionFactoryConfigTest;

import org.hibernate.service.ServiceRegistry;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@org.springframework.context.annotation.Configuration
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
//@WebAppConfiguration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.jezh.springmvcjpa")
@PropertySource(value = { "classpath:test.properties" })

// FIXME: the newSessionFactory() method doesn't work: ClassNotFoundException: org.hibernate.cache.spi.support.RegionFactoryTemplate

public class BasicHibernateTest {
    @Autowired
    SessionFactory sessionFactory;
    Session session = null;
    Transaction tx = null;

    @Autowired
    private Environment environment;

//    @Autowired
//    public BasicHibernateTest() {
//        sessionFactory = newSessionFactory();
//    }

    @Before
    public void setUp() throws Exception {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
    }

    @After
    public void tearDown() throws Exception {
        try {
            tx.commit();
        } catch (RuntimeException e) { // ?
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }

    // https://vladmihalcea.com/the-minimal-configuration-for-testing-hibernate/
    @Bean
    SessionFactory newSessionFactory() {
        Properties properties = new Properties();
//        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        //log settings
//        properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.show_sql", "true");
        //driver settings
//        properties.put("hibernate.connection.driver_class", environment.getProperty("hibernate.connection.driver_class"));
        properties.put("hibernate.connection.driver_class", "jdbc:hsqldb:mem:test");
//        properties.put("hibernate.connection.url", environment.getProperty("hibernate.connection.url"));
        properties.put("hibernate.connection.url", "jdbc:hsqldb:mem:test");
//        properties.put("hibernate.connection.username", environment.getProperty("hibernate.connection.username"));
        properties.put("hibernate.connection.username", "hb_root");
        properties.put("hibernate.connection.password", "");
        //c3p0 settings
        properties.put("hibernate.c3p0.min_size", 1);
        properties.put("hibernate.c3p0.max_size", 5);

//        return new Configuration()
//                .addProperties(properties)
//                .addAnnotatedClass(User.class)
//                .addAnnotatedClass(UserProfile.class)
//                .buildSessionFactory(
//                        new StandardServiceRegistryBuilder()
//                                .applySettings(properties)
//                                .build()
//                        /*new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry()*/
//                );

        Configuration configuration = new Configuration();
        configuration.addProperties(properties);
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        registryBuilder.applySettings(properties);
        ServiceRegistry registry = registryBuilder.build(); // build() returns StandardServiceRegistry
        return configuration.buildSessionFactory(registry);
    }
}

