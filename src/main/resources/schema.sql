DROP TABLE IF EXISTS site;

CREATE TABLE site (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  url VARCHAR(250) DEFAULT NULL,
  enabled BOOLEAN DEFAULT true,
  scrap_thumb_enabled BOOLEAN DEFAULT true
);

DROP TABLE IF EXISTS article;

CREATE TABLE article (
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

CREATE TABLE label (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(250) NOT NULL
);

CREATE TABLE article_label (
  article_id INT NOT NULL,
  label_id INT NOT NULL,
  FOREIGN KEY (article_id) REFERENCES article(id),
  FOREIGN KEY (label_id) REFERENCES label(id),
  PRIMARY KEY (article_id, label_id)
);

CREATE TABLE scrap_rule (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  headline VARCHAR(500) NOT NULL,
  title VARCHAR(500) NOT NULL,
  link VARCHAR(500) NOT NULL,
  image VARCHAR(500) NOT NULL
);

DROP TABLE IF EXISTS category;

CREATE TABLE category (
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