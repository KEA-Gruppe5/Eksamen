CREATE SCHEMA IF NOT EXISTS PMTool;
SET SCHEMA PMTool;

CREATE TABLE projects
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    title      varchar(255),
    start_date DATE,
    end_date   date,
    duration   int
);

CREATE TABLE subprojects
(
    parent_project_id int,
    subproject_id     int,
    FOREIGN KEY (parent_project_id) REFERENCES projects(id),
    FOREIGN KEY (subproject_id) REFERENCES projects(id),
    PRIMARY KEY (parent_project_id, subproject_id)
);


CREATE TABLE roles
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(255)
);


CREATE TABLE users
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255),
    lastname  VARCHAR(255),
    email     VARCHAR(255) UNIQUE,
    password  VARCHAR(255),
    role_id   int,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE TABLE tasks
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    title      VARCHAR(255),
    start_date DATE,
    end_date   DATE,
    duration   INT,
    user_id    INT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE table users_projects
(
    user_id INT,
    project_id INT,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (project_id) REFERENCES projects (id),
    PRIMARY KEY (user_id, project_id)
);
