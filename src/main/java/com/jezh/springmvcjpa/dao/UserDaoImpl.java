package com.jezh.springmvcjpa.dao;

import com.jezh.springmvcjpa.model.User;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.List;


@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	public User findById(int id) {
		User user = getByKey(id);
		if(user!=null){
			initializeCollection(user.getUserProfiles());
		}
		return user;
	}

	public User findBySSO(String sso) {
		System.out.println("SSO : "+sso);
		try{
			User user = (User) getEntityManager()
// NB: not "APP_USER" like the table name, but "User" like the class name - that is HQL feature
					.createQuery("SELECT u FROM User u WHERE u.ssoId LIKE :ssoId")
					.setParameter("ssoId", sso)
					.getSingleResult();
//
			if(user!=null){
				initializeCollection(user.getUserProfiles());
			}
			return user;
//			thrown by Query.getSingleResult() like many other PersistenceException's here (inherited from RuntimeException):
		}catch(NoResultException ex){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		List<User> users = getEntityManager()
				.createQuery("SELECT u FROM User u ORDER BY u.firstName ASC")
				.getResultList();
		return users;
	}

	public void save(User user) {
		persist(user);
	}

	public void deleteBySSO(String sso) {
		User user = (User) getEntityManager()
				.createQuery("SELECT u FROM User u WHERE u.ssoId LIKE :ssoId")
				.setParameter("ssoId", sso)
				.getSingleResult();
		delete(user);
	}

	//An alternative to Hibernate.initialize(collection); or Hibernate.initialize(user.getUserProfiles());
//	To avoid "LazyInitializationException – could not initialize proxy – no Session" (this happens if I try to access
// @ManyToMany Set<UserProfile> outside a session), I need previously to fetch collection inside a session
// with Hibernate.initialize(user.getUserProfiles());
	protected void initializeCollection(Collection<?> collection) {
	    if(collection == null) {
	        return;
	    }
// Since fetch = FetchType.LAZY, I simply call any operation on retrieved collection to initialize it
	    collection.iterator().hasNext();
	}

}
