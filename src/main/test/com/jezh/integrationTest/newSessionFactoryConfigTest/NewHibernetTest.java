package com.jezh.integrationTest.newSessionFactoryConfigTest;

import com.jezh.springmvcjpa.model.User;
import com.jezh.springmvcjpa.model.UserProfile;
import com.jezh.springmvcjpa.model.UserProfileType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


public class NewHibernetTest extends NewBasicHibernateTest {

    private User user1;

    private UserProfile userProfile1;
    private UserProfile userProfile2;

    public NewHibernetTest() { //  "Test class should have exactly one public constructor"
        super();
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        userProfile1 = new UserProfile();
//        userProfile1.setType();
        userProfile2 = new UserProfile();
        userProfile2.setType(UserProfileType.ADMIN.getUserProfileType());
        Set<UserProfile> userProfileSet = new HashSet<>();
        userProfileSet.add(userProfile1);
        userProfileSet.add(userProfile2);

        user1 = new User();
        user1.setEmail("guhu");
        user1.setFirstName("Oi");
        user1.setLastName("Koi");
        user1.setPassword("pass");
        user1.setUserProfiles(userProfileSet);
    }

    @Test
    public void testUserProfilePersist() {
        session.persist(userProfile1);
        session.persist(userProfile2);
    }
}
