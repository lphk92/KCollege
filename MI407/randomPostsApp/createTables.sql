CREATE DATABASE inspiration;

CREATE TABLE inspiration.users
(
	id int NOT NULL AUTO_INCREMENT,
	username varchar(30) NOT NULL,
	password varchar(30) NOT NULL,
	email varchar(255) NOT NULL,
	name varchar(255),
	PRIMARY KEY (id)
);

CREATE TABLE inspiration.posts
(
	id int NOT NULL AUTO_INCREMENT,
	userid int NOT NULL,
	title varchar(255) NOT NULL,
	content blob,
	PRIMARY KEY (id),
	FOREIGN KEY (userid) REFERENCES users(id)
);

CREATE TABLE inspiration.favorites
(
	userid int NOT NULL,
	postid int NOT NULL,
	FOREIGN KEY (userid) REFERENCES users(id),
	FOREIGN KEY (postid) REFERENCES posts(id)
);
