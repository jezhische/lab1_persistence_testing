package com.jezh.integrTest;

import com.jezh.springmvcjpa.configuration.AppConfig;
import com.jezh.springmvcjpa.model.User;
import com.jezh.springmvcjpa.model.UserProfile;
import com.jezh.testUtil.TestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transaction;

// provides functionality of the Spring TestContext Framework to standard JUnit tests by means of the TestContextManager
// and associated support classes and annotations.
@RunWith(SpringRunner.class)
// "to load the context configuration and bootstrap the context that the test will use":
@ContextConfiguration(classes = AppConfig.class)
// "This annotation ensures that the application context which is loaded for our test is a WebApplicationContext..."
@WebAppConfiguration(value = "com.jezh.springmvcjpa.configuration.AppInitializer")
public class BaseIntegrationTest {

    @Autowired
    EntityManager entityManager;

    private EntityTransaction transaction;
    protected User user, notEmptyFailedUser;
    protected UserProfile userProfile;

// NB, that @EnableTransactionManagement does NOT create transaction in test (why?), so I need to do it explicitly.
// Otherwise the transaction will not be commited, although the test will be passed
    @Before
    public void setUp() throws Exception {
        user = TestUtil.newUser();
        userProfile = new UserProfile();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @After
    public void tearDown() throws Exception {
        if (transaction != null || !transaction.isActive()) transaction.commit();
        user = null;
        notEmptyFailedUser = null;
        userProfile = null;
    }
}
