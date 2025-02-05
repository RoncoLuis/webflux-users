CREATE TABLE users(
    id UUID default RANDOM_UUID() primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(50) not null unique,
    password varchar(255) not null
);