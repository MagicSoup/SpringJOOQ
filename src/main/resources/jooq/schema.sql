DROP TABLE IF EXISTS author_book, author, book;

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

INSERT INTO author VALUES
  ('d6a19b5e-b0af-11ea-b3de-0242ac130004', 'Kathy', 'Sierra'),
  ('ea14c2ba-b0af-11ea-b3de-0242ac130004', 'Bert', 'Bates'),
  ('ff302fc2-b0af-11ea-b3de-0242ac130004', 'Bryan', 'Basham');

INSERT INTO book VALUES
  ('08decfa6-b0b0-11ea-b3de-0242ac130004', 'Head First Java'),
  ('1d520048-b0b0-11ea-b3de-0242ac130004', 'Head First Servlets and JSP'),
  ('24a453aa-b0b0-11ea-b3de-0242ac130004', 'OCA/OCP Java SE 7 Programmer');

INSERT INTO author_book VALUES ('d6a19b5e-b0af-11ea-b3de-0242ac130004', '08decfa6-b0b0-11ea-b3de-0242ac130004'),
('d6a19b5e-b0af-11ea-b3de-0242ac130004', '24a453aa-b0b0-11ea-b3de-0242ac130004'),
('ea14c2ba-b0af-11ea-b3de-0242ac130004', '08decfa6-b0b0-11ea-b3de-0242ac130004');