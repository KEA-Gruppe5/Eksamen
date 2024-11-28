INSERT INTO roles (role)
VALUES ('PM'),
       ('Employee'),
       ('Client');

INSERT INTO users (firstname, lastname, email, password, role_id)
VALUES ('Alice', 'Smith', 'alice.smith@example.com', 'password123', 1),
       ('Bob', 'Johnson', 'bob.johnson@example.com', 'password123', 2),
       ('Alex', 'Peterson', 'alex.peterson@example.com', 'password123', null),
       ('Charlie', 'Brown', 'charlie.brown@example.com', 'password123', 3);

INSERT INTO projects (title, start_date, end_date, duration)
VALUES ('Project Alpha', '2024-01-01', '2024-03-01', 60),
       ('Project Beta', '2024-02-01', '2024-04-01', 60),
       ('Project Gamma', '2024-03-01', '2024-05-01', 60);

INSERT INTO subprojects (parent_project_id, subproject_id)
VALUES (1, 2);

INSERT INTO users_projects (user_id, project_id)
VALUES (1, 1),
       (2, 2),
       (3, 1),
       (3, 3);

INSERT INTO tasks (project_id, sub_project_id, title, description, priority, start_date, end_date, duration, user_id)
VALUES
(1, NULL, 'Task 1 for Alpha', 'Task description for Alpha 1', 'High', '2024-01-01', '2024-01-15', 14, 1),
(1, NULL, 'Task 2 for Alpha', 'Task description for Alpha 2', 'Medium', '2024-01-01', '2024-01-15', 14, 1),
(1, NULL, 'Task 3 for Alpha', 'Task description for Alpha 3', 'Low', '2024-01-01', '2024-01-15', 14, 1);
