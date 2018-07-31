package com.jezh.integrTest;


import com.jezh.springmvcjpa.model.User;
import org.junit.Assert;
import org.junit.Test;

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
}
