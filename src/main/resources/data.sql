INSERT INTO USERS (NAME, LOGIN, PASSWORD)
 VALUES ('User', 'user', 'user'),
        ('Admin', 'admin', 'admin');

INSERT INTO ROLES (ID, NAME)
 VALUES (1, 'User'),
        (2, 'Admin');


INSERT INTO USER_ROLES (LOGIN, ID)
VALUES ('user', 1),
       ('admin', 2);




