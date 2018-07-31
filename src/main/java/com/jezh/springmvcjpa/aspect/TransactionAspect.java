package com.jezh.springmvcjpa.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.test.context.transaction.AfterTransaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Component
@Aspect
public class TransactionAspect {

/**
 * I need this class since for unknown reason the @Transactional annotation do not work, so I need to create
 * "hand made" transactions.
 * */

private final EntityManager entityManager;
private EntityTransaction tx;

    @Autowired
    public TransactionAspect(EntityManager entityManager) {
        this.entityManager = entityManager;
        tx = entityManager.getTransaction();
    }

    // creating pointcuts

    @Pointcut("execution(* com.jezh.springmvcjpa.service.UserServiceImpl.isUserSSOUnique(..))")
    public void forIsUserSSOUnique() {}

    @Pointcut("execution(* com.jezh.springmvcjpa.service.*Impl.*(..))")
    public void beforeAllInUserServiceExceptOfIsUserSSOUnique() {};

    // combine several pointcuts
    @Pointcut("beforeAllInUserServiceExceptOfIsUserSSOUnique() && !forIsUserSSOUnique()")
    public void forTransactions() {}

    // creating advices

    @Before("forTransactions()")
    public void beginTransaction() {
//        tx = entityManager.getTransaction();
        tx.begin();
        System.out.println("************************************* begin transaction");
    }

    @After("forTransactions()")
    public void commitTransaction() {
        tx.commit();
        System.out.println("************************ commit transaction");
    }

    @AfterThrowing("forTransactions()")
    public void rollbackTransaction() {
        tx.rollback();
        System.out.println("************************ transaction failed");
    }

//    @Before("execution(public void  com.jezh.springmvcjpa.service.UserServiceImpl.saveUser(..))")
//    public void testBefore() {
//        System.out.println("***********this is the testBefore");
//    }
}
