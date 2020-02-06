# About
API to create, view and manage products

This project is a RESTful API application built on Java SpringBoot that allows users to perform operations on products and their categories. Following operations are supported as of now,

View all Products
Create/View/Update/Delete a Product
Create/View/Update/Delete a Product Category

# Schema
The schema consists of the following tables,

Product
Product_Category

```
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

-- sample data for testing

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
```
# Technologies
- Java 8
- SpringBoot 2.2+
- Swagger UI 2.7
- H2 database

# Build tools
- Gradle 4.10 (Gradle 4+ recommended)

# Building/Running the project
- Option 1: Using Gradle<br/>
  After cloning the project and installing Gradle, use a command line tool and navigate to the root directory of the project.
  Use the following command to run the application,<br/><br/>
  ``` gradle bootRun ```
  <br/><br/>
  Once the application is running successfully, navigate to Swagger UI - http://localhost:8080/swagger-ui.html#
  <br/>
  Swagger UI is an interactive API visualization tool.
  

- Option 2: Using Docker image<br/>
  Build a docker image of the application using the following command, <br/><br/>
  ``` gradle buildDocker ```
  <br><br/>
  Once the image is ready, you can run it in a container using the following command, <br/><br/>
  ``` docker run product-api ```
  <br><br/>
