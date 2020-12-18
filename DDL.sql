DROP DATABASE IF EXISTS bullsAndCowsDB;
CREATE DATABASE bullsAndCowsDB;

USE bullsAndCowsDB;

CREATE TABLE Game (
	id INT PRIMARY KEY AUTO_INCREMENT,
    finished BOOL DEFAULT false,
    answer CHAR(4) NOT NULL
);

CREATE TABLE Round (
	id INT PRIMARY KEY AUTO_INCREMENT,
    gameId INT,
    FOREIGN KEY fk_Game (gameId)
		REFERENCES Game(id),
	
    guess CHAR(4) NOT NULL,
    result CHAR(7) NOT NULL,
    `time` DATETIME NOT NULL DEFAULT  NOW()
);