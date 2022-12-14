-- One to Many
DROP TABLE IF EXISTS Book;
DROP TABLE IF EXISTS Person;

CREATE TABLE IF NOT EXISTS Person(
	id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	fullname varchar(300) NOT NULL UNIQUE,
	year_of_birth int CHECK(year_of_birth >= 1900 AND year_of_birth <= 2022)
);

CREATE TABLE IF NOT EXISTS Book(
	id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	person_id int REFERENCES Person(id) ON DELETE SET NULL,
	name varchar(100) NOT NULL,
	author varchar(100) NOT NULL,
	year int,
	taken_at date
);