CREATE TABLE authors
(
    id         UUID         NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_authors PRIMARY KEY (id)
);

CREATE TABLE books
(
    id         UUID                        NOT NULL,
    title      VARCHAR(255)                NOT NULL,
    author_id  UUID,
    pages      VARCHAR(255)                NOT NULL,
    price      DECIMAL                     NOT NULL,
    stock      INTEGER                     NOT NULL,
    created_ad TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    is_deleted BOOLEAN,
    CONSTRAINT pk_books PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         UUID NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    is_admin   BOOLEAN,
    is_blocked BOOLEAN,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE books
    ADD CONSTRAINT FK_BOOKS_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES authors (id);