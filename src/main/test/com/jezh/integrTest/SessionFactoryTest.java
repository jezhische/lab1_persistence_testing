package com.jezh.integrTest;

import com.jezh.springmvcjpa.configuration.AppConfig;
import com.jezh.springmvcjpa.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration(value = "com.jezh.springmvcjpa.configuration.AppInitializer")
public class SessionFactoryTest {

    @Autowired
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    private User user, notEmptyFailedUser;


    @Before
    public void setUp() throws Exception {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        user = new User("password", "firstWithSessionFactory", "lastWithSessionFactory",
                "emailWithSessionFactory");
        notEmptyFailedUser = new User();
    }

    @After
    public void tearDown() throws Exception {
        transaction.commit();
        session.close();
    }

    @Test
    public void testUserSave() {
        session.save(user);
//        session.persist(user);
    }

    @Test(expected = /*org.hibernate.PropertyValueException.class, */org.hibernate.AssertionFailure.class)
    public void testNotEmptyFailedUserSave() {
        try {
            session.save(notEmptyFailedUser);
        } catch (org.hibernate.AssertionFailure e) {
//            e.printStackTrace();
        }
    }

    @Test
    public void testUserPersist() {
//        session.save(user);
        session.persist(user);
    }
}
