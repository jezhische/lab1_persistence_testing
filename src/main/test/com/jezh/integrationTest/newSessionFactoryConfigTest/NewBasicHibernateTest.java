package com.jezh.integrationTest.newSessionFactoryConfigTest;

import com.jezh.sessionFactoryConfig.SessionFactoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//import org.springframework.context.annotation.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SessionFactoryConfig.class)

// FIXME: the newSessionFactory() method doesn't work: ClassNotFoundException: org.hibernate.cache.spi.support.RegionFactoryTemplate

public class NewBasicHibernateTest {
    @Autowired
// @Autowired (LocalSessionFactoryBean from SessionFactoryConfig) is possible here
// due to @ContextConfiguration(classes = SessionFactoryConfig.class)
    SessionFactory sessionFactory;
    Session session = null;
    Transaction tx = null;

    @Autowired
    private Environment environment;


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
}

