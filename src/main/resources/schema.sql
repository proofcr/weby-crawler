DROP TABLE IF EXISTS SITE;
CREATE TABLE SITE (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  url VARCHAR(250) DEFAULT NULL,
  enabled BOOLEAN DEFAULT true,
  scrap_thumb_enabled BOOLEAN DEFAULT true
);

DROP TABLE IF EXISTS ARTICLE;
CREATE TABLE ARTICLE (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  description VARCHAR(250) NULL,
  url VARCHAR(250) NOT NULL,
  thumb_url VARCHAR(250) DEFAULT NULL,
  thumb MEDIUMBLOB NULL,
  body TEXT DEFAULT NULL,
  raw_body TEXT DEFAULT NULL,
  scrap_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  site_id INT,
  FOREIGN KEY (site_id) REFERENCES site(id)
);

DROP TABLE IF EXISTS IMAGE;
CREATE TABLE IMAGE (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(250) NULL,
  url VARCHAR(250) NOT NULL,
  thumb MEDIUMBLOB NULL,
  image MEDIUMBLOB NULL,
  scrap_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  article_id INT,
  FOREIGN KEY (article_id) REFERENCES article(id)
);

DROP TABLE IF EXISTS LABEL;
CREATE TABLE LABEL (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS ARTICLE_LABEL;
CREATE TABLE ARTICLE_LABEL (
  article_id INT NOT NULL,
  label_id INT NOT NULL,
  FOREIGN KEY (article_id) REFERENCES article(id),
  FOREIGN KEY (label_id) REFERENCES label(id),
  PRIMARY KEY (article_id, label_id)
);

DROP TABLE IF EXISTS SCRAP_RULE;
CREATE TABLE SCRAP_RULE (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  headline VARCHAR(500) NOT NULL,
  title VARCHAR(500) NOT NULL,
  link VARCHAR(500) NOT NULL,
  image VARCHAR(500) NOT NULL
);

DROP TABLE IF EXISTS CATEGORY;
CREATE TABLE CATEGORY (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  url VARCHAR(250) DEFAULT NULL,
  enabled BOOLEAN DEFAULT true,
  site_id INT,
  label_id INT,
  scrap_rule_id INT,
  FOREIGN KEY (site_id) REFERENCES site(id),
  FOREIGN KEY (label_id) REFERENCES label(id),
  FOREIGN KEY (scrap_rule_id) REFERENCES scrap_rule(id)
);