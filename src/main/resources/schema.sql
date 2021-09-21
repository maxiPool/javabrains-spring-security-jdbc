-- https://docs.spring.io/spring-security/site/docs/current/reference/html5/#user-schema
-- schema.sql is a Sprint Boot convention for a file that Sprint utilizes to create a schema!
-- Basically, here you can setup your database before the application starts.

create table users(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(50) not null,
    enabled boolean not null
);

create table authorities (
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);
