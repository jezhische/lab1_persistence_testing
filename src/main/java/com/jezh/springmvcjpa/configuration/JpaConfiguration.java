package com.jezh.springmvcjpa.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.Transaction;
import java.util.Properties;

@Configuration
// responsible for registering the necessary Spring components that power annotation-driven transaction management
@EnableTransactionManagement
// @ComponentScan see AppConfig
// ...mechanism for adding a PropertySource to Spring's Environment. To be used in conjunction with @Configuration classes.
// I don't know why, but in this configuration class spring can autowire environment without explicit defining of this
// annotation. Maybe because of I push collecting of hibernate properties to separate class - see AppProperties class
@PropertySource(value = {"classpath:application.properties"})
public class JpaConfiguration {


// FIXME: Annotation @Transactional in service classes retrieves appropriate bean of PlatformTransactionManager, BUT THE
// TRANSACTIONS DO NOT COMMIT by some unknown reason, so I must to create transactions explicitly in AbstractDao class

	private final Environment environment;
	private final AppProperties appProperties;

    @Autowired
    public JpaConfiguration(Environment environment, AppProperties appProperties) {
        this.environment = environment;
        this.appProperties = appProperties;
    }

    @Bean
	public DataSource dataSource() {
// DriverManagerDataSource - Simple implementation of the standard JDBC DataSource interface... This class is not
// an actual connection pool; it does not actually pool Connections.
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() /*throws NamingException*/ {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
    	// "Specify packages to search for autodetection of your entity classes in the classpath":
		factoryBean.setPackagesToScan("com.jezh.springmvcjpa.model");
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		// another option:
//        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factoryBean.setJpaProperties(appProperties);
		return factoryBean;
	}

// Without following I have Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean
// of type 'javax.persistence.EntityManager' available: expected at least 1 bean which qualifies as autowire candidate.
// Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
    // Don't know why LocalContainerEntityManagerFactoryBean does't satisfy above terms.
    @Bean
    public EntityManager entityManager() /*throws NamingException*/ {
        EntityManagerFactory emFactory = entityManagerFactory().getNativeEntityManagerFactory();
        return emFactory.createEntityManager();
    }

	/**
	 * Provider specific adapter.
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
	}

	// see also HibernateTransactionManager transactionManager() in SessionFactoryConfig
//	@Bean
////	@Autowired
////    @Qualifier("entityManagerFactory")
//	public PlatformTransactionManager transactionManager(/*EntityManagerFactory emf*/) {
//		JpaTransactionManager txManager = new JpaTransactionManager();
//		txManager.setEntityManagerFactory(entityManagerFactory().getNativeEntityManagerFactory());
//		return txManager;
//	}
//	it's work also like following:
//    @Bean
//    @Autowired
//    @Qualifier("entityManagerFactory")
//    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//        .........
//        txManager.setEntityManagerFactory(emf);
//        .......
//}
   @Bean
//   @Autowired
    public JpaTransactionManager transactionManager(/*EntityManagerFactory emf*/) {
       JpaTransactionManager txManager = new JpaTransactionManager();
       txManager.setEntityManagerFactory(entityManagerFactory().getObject());
       return txManager;
   }
}
