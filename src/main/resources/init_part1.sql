CREATE TABLE user1.person (
  id serial PRIMARY KEY,
  name varchar(50),
  surname varchar(50),
  city varchar(50),
  street varchar(50),
  zip_code varchar(50)
)

ALTER TABLE user1.person ADD COLUMN billing_street varchar(50);
ALTER TABLE user1.person ADD COLUMN billing_city varchar(50);
ALTER TABLE user1.person ADD COLUMN billing_zip_code varchar(50);

CREATE TABLE user1.Author (
  id serial PRIMARY KEY,
  name varchar(50),
  surname varchar(50)
);
CREATE TABLE user1.Book (
  id serial PRIMARY KEY,
  author_id integer,
  title varchar(50),
  student_id integer
);
CREATE TABLE user1.Student (
  id serial PRIMARY KEY,
  name varchar(50),
  surname varchar(50)
);
CREATE TABLE user1.Computer (
  id serial PRIMARY KEY,
  serial_number varchar(50),
  device_name varchar(50),
  localization varchar(50)
);
CREATE TABLE user1.computer_student (
  computer_id integer,
  student_id integer,
  PRIMARY KEY (computer_id,student_id)
);