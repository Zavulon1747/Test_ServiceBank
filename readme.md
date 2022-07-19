# ServiceBank API
- Spring Boot 
- Maven
- Liquibase
- RabbitMQ
- Lombok

##Includes the following features:

- **CRUD** operations for: *customers, products, rules.*


- Creating a new product through the **RabbitMQ** queue that will add it in the database easily :)


- Auto-creating tables for the customers, products, rules using **Liquibase** [(_I used .yaml files_)](src/main/resources/db/changelog)


