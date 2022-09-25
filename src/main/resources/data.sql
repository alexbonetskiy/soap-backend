INSERT INTO USERS (NAME, LOGIN, PASSWORD)
 VALUES ('Peter', 'peter', 'Peter1'),
        ('Andrew', 'andrew', 'Andrew3');

INSERT INTO ROLES  (NAME)
 VALUES ('User'),
        ('Admin');


INSERT INTO USER_ROLES (LOGIN, ID)
VALUES ('user', 1),
       ('admin', 2);




