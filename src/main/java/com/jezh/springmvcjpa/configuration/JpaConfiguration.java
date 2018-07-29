package com.jezh.springmvcjpa.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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
public class JpaConfiguration {

	private final Environment environment;
	private final AppProperties appProperties;

    @Autowired
    public JpaConfiguration(Environment environment, AppProperties appProperties) {
        this.environment = environment;
        this.appProperties = appProperties;
    }

    @Bean
	public DataSource dataSource() {
// Simple implementation of the standard JDBC DataSource interface... This class is not an actual connection pool;
// it does not actually pool Connections.
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
		factoryBean.setPackagesToScan("com.jezh.springmvcjpa");
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.setJpaProperties(appProperties);
		return factoryBean;
	}

// Without following I have Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean
// of type 'javax.persistence.EntityManager' available: expected at least 1 bean which qualifies as autowire candidate.
// Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
    // Don't know why LocalContainerEntityManagerFactoryBean does't satisfy above terms.
    @Bean
    public EntityManager entityManager() /*throws NamingException*/ {
        EntityManagerFactory emf = entityManagerFactory().getNativeEntityManagerFactory();
        return emf.createEntityManager();
    }

	/**
	 * Provider specific adapter.
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
	}

	// see HibernateTransactionManager transactionManager() in SessionFactoryConfig
//	@Bean
//	@Autowired
//    @Qualifier("entityManagerFactory")
//	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//		JpaTransactionManager txManager = new JpaTransactionManager();
//		txManager.setEntityManagerFactory(emf);
//		return txManager;
//	}
}
