DROP DATABASE IF EXISTS BlogDatabase;

CREATE DATABASE BlogDatabase;

USE BlogDatabase;


CREATE TABLE role (
	roleId INT AUTO_INCREMENT,
	roleName varchar(12),
	CONSTRAINT pk_role
        PRIMARY KEY (roleId)
);

CREATE TABLE user (
	userId INT AUTO_INCREMENT,
	roleId INT,
	firstName varchar(26),
	lastName varchar(40),
	email varchar(126),
	password varchar(60),
	CONSTRAINT pk_user
        PRIMARY KEY (userId),
	CONSTRAINT fk_user_role
    	FOREIGN KEY (roleId)
    	REFERENCES role(roleId)
);

CREATE TABLE category (
    categoryId INT AUTO_INCREMENT,
    name varchar(26),
    description text,
    CONSTRAINT pk_category
        PRIMARY KEY (categoryId)
);

CREATE TABLE post (
	postId INT AUTO_INCREMENT,
    userId INT,
    categoryId INT,
	title varchar(48),
	published boolean,
	createdAt TIMESTAMP ,
	editedAt TIMESTAMP ,
	publishedAt TIMESTAMP ,
    expiryDate TIMESTAMP ,
    content text,
	CONSTRAINT pk_post 
        PRIMARY KEY (postId),
	CONSTRAINT fk_post_category
    	FOREIGN KEY (categoryId)
    	REFERENCES category(categoryId),
	CONSTRAINT fk_post_user
    	FOREIGN KEY (userId)
    	REFERENCES user(userId)
);

CREATE TABLE tag (
	tagId INT AUTO_INCREMENT,
	name varchar(256),
	CONSTRAINT pk_tag
        PRIMARY KEY (tagId)
);

CREATE TABLE staticPage (
	staticPageId INT AUTO_INCREMENT,
	userId int,
	title varchar(128),
	content text,
	CONSTRAINT pk_staticPage
        PRIMARY KEY (staticPageId),
	CONSTRAINT fk_staticPage_user
    	FOREIGN KEY (userId)
    	REFERENCES user(userId)
);

CREATE TABLE postTag (
    postId INT,
    tagId INT,
    CONSTRAINT pk_postTag
        PRIMARY KEY (postId, TagId),
	CONSTRAINT fk_postTag_post
    	FOREIGN KEY (postId)
    	REFERENCES post(postId),
	CONSTRAINT fk_postTag_tag
		FOREIGN KEY (tagId)
    	REFERENCES tag(tagId)
);

SHOW TABLES;

-- Add Roles:
INSERT INTO role(roleId, roleName)
VALUES (1, 'OWNER'),
	(2, 'ADMIN');

-- Add Sample Users
-- OWNER username: 'owner@sg.com', password: 'owner'
-- ADMIN username: 'admin@sg.com', password: 'admin'
INSERT INTO user(userId, roleId, firstName, lastName, email, password)
VALUES (1, 1, 'OwnerFirstName', 'OwnerLastName', 'owner@sg.com', '$2a$12$zPIAQ3ZBZAVryG1XPPWPku1oWYAFW9rDjydkqa/y3hp2Ai9FKuJIG'),
	(2, 2, 'AdminFirstName', 'AdminLastName', 'admin@sg.com', '$2a$12$48419Mzcmk/YSDiCckb5m.Bh6NxEOd0M9ydDyq6/RjnMHF6SPEINS');
	
-- Add Sample Categories:
INSERT INTO category(categoryId, name, description)
VALUES (1, 'Important', 'Important and high priority posts'),
	(2, 'Everyday', 'Normal everyday posts'),
	(3, 'Crazy timez', 'For all the crazy things!');


