CREATE DATABASE  IF NOT EXISTS `lab1`;
USE `lab1`;

create table IF NOT EXISTS APP_USER (
  id BIGINT NOT NULL AUTO_INCREMENT,
#   sso_id VARCHAR(30) NOT NULL,
  password VARCHAR(100) NOT NULL,
  first_name VARCHAR(30) NOT NULL,
  last_name  VARCHAR(30) NOT NULL,
  email VARCHAR(30) NOT NULL,
  PRIMARY KEY (id)
#   ,
#   The UNIQUE constraint ensures that all values in a column are different.
#   Both the UNIQUE and PRIMARY KEY constraints provide a guarantee for uniqueness for a column or set of columns.
#   A PRIMARY KEY constraint automatically has a UNIQUE constraint.
#   UNIQUE (sso_id)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;


# create table IF NOT EXISTS USER_PROFILE(
#   id BIGINT NOT NULL AUTO_INCREMENT,
#   type VARCHAR(30) NOT NULL,
#   PRIMARY KEY (id),
#   UNIQUE (type)
# )ENGINE=InnoDB DEFAULT CHARSET=latin1;


# CREATE TABLE IF NOT EXISTS APP_USER_USER_PROFILE (
#   user_id BIGINT NOT NULL,
#   user_profile_id BIGINT NOT NULL,
#   PRIMARY KEY (user_id, user_profile_id),
#   CONSTRAINT FK_APP_USER FOREIGN KEY (user_id) REFERENCES APP_USER (id),
#   CONSTRAINT FK_USER_PROFILE FOREIGN KEY (user_profile_id) REFERENCES USER_PROFILE (id)
# )ENGINE=InnoDB DEFAULT CHARSET=latin1;



/* Populate USER_PROFILE Table */
INSERT INTO USER_PROFILE(type)
VALUES ('USER');

INSERT INTO USER_PROFILE(type)
VALUES ('ADMIN');

INSERT INTO USER_PROFILE(type)
VALUES ('DBA');

commit;