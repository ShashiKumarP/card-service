DROP TABLE IF EXISTS CREDIT_CARD;

CREATE TABLE CREDIT_CARD (
ID INTEGER NOT NULL,
CARD_NUMBER VARCHAR(255) NOT NULL UNIQUE,
CARD_LIMIT INTEGER,
CARD_HOLDER_NAME VARCHAR(255) NOT NULL, PRIMARY KEY (ID));
