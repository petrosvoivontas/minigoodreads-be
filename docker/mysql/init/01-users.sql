CREATE
    USER 'minigoodreads'@'%' IDENTIFIED BY 'minigoodreads';

GRANT ALL PRIVILEGES ON *.* TO
    'minigoodreads'@'%';

CREATE TABLE users
(
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) not null,
    enabled  boolean      not null
);

CREATE TABLE IF NOT EXISTS authorities
(
    username  varchar(50) NOT NULL,
    authority varchar(50) NOT NULL,
    unique (username, authority),
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);

INSERT INTO users
VALUES ('admin', '$2a$12$0lG8b6eAn4VSsqdY8ZXAKuBDOU/VwmKjPeWoWijLsaOWA4BDTuMmW', true);

INSERT INTO authorities
VALUES ('admin', 'ROLE_ADMIN')