package com.jezh.integrTest;

import com.jezh.springmvcjpa.configuration.AppConfig;
import com.jezh.springmvcjpa.configuration.JpaConfiguration;
import com.jezh.springmvcjpa.dao.AbstractDao;
import com.jezh.springmvcjpa.dao.UserDao;
import com.jezh.springmvcjpa.dao.UserDaoImpl;
import com.jezh.springmvcjpa.model.User;
import com.jezh.springmvcjpa.service.UserService;
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

@RunWith(SpringJUnit4ClassRunner.class)
// "to load the context configuration and bootstrap the context that the test will use":
@ContextConfiguration(classes = {AppConfig.class})
@EnableTransactionManagement
// "This annotation ensures that the application context which is loaded for our test is a WebApplicationContext..."
// Иначе получаем  "No qualifying bean of type 'javax.servlet.ServletContext' available"
@WebAppConfiguration(value = "com.jezh.springmvcjpa.configuration.AppInitializer")
public class EntityManagerTest {

    @Autowired
    EntityManager entityManager;

//    @Autowired
//    JpaTransactionManager transactionManager;

    @Test
    public void testUserPersist() {
        User user = new User("password", "first", "last", "email");
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(user);
        transaction.commit();
    }

//    @Test
//    public void testUserPersistWithFlush() {
//        User user = new User("password", "first", "last", "email");
////        EntityTransaction transaction = entityManager.getTransaction();
////        transaction.begin();
//        entityManager.joinTransaction();
//        entityManager.persist(user);
//        entityManager.flush();
////        transaction.commit();
//    }

    @Test
    public void testUserFindById() {
        User user = entityManager.find(User.class, 1);
        System.out.println(user);
    }


    //    @Autowired
//    UserDao userDao;
//
//    @Autowired
//    UserService userService;

//    @Resource
//    @Qualifier("sessionFactory")
//    SessionFactory sessionFactory;

//    @Test
//    public void testUserPersistWithSessionFactory() {
//        User user = new User("password", "first", "last", "email");
//        Session session = sessionFactory.getCurrentSession();
//        Transaction transaction = session.beginTransaction();
//        session.save(user);
//        transaction.commit();
//    }

//    @Test
//    public void testUserService() {
//        User user = new User("password", "first", "last", "email");
//        userService.saveUser(user);
//    }
//
//    @Test
//    public void testUserDao() {
//        User user = new User("password", "first", "last", "email");
//        userDao.save(user);
//    }
}
