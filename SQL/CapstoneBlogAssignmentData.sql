USE BlogDatabase;

/* INSERT INTO role
VALUES 
(1, 'Owner');*/

INSERT INTO category
VALUES 
(4, 'Animals', 'Everything about animals');

/*INSERT INTO user
VALUES 
(2, 1, 'John', 'Smith', 'koolkidz@gmail.com', 'password');*/

INSERT INTO post
VALUES 
(2,1, 4, 'The dog', 1, '2018-01-19 12:14:07', '2018-01-19  12:14:07', '2018-01-19  12:14:07', '2024-01-19 12:14:07', 'Once a cow jumped over the moon'),
(3,1, 4, 'The cat', 1, '2018-01-19  12:14:07', '2018-01-19  12:14:07', '2018-01-19  12:14:07', '2024-01-19 12:14:07', 'Once a cat ate a mouse');


INSERT INTO tag
VALUES 
(1, 'Fun'),
(2, 'Interesting'),
(3, 'Lol'),
(4, 'INeveKnow');

INSERT INTO posttag
VALUES 
(2, 2),
(2, 3),
(3, 1),
(3, 2),
(3, 3),
(3, 4);

INSERT INTO staticPage
VALUES 
(1, '1', 'Disclaimer', 'totally not my fault i didnt do anything i swear'),
(2, '1', 'Terms and Condition', 'Read at your own risk it be spicy');