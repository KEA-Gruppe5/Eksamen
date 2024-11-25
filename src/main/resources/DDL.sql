CREATE
DATABASE IF NOT EXISTS PMTool;
USE
PMTool;

CREATE TABLE project
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    title      varchar(255),
    start_date DATE,
    end_date   date,
    duration   int
);

CREATE TABLE subproject
(
    parent_project_id int,
    subproject_id     int,
    FOREIGN KEY (parent_project_id) REFERENCES project (id),
    FOREIGN KEY (subproject_id) REFERENCES project (id),
    PRIMARY KEY (parent_project_id, subproject_id)
);


CREATE TABLE role
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(255)
);


CREATE TABLE user
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255),
    lastname  VARCHAR(255),
    email     VARCHAR(255) UNIQUE,
    password  VARCHAR(255),
    role_id   int,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);

CREATE TABLE task
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    title      VARCHAR(255),
    start_date DATE,
    end_date   DATE,
    duration   INT,
    user_id    INT,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE table user_project
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT NOT NULL,
    project_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (project_id) REFERENCES project (id),
    primary key (user_id, project_id),
);
