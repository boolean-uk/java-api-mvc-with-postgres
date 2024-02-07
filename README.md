Java API using JDBC and ElephantSQL. Run _flyway migrate_ -> set database url/name/password to the application.yml settings file -> run spring application 

# Java API Exercise

## Learning Objectives
- Use the MVC pattern with Spring Boot to communicate with a Postgres database
- Create an API which has a database behind it
- Use Database Migrations and Java classes to create and model the database

## Instructions

1. Fork this repository
2. Clone your fork to your machine
3. Open the project in IntelliJ

## Core Exercise

Look at the OpenAPI schema below, your task is to set up a remote Postgres Database on ElephantSQL and connect to it from your code, the code should use SpringBoot to respond to API requests so that it matches the schema.

The database table for Employees will be something like this:

| Field       | Type         | Information |
|-------------|--------------|-------------|
| id          | Serial       | PK          |
| name        | VARCHAR(250) |             |
| jobName     | VARCHAR(250) |             |
| salaryGrade | VARCHAR(10)  |             |
| department  | VARCHAR(50)  |             |

Create some dummy data to populate the table with and use flyway to add it to the table.

You should use Flyway, SpringBoot and the Postgres JDBC in the same way we did during the morning session to achieve this.

Add tests as usual to test the functionality of the Model layer, you do not need to test the repository code directly.

Here is the spec for the Core Criteria: [Core Spec](https://boolean-uk.github.io/java-api-mvc-with-postgres/)

## Extension Exercise

Add in the rest of the Schema based on the OpenAPI Spec linked below, modify the original Employee table to use Foreign Keys as appropriate based on the two new tables you will create, you can use Flyway to help run the migrations and populate the tables.

Add in models and other classes to enable API calls to be made which will match the endpoints specified in the schema.

Here is the spec for the Extension Criteria: [Extension Spec](https://boolean-uk.github.io/java-api-mvc-with-postgres/extensions.html)
