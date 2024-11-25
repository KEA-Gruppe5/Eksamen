INSERT INTO role (role)
VALUES ('PM'),
       ('Employee'),
       ('Client');

INSERT INTO user (firstname, lastname, email, password, role_id)
VALUES ('Alice', 'Smith', 'alice.smith@example.com', 'password123', 1),
       ('Bob', 'Johnson', 'bob.johnson@example.com', 'password123', 2),
       ('Charlie', 'Brown', 'charlie.brown@example.com', 'password123', 3);

INSERT INTO project (title, start_date, end_date, duration)
VALUES ('Project Alpha', '2024-01-01', '2024-03-01', 60),
       ('Project Beta', '2024-02-01', '2024-04-01', 60),
       ('Project Gamma', '2024-03-01', '2024-05-01', 60);

INSERT INTO subproject (parent_project_id, subproject_id)
VALUES (1, 2);

INSERT INTO user_project (parent_project_id, subproject_id)
VALUES (1, NULL),
       (2, NULL),
       (3, NULL);

INSERT INTO task (title, start_date, end_date, duration, user_id)
VALUES ('Task 1 for Alpha', '2024-01-01', '2024-01-15', 14, 1),
       ('Task 2 for Beta', '2024-02-01', '2024-02-15', 14, 2),
       ('Task 3 for Gamma', '2024-03-01', '2024-03-15', 14, 3);
