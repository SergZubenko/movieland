CREATE TABLE `movieland`.`genres` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  PRIMARY KEY (`id`));



CREATE TABLE `movieland`.`movies` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `movie_name` VARCHAR(250) NULL,
  `movie_name_origin` VARCHAR(250) NULL,
  `yearOfRelease` INT NULL,
  `genre_id` INT NULL,
  `description` VARCHAR(3000) NULL,
  `rating` DECIMAL(5,2) NULL,
  `price` DECIMAL(15,2) NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `movieland`.`posters` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `movie_id` INT NULL,
  `picturePath` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`));



CREATE TABLE `movieland`.`reviews` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NULL,
  `review` VARCHAR(3000) NULL,
  PRIMARY KEY (`id`));



CREATE TABLE `movieland`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(250) NULL,
  `email` VARCHAR(100) NULL,
  `password` VARCHAR(150) NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `movieland`.`movies_genres` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `movie_id` INT NULL,
  `genre_id` INT NULL,
  PRIMARY KEY (`id`));
