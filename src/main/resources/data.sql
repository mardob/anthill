INSERT INTO USERS (FIRST_NAME, LAST_NAME, USER_NAME, EMAIL, LAST_LOGGED_IN, LAST_UPDATED) VALUES ('John', 'Smith','jsmit', 'john.smith@gmail.com', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO USERS (FIRST_NAME, LAST_NAME, EMAIL) VALUES ('John', 'Cleese', 'john.Cleese@gmail.com');
INSERT INTO USERS (FIRST_NAME, LAST_NAME, EMAIL) VALUES ('Bernard', 'Black', 'bernard.black@gmail.com');
INSERT INTO USERS (FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGGED_IN) VALUES ('Fran', 'Black', 'fran.black@gmail.com', CURRENT_TIMESTAMP());
INSERT INTO USERS (FIRST_NAME, LAST_NAME, EMAIL, LAST_UPDATED) VALUES ('Manny', 'Black', 'manny.black@gmail.com', CURRENT_TIMESTAMP());

INSERT INTO TICKET (REPORTER_ID, ASSIGNEE_ID, NAME, DESCRIPTION, LAST_UPDATED) VALUES (1,1,'First test ticket', 'This program doesnt seem to work please fix', CURRENT_TIMESTAMP());
INSERT INTO TICKET (REPORTER_ID,NAME, DESCRIPTION, STATE) VALUES (2,'Second test ticket', 'This program doesnt seem to work please fix',3);
INSERT INTO TICKET (REPORTER_ID,NAME, DESCRIPTION, LAST_CHECKED) VALUES (4,'Third test ticket', 'This program doesnt seem to work please fix', CURRENT_TIMESTAMP());
INSERT INTO TICKET (REPORTER_ID,NAME, DESCRIPTION, LAST_CHECKED, LAST_UPDATED) VALUES (2,'Fourth test ticket', 'This program doesnt seem to work please fix', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO TICKET (REPORTER_ID,NAME, DESCRIPTION) VALUES (1,'Fifth test ticket', 'This program doesnt seem to work please fix');
INSERT INTO TICKET (REPORTER_ID,NAME, DESCRIPTION) VALUES (3,'Sixth test ticket', 'This program doesnt seem to work please fix');
INSERT INTO TICKET (REPORTER_ID,NAME, DESCRIPTION, LAST_UPDATED) VALUES (3,'Seventh test ticket', 'This program doesnt seem to work please fix', CURRENT_TIMESTAMP());
INSERT INTO TICKET (REPORTER_ID,NAME, DESCRIPTION) VALUES (1,'Eight test ticket', 'This program doesnt seem to work please fix');


INSERT INTO NOTE (TICKET_ID, CREATOR_ID, NAME, DESCRIPTION, LAST_UPDATED) VALUES (1,1,'Basic', 'I will deliver you requested documents later', CURRENT_TIMESTAMP());
INSERT INTO NOTE (TICKET_ID, CREATOR_ID,NAME, DESCRIPTION) VALUES (1,3,'Basic', 'I will deliver you requested documents later');


