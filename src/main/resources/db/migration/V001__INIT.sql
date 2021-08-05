CREATE TABLE books
(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    isbn  varchar(17)  NOT NULL UNIQUE,
    title varchar(255) NOT NULL,
    author  varchar(20)  NOT NULL,
    price  bigint NOT NULL,
    PRIMARY KEY (id)
);