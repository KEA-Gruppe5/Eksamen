CREATE
DATABASE IF NOT EXISTS PMTool;
USE
PMTool;

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
    role_id   int,
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
    start_date DATE,
    end_date   DATE,
    duration   INT,
    user_id    INT,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE,
    FOREIGN KEY (sub_project_id) REFERENCES projects (id) ON DELETE CASCADE
);

CREATE table users_projects
(
    user_id    INT NOT NULL,
    project_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE,
    primary key (user_id, project_id)
);
