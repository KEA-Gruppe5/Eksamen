CREATE SCHEMA IF NOT EXISTS PMTool;
SET SCHEMA PMTool;

DROP TABLE IF EXISTS users_projects;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS subprojects;
DROP TABLE IF EXISTS projects;

CREATE TABLE projects
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(255),
    start_date DATE,
    end_date   DATE,
    duration   INT
);

CREATE TABLE subprojects
(
    parent_project_id INT,
    subproject_id     INT,
    FOREIGN KEY (parent_project_id) REFERENCES projects (id) ON DELETE CASCADE,
    FOREIGN KEY (subproject_id) REFERENCES projects (id) ON DELETE CASCADE,
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
    role_id   INT,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE TABLE tasks
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    project_id INT,
    sub_project_id INT,
    title      VARCHAR(255),
    description VARCHAR(255),
    priority   ENUM('Low', 'Medium', 'High'),
    user_id    INT,
    assigned_user_id INT NULL,
    estimated_hours INT,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE,
    FOREIGN KEY (sub_project_id) REFERENCES projects (id) ON DELETE CASCADE
);

CREATE TABLE users_projects
(
    user_id    INT NOT NULL,
    project_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, project_id)
);
