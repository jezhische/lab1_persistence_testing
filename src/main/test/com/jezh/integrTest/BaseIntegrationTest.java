package com.jezh.integrTest;

import com.jezh.springmvcjpa.configuration.AppConfig;
import com.jezh.springmvcjpa.model.User;
import com.jezh.springmvcjpa.model.UserProfile;
import com.jezh.springmvcjpa.service.UserService;
import com.jezh.testUtil.TestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

// provides functionality of the Spring TestContext Framework to standard JUnit tests by means of the TestContextManager
// and associated support classes and annotations.
@RunWith(SpringRunner.class)
//// don't need it 'cause I have one in the JpaConfiguration class, that to be import to AppConfig class
//@EnableTransactionManagement

// this annotation retrieves PlatformTransactionManager for test context, if I wont to test
// "raw" entityManager without appropriated service classes methods
//@Transactional
// "to load the context configuration and bootstrap the context that the test will use":
@ContextConfiguration(classes = AppConfig.class)
// "This annotation ensures that the application context which is loaded for our test is a WebApplicationContext..."
@WebAppConfiguration(value = "com.jezh.springmvcjpa.configuration.AppInitializer")
public class BaseIntegrationTest {

    @Autowired
    EntityManager entityManager;

// NB, that I use @Transactional to retrieve PlatformTransactionManager for test context, so I dont't need to
// begin/commit transaction explicitly. If I haven't @Transactional or explicit transaction begin/commit,
// in this case the transaction will not be commited, although the test will be passed

//    private EntityTransaction transaction;

    protected User user, notEmptyFailedUser;
    protected UserProfile userProfile;

    @Autowired
    protected UserService userService;

    @Before
    public void setUp() throws Exception {
        user = TestUtil.newUser();
        userProfile = new UserProfile();
//        transaction = entityManager.getTransaction();
//        transaction.begin();
    }

    @After
    public void tearDown() throws Exception {
//        if (transaction != null || !transaction.isActive()) transaction.commit();
        user = null;
        notEmptyFailedUser = null;
        userProfile = null;
    }
}
