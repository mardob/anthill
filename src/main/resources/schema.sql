CREATE TABLE USERS(
  ID BIGINT AUTO_INCREMENT NOT NULL,
  FIRST_NAME VARCHAR(250),
  LAST_NAME VARCHAR(250),
  USER_NAME VARCHAR(100),
  EMAIL VARCHAR(250),
  DATE_CREATED DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  LAST_LOGGED_IN DATE,
  LAST_UPDATED DATE,
  PRIMARY KEY (ID)
);

CREATE TABLE TICKET(
  TICKET_ID BIGINT AUTO_INCREMENT NOT NULL,
  REPORTER_ID BIGINT,
  ASSIGNEE_ID BIGINT,
  PILE BIGINT,
  NAME VARCHAR(250),
  DESCRIPTION VARCHAR(4000),
  DATE_CREATED DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  LAST_CHECKED DATE,
  LAST_UPDATED DATE,
  STATE int,
  SEVERITY int,
  PRIMARY KEY (TICKET_ID),
  FOREIGN KEY (REPORTER_ID) REFERENCES USERS (ID),
  FOREIGN KEY (ASSIGNEE_ID) REFERENCES USERS (ID)

);

CREATE TABLE NOTE(
     NOTE_ID BIGINT AUTO_INCREMENT NOT NULL,
     TICKET_ID BIGINT NOT NULL,
     CREATOR_ID BIGINT,
     NAME VARCHAR(250),
     DESCRIPTION VARCHAR(4000),
     DATE_CREATED DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
     LAST_UPDATED DATE,
     PRIMARY KEY (NOTE_ID),
     FOREIGN KEY (TICKET_ID) REFERENCES TICKET (TICKET_ID),
     FOREIGN KEY (CREATOR_ID) REFERENCES USERS (ID)
);

CREATE TABLE PILE(
  ID BIGINT AUTO_INCREMENT NOT NULL,
  NAME VARCHAR(250),
  TICKET_ID BIGINT,
  PRIMARY KEY (ID)
);