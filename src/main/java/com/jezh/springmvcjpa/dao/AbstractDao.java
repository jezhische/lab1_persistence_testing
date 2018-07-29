package com.jezh.springmvcjpa.dao;

import com.jezh.springmvcjpa.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.tree.TypeArgument;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Repository
public abstract class AbstractDao<PK extends Serializable, T> {

//	При применении AbstractDao я напишу, например: public class UserProfileDaoImpl extends AbstractDao<Integer, UserProfile>.
// Т.о. T будет реализован через UserProfile.
	private final Class<T> persistentClass; // т.е. это, например, Class<UserProfile> clazz = UserProfile.class, если T = UserProfile

//	В этом конструкторе переменной persistentClass придается конкретный класс, например, UserProfile.class.
//  Здесь я имею нетривиальную задачу: определить тип параметра T. Из-за Type Erasure "во время исполнения программы
// информации о реальных параметрах нашего generic-класса может уже и не быть" (https://habr.com/post/66593/), поэтому
// запись типа T.class не будет пропущена компилятором: "Cannot select from a type variable"
	@SuppressWarnings("unchecked")
	public AbstractDao(){
	    // public interface Type:
	    // "Type is the common superinterface for all types in the Java programming language. "
        // public interface ParameterizedType extends Type:
        //ParameterizedType represents a parameterized type such as Collection<String> (т.е. параметризованный стрингом).
// NB:todo: например, this = UserProfileDaoImpl extends AbstractDao<Integer, UserProfile>; тогда this.getClass() = UserProfileDaoImpl.class;
// .getGenericSuperclass() = AbstractDao<Integer, UserProfile>.class;
// Тпереь у параметризованного AbstractDao<Integer, UserProfile> (созданного из дженерик-класса AbstractDao<PK extends Serializable, T>)
// мы можем определить параметры:
//        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
//        Type [] type = parameterizedType.getActualTypeArguments();
//        Type secondArgument = type[1]; //
//        Class<UserProfile> userProfileClass = (Class<UserProfile>) secondArgument;
//        this.persistentClass = userProfileClass;

		this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
    }

//	@PersistenceContext "Expresses a dependency on a container-managed EntityManager and its associated persistence context"
//	@PersistenceContext
//	EntityManager entityManager;

    @Autowired
    EntityManager entityManager;

	protected EntityManager getEntityManager(){
		return this.entityManager;
	}

	// really this is "getById"
	protected T getByKey(PK key) {
// not T.class, but persistentClass, 'cause "cannot select from a type variable" - Type Erasure
		return (T) entityManager.find(persistentClass, key);
	}

	protected void persist(T entity) {
		entityManager.persist(entity);
	}

	protected void update(T entity) {
		entityManager.merge(entity);
	}

	protected void delete(T entity) {
		entityManager.remove(entity);
	}
}
