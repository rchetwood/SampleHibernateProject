DROP DATABASE IF EXISTS test_library;
CREATE DATABASE test_library;
USE test_library; 
DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK
(
 title VARCHAR(50),
 author VARCHAR(30),
 copies INT,
 primary key (title)

);

DROP TABLE IF EXISTS USER;
CREATE TABLE USER
(uID INT AUTO_INCREMENT,
 uNAME VARCHAR(30),
 age INT,
 loaned INT,
 updatedOn timestamp not null on update current_timestamp,
 PRIMARY KEY (uID)
) ;
ALTER table USER AUTO_INCREMENT = 1001;

CREATE TABLE LOAN
(uID INT,
 title VARCHAR(50),
 loanDate DATE,
 overdue BOOLEAN DEFAULT FALSE,
 PRIMARY KEY(uID,title,loanDate),
 FOREIGN KEY(uid) REFERENCES user(uid) on delete cascade, 
 FOREIGN KEY(title) REFERENCES Book(title) on delete cascade 
 #FOREIGN KEY(uid) REFERENCES user(uid) on delete set NULL, 
 #FOREIGN KEY(title) REFERENCES Book(title) on delete set NULL 
); 


LOAD DATA LOCAL INFILE 'C:\\Users\\riley\\OneDrive\\Desktop\\HibernateTest\\springHibernateSQLEx\\src\\main\\resources\\Library\\books.txt' INTO TABLE BOOK;
LOAD DATA LOCAL INFILE 'C:\\Users\\riley\\OneDrive\\Desktop\\HibernateTest\\springHibernateSQLEx\\src\\main\\resources\\Library\\users.txt' INTO TABLE USER;
LOAD DATA LOCAL INFILE 'C:\\Users\\riley\\OneDrive\\Desktop\\HibernateTest\\springHibernateSQLEx\\src\\main\\resources\\Library\\loans.txt' INTO TABLE LOAN;
