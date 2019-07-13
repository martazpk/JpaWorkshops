CREATE TABLE ailleron.Employee (
  id serial PRIMARY KEY,
  name varchar(50)
);

CREATE TABLE ailleron.contract_employee (
  id serial PRIMARY KEY,
  name varchar(50),
  pay_per_hour numeric
);

CREATE TABLE ailleron.regular_employee (
  id serial PRIMARY KEY,
  name varchar(50),
  salary numeric
);

CREATE TABLE ailleron.Product (
  id serial PRIMARY KEY,
  color varchar(50),
  rubber varchar(50),
  type varchar(50)
);

CREATE TABLE ailleron.portal_user (
  id serial PRIMARY KEY,
  portal_name varchar(50),
  version integer
);