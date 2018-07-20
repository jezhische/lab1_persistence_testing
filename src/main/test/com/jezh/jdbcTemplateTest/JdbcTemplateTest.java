package com.jezh.jdbcTemplateTest;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

//@Component
public class JdbcTemplateTest {

//    @Autowired
//    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;
    private EmbeddedDatabase embeddedDatabase;
//    private PersonRepository personRepository;

//    @PostConstruct
//    private void postConstruct() {
//        jdbcTemplate = new JdbcTemplate(dataSource);
//    }


//    -----------------------------------------------------------------------------------


    @Before
    public void setUp() {
        // Создадим базу данных для тестирования
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()// Добавляем скрипты schema.sql и data.sql
                .setType(EmbeddedDatabaseType.H2)// Используем базу H2
                .build();

        // Создадим JdbcTemplate
        jdbcTemplate = new JdbcTemplate(embeddedDatabase);

        // Создадим PersonRepository
//        personRepository = new PersonRepositoryImpl(jdbcTemplate);
    }

    @After
    public void tearDown() {
        embeddedDatabase.shutdown();
    }
}
