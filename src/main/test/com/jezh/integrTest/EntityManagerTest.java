package com.jezh.integrTest;

import com.jezh.springmvcjpa.configuration.AppConfig;
import com.jezh.springmvcjpa.configuration.JpaConfiguration;
import com.jezh.springmvcjpa.dao.AbstractDao;
import com.jezh.springmvcjpa.dao.UserDao;
import com.jezh.springmvcjpa.dao.UserDaoImpl;
import com.jezh.springmvcjpa.model.User;
import com.jezh.springmvcjpa.service.UserService;
import com.jezh.testUtil.TestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionManager;

public class EntityManagerTest extends BaseIntegrationTest {

    @Test
    public void testUserPersist() {
        entityManager.persist(user);
//        entityManager.flush();
    }

    @Test
    public void testUserFindById() {
        // retrieve user with id = 1
        User user = entityManager.find(User.class, 1);
        System.out.println(user);
    }

//    First javax.persistence.PersistenceException will be thrown, and only second there will be
// org.hibernate.exception.ConstraintViolationException?? Maybe PersistenceException is only some emersion of
//    ConstraintViolationException?
    @Test(expected = javax.persistence.PersistenceException.class)
    public void testUniqueSsoId() {
        String randomSsoId = user.getSsoId();
        User user1 = new User(randomSsoId, "password", "first", "last", "email");;
        User user2 = new User(randomSsoId, "password", "first", "last", "email");;
        entityManager.persist(user1);
        entityManager.persist(user2);
    }
}
