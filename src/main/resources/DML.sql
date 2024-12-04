INSERT INTO roles (role)
VALUES ('PM'),
       ('Employee'),
       ('Client');

INSERT INTO users (firstname, lastname, email, password, role_id)
VALUES ('Alice', 'Smith', 'alice.smith@example.com', 'password123', 1),
       ('Bob', 'Johnson', 'bob.johnson@example.com', 'password123', 2),
       ('Alex', 'Peterson', 'alex.peterson@example.com', 'password123', null),
       ('Charlie', 'Brown', 'charlie.brown@example.com', 'password123', 3);

INSERT INTO projects (title, start_date, end_date, duration, archived)
VALUES ('Project Alpha', '2024-01-01', '2024-03-01', 60, FALSE),
       ('Project Beta', '2024-02-01', '2024-04-01', 60, FALSE),
       ('Project Gamma', '2024-03-01', '2024-05-01', 60, FALSE);

INSERT INTO subprojects (parent_project_id, subproject_id)
VALUES (1, 2);

INSERT INTO users_projects (user_id, project_id)
VALUES (1, 1),
       (2, 2),
       (3, 1),
       (3, 3);

INSERT INTO tasks (project_id, title, description, priority, status, deadline, assigned_user_id, estimated_hours, archived)
VALUES
    (2, 'Task 1 for Beta', 'Task description for Alpha 1', 3, 1, '2024-04-01', 1, 2.0, false),
    (2, 'Task 1 for Beta', 'Task description for Alpha 1', 3, 1, '2024-04-01', 2, 8.0, false),
    (2, 'Task 1 for Beta', 'Task description for Alpha 1', 3, 1, '2024-04-01', 3, 8.0, false);
