package com.jezh;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.MappingSqlQueryWithParameters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class noth {

//    FunctionalInterface
    RowMapper<?> rowMapper = (resultSet, rowNum) ->
{return new Object();}; // return new Person(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("email"));
//            new RowMapper<Object>() {
//        @Override
//        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//            return null;
//        }
//    };

    MappingSqlQuery<?> mappingSqlQuery = new MappingSqlQuery<Object>() {
        @Override
        protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return null;
        }
    };

    MappingSqlQueryWithParameters<?> mappingSqlQueryWithParameters = new MappingSqlQueryWithParameters<Object>() {
        @Override
        protected Object mapRow(ResultSet rs, int rowNum, Object[] parameters, Map<?, ?> context) throws SQLException {
            return null;
        }
    };
}
