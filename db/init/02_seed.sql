USE cereal_db;

LOAD DATA INFILE '/var/lib/mysql-files/data.csv'
INTO TABLE product
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 2 ROWS
(name,mfr,cereal_type,calories,protein,fat,sodium,fiber,carbo,sugars,potass,vitamins,shelf,weight,cups,rating);

INSERT INTO manufacturer (name) VALUES
('American Home Food Products'),
('General Mills'),
('Kelloggs'),
('Nabisco'),
('Post'),
('Quaker Oats'),
('Ralston Purina');

-- Password hashes generated using bcrypt
-- 1234
INSERT INTO users (username, password)
VALUES
  ('admin', '$2a$10$QxF4bW0X0m7Yt1pQX1yGxu4UO8Qm9C8b4WcQjv2nC2h7I6QxO1f1y'),
  ('alice', '$2a$10$eW5vQkNyS3lQbGFjZUludG8l4q3Zpo8GmIu1X9cWZyC9F4yM9y8nW.');
