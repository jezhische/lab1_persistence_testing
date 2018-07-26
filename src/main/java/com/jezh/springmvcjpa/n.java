package com.jezh.springmvcjpa;

import com.jezh.springmvcjpa.dao.AbstractDao;
import com.jezh.springmvcjpa.dao.UserProfileDaoImpl;
import com.jezh.springmvcjpa.model.User;
import com.jezh.springmvcjpa.model.UserProfile;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public class n {
    public static void main(String[] args) {
        System.out.println(new Integer(5).hashCode());
//        String a = null;
//        String b = null;
//        System.out.println(a.equals(b)); // NullPointerException

        User u = new User();
        User uu = new User();
        System.out.println(u.equals(uu));

        class Y {}
        ArrayList<Y> yyy = new ArrayList<>();
        yyy.add(new Y());
        Object ou = yyy.get(0);
        System.out.println(yyy.get(0).getClass());
        System.out.println(ou.getClass());

        System.out.println("------------------------------------------------");

        Class<UserProfileDaoImpl> clazz = UserProfileDaoImpl.class;
        System.out.println(clazz);
        System.out.println(clazz.getGenericSuperclass());
//        System.out.println((Class<UserProfile>)((ParameterizedType) UserProfile.class.getGenericSuperclass()).getActualTypeArguments()[1]);
        class FloatList extends ArrayList<Float>{};
        ArrayList<Float> listOfNumbers = new FloatList();
        Class actualClass = listOfNumbers.getClass();
        ParameterizedType type = (ParameterizedType)actualClass.getGenericSuperclass();
        System.out.println(type);
        System.out.println(listOfNumbers.getClass());
        System.out.println(listOfNumbers.getClass().getGenericSuperclass());

    }
}
