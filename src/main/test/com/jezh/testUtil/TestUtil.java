package com.jezh.testUtil;

import com.jezh.springmvcjpa.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUtil {
    public static User newUser() {
        StringBuilder ssoBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++)
            ssoBuilder.append(generateRandomChar());

        System.out.println("/////////////////******************************** new User ssoId: " + ssoBuilder.toString());
        return new User(ssoBuilder.toString(), "password", "first", "last", "email");
    }

//    private static String randomString() {
//        String rowSample = Character.toString((char) (Math.random() * 200 - Math.random() * 100));
//        Pattern pattern = Pattern.compile("[a-zA-Z]");
//        Matcher matcher = pattern.matcher(rowSample);
//        return matcher.find() ? matcher.group() : randomString();
//    }

    private static char generateRandomChar() {
        char randomChar = (char)((Math.random() * 200 - Math.random() * 100));
        return randomChar >= 65 && randomChar <= 90 || randomChar >= 97 && randomChar <= 122 ?
                randomChar : generateRandomChar();
//        return randomChar >= 65 && randomChar <= 90 ?
//                randomChar : randomChar >= 95 && randomChar <= 122 ?
//                randomChar : generateRandomChar();
    }
}
