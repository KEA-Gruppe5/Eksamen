# Project Management Tool

Link to deployed application: https://pmtool-fvesfthrbud6gqec.norwayeast-01.azurewebsites.net/

A web app designed for assistance in project management. 
Supported features:

- user registration and authentication;

- view, add, update, delete project;

- add a subproject to the project;

- view, add, update, delete tasks under the subproject. Update and delete only for PM;

- assign multiple users at once to the project;

- assign a user to the task (only for PM);

- view how much time the project and subproject take to accomplish;

- view how many hours needs to be worked per day before the project/subproject is done.

Used Technologies:
- Java 17

- Spring framework

- MySQL

- Maven

**To Run the application**

 1. Clone the repository by running git clone https://github.com/KEA-GruppeJVK/Eksamen.git
 2. Open the application in your desired IDE
 3. Setup a database environment (we used MySQL), and execute the script in
    `/resources/DDL.sql` and `/resources/DML.sql` to add test data
 4. Add envirmoent variables - if you use Intellij go to Run
    Configurations and add the folliwing under enviroment variables:
    
    `DEV_URL - Database URL`
    
    `DEV_USER - Database Username`
    
    `DEV_PASSWORD - Database Password`
    
    `PROFILE - add "test" to use h2 ddl or "dev" to use your own database` 

 6. Run your application from the IDE Navigate to http://localhost:8080 inside your browser.
