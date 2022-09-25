INSERT INTO USERS (NAME, LOGIN, PASSWORD)
 VALUES ('Peter', 'Peter', 'Peter1'),
        ('Andrew', 'Andrew', 'Andrew3');

INSERT INTO ROLES  (NAME)
 VALUES ('Operator'),
        ('Admin'),
        ('Engineer'),
        ('Analytic');


INSERT INTO USER_ROLES (LOGIN, ID)
VALUES ('Peter', 1),
       ('Andrew', 2),
       ('Andrew', 3);




