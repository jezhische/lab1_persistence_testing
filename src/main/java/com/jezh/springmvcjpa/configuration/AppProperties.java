package com.jezh.springmvcjpa.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Properties;

@Component
@PropertySource(value = { "classpath:application.properties" })
public class AppProperties extends Properties {

    /**
     * in this constructor I specify provider specific properties.
     */
    @Autowired
    public AppProperties(Environment environment) {
        super();
        this.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        this.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        this.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        this.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
// To avoid "java.sql.BatchUpdateException: You have an error in your SQL syntax; check the manual that corresponds
// to your MySQL server version for the right syntax to use near... " - Make hibernate backquote '`' all table / column names
//		this.put("hibernate.globally_quoted_identifiers",
//                environment.getRequiredProperty("hibernate.globally_quoted_identifiers"));
    }
}
