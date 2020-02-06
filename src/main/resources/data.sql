DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS product_category;

CREATE TABLE product_category (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL
);

CREATE TABLE product (
  id INT AUTO_INCREMENT PRIMARY KEY,
  category_id INT NOT NULL,
  name VARCHAR(250) NOT NULL,
  quantity INT UNSIGNED NOT NULL,

  FOREIGN KEY (category_id)
  REFERENCES product_category(id)
    ON UPDATE CASCADE
);

INSERT INTO product_category (name) VALUES
('Electronics'),
('Home'),
('Clothing');

INSERT INTO product (category_id, name, quantity) VALUES
  ('1', 'Computers', '1000'),
  ('1', 'Phones', '1500'),
  ('1', 'TV', '500'),
  ('2', 'Houseware', '400'),
  ('2', 'Tools', '200'),
  ('2', 'Furniture', '100'),
  ('3', 'Shirts', '2000'),
  ('3', 'Pants', '3000'),
  ('3', 'Shoes', '3500');

-- add createdAt, updatedAt, deletedAt attributes