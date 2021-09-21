-- where to place data to populate in our database before the application runs
-- Also, with this file and the schema.sql file, we've replicated exactly what
-- Spring Security does when creating its default schema and user.
--      Furthermore, we can now delete the data we hardcoded inside the
--      WebSecurity class and only leave the datasource.
-- This serves as an example of how we could create our own schema by defining these
-- 2 files in a different way.

INSERT INTO users (username, password, enabled)
VALUES ('user', 'pass', true);

INSERT INTO users (username, password, enabled)
VALUES ('admin', 'pass', true);

INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_USER');

INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_ADMIN');

