PRAGMA foreign_keys = ON;
CREATE TABLE authorities (
  username TEXT NOT NULL,
  authority TEXT NOT NULL,
  UNIQUE (username, authority),
  FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE book (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  url_id TEXT NOT NULL,
  title TEXT NOT NULL,
  author TEXT NOT NULL,
  stars INTEGER,
  price REAL,
  favorite INTEGER,
  image_url TEXT,
  UNIQUE (url_id)
);

CREATE TABLE book_tag (
  book_id INTEGER NOT NULL,
  tag_id INTEGER NOT NULL,
  PRIMARY KEY (book_id, tag_id),
  FOREIGN KEY (book_id)
    REFERENCES book (id),
  FOREIGN KEY (tag_id) REFERENCES tag (id)
);

CREATE TABLE tag (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  name TEXT NOT NULL,
  UNIQUE (name)
);

CREATE TABLE users (
  username TEXT NOT NULL PRIMARY KEY,
  password TEXT NOT NULL,
  enabled INTEGER NOT NULL
);