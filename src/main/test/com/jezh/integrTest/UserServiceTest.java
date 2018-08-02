package com.jezh.integrTest;


import com.jezh.springmvcjpa.model.User;
import com.jezh.springmvcjpa.model.UserProfile;
import com.jezh.springmvcjpa.model.UserProfileType;
import com.jezh.springmvcjpa.service.UserProfileService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserServiceTest extends BaseIntegrationTest {

    @Test
    public void testUserPersist() {
        userService.saveUser(user);
    }

    @Test
    public void testUserFinById() {
        userService.saveUser(user);
        User persistedUser = userService.findById(user.getId());
        Assert.assertNotNull(persistedUser);
        System.out.println(persistedUser);
    }

    @Test
    public void testDeleteUser() {
        Assert.assertNull(userService.findBySSO(user.getSsoId()));
        userService.saveUser(user);
        Assert.assertNotNull(userService.findBySSO(user.getSsoId()));
        userService.deleteUserBySSO(user.getSsoId());
        Assert.assertNull(userService.findBySSO(user.getSsoId()));
    }

    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    EntityManager entityManager;
    private UserProfile userProfile;
    private EntityTransaction tx;

    /**
     * NB: this test can be done only once, and in second time I'll get ConstraintViolationException. To run this test
     * one more time, I need firstly to run testDeleteUserProfilesFromDB()
     */
    @Test
    public void setUserProfileInDB() {
        tx = entityManager.getTransaction();
        tx.begin();
        Arrays.asList(UserProfileType.values()).forEach(userProfileType -> {
            userProfile = new UserProfile();
            userProfile.setType(userProfileType.getUserProfileType());
//            entityManager.joinTransaction();
            entityManager.persist(userProfile);
        });
        tx.commit();
    }

    @Test
    public void testDeleteUserProfilesFromDB() {
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.createQuery("from UserProfile").getResultList().forEach(uPr -> entityManager.remove((UserProfile)uPr));
        tx.commit();
    }
        @Test
    public void testUpdateUserSetSsoId() {
        userService.findAllUsers().forEach(retrievedUser -> {
            Set<UserProfile> upSet = new HashSet<>();
// Get List of UserProfiles stored in DB, then add them in HashSet, and then set this Set to each User
            entityManager.createQuery("from UserProfile").getResultList().forEach(uPr -> upSet.add((UserProfile) uPr));
            retrievedUser.setUserProfiles(upSet);
            userService.updateUser(retrievedUser);
        });
    }
}
