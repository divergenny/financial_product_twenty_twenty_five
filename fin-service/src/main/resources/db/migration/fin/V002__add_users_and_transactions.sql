INSERT INTO employees (name, balance)
VALUES ('Vlad', 3000),
       ('Igor', 2000);

INSERT INTO transactions (employee_id, amount, timestamp)
VALUES (1, 5000, now() - interval '1 day'),
       (1, -200, now()),
       (2, 1000, now() - interval '1 day'),
       (2, -100, now());
