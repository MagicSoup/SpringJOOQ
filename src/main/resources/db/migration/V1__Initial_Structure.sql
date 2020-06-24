CREATE TABLE author (
  uuid           VARCHAR(64) NOT NULL PRIMARY KEY,
  first_name     VARCHAR(50) NOT NULL,
  last_name      VARCHAR(50)  NOT NULL
);

CREATE TABLE book (
  uuid           VARCHAR(64)  NOT NULL PRIMARY KEY,
  title          VARCHAR(100) NOT NULL
);

CREATE TABLE author_book (
  author_uuid      VARCHAR(64) NOT NULL,
  book_uuid        VARCHAR(64) NOT NULL,
  PRIMARY KEY (author_uuid, book_uuid),
  CONSTRAINT fk_ab_author     FOREIGN KEY (author_uuid)  REFERENCES author (uuid)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_ab_book       FOREIGN KEY (book_uuid)    REFERENCES book   (uuid)
);