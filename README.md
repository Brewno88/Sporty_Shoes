# SportyShoes Application

## Overview

The SportyShoes application is a comprehensive web-based platform designed to facilitate the management and sale of sports shoes. It offers functionalities for both customers and administrators, including product browsing, inventory management, and order processing.

## Features

- **User Authentication**: Secure login and registration for users.
- **Product Management**: Add, update, and delete products from the inventory.
- **Order Management**: Process and track customer orders.
- **User Management**: Manage user roles and permissions.
- **Reporting**: Generate sales and inventory reports.

## Technologies Used

- **Backend**: Java, Spring Boot
- **Frontend**: HTML, CSS, JavaScript
- **Database**: MySQL
- **Build Tool**: Maven

## Project Structure

- **src/main/java**: Contains the Java source files.
  - **controller**: Handles HTTP requests and responses.
  - **service**: Contains business logic.
  - **repository**: Interfaces for database operations.
  - **model**: Entity classes representing the database tables.
  - **dto**: Data Transfer Objects for request and response payloads.
  - **config**: Configuration classes.
- **src/main/resources**: Contains configuration files and static resources.
  - **templates**: HTML templates for the frontend.
  - **static**: CSS, JavaScript, and image files.
- **src/test/java**: Contains test cases for the application.

## Setup and Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Brewno88/sporty-shoes.git
   ```
2. Navigate to the project directory:
   ```bash
   cd sporty-shoes
   ```
3. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/sporty_shoes
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```
4. Build the project using Maven:
   ```bash
   mvn clean install
   ```
5. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Usage

- Access the application at `http://localhost:8090`.
- Register as a new user or log in with existing credentials.
- Browse products, add them to the cart, and place orders.
- Admin users can manage products, orders, and users through the admin panel.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License.
