create table notice_url(
   id INT NOT NULL AUTO_INCREMENT,
   n_url VARCHAR(500) NOT NULL,
   PRIMARY KEY ( id )
);

create table notice_html(
   id INT NOT NULL AUTO_INCREMENT,
   n_url VARCHAR(500) NOT NULL,
   n_html VARCHAR(20000) NOT NULL,
   PRIMARY KEY ( id )
);

create table notice_article(
   id INT NOT NULL AUTO_INCREMENT,
   n_url VARCHAR(500) NOT NULL,
   n_title VARCHAR(500) NOT NULL,
   n_article VARCHAR(20000) NOT NULL,
   PRIMARY KEY ( id )
);