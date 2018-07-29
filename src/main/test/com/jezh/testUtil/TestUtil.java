package com.jezh.testUtil;

import com.jezh.springmvcjpa.model.User;

public class TestUtil {
    public static User newUser() {
        StringBuilder ssoBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++)
            ssoBuilder.append((char) (Math.random() * 30 + 65));

        System.out.println("/////////////////********************************" + ssoBuilder.toString());
        return new User(ssoBuilder.toString(), "password", "first", "last", "email");
    }
}
