DROP DATABASE IF EXISTS BlogDatabaseTest;

CREATE DATABASE BlogDatabaseTest;

USE BlogDatabaseTest;


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
	password varchar(48),
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