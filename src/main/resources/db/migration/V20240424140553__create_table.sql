CREATE TABLE animal
(
    id      SERIAL        NOT NULL  PRIMARY KEY,
    name    VARCHAR(30),
    type    VARCHAR(30)   NOT NULL,
    sex     VARCHAR(6)    NOT NULL,
    weight  REAL          NOT NULL,
    cost    REAL          NOT NULL
);