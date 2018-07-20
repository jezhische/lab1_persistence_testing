package com.jezh.springmvcjpa;

import com.jezh.springmvcjpa.model.User;

public class n {
    public static void main(String[] args) {
        System.out.println(new Integer(5).hashCode());
//        String a = null;
//        String b = null;
//        System.out.println(a.equals(b)); // NullPointerException

        User u = new User();
        User uu = new User();
        System.out.println(u.equals(uu));
    }
}
