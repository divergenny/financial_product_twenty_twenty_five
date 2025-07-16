INSERT INTO roles (name, created, updated)
    VALUES ('USER', now(), now()),
           ('ADMIN', now(), now())
ON CONFLICT (name) DO NOTHING;


INSERT INTO users (username, first_name, last_name, email, password)
VALUES (
    'admin',
    'Иван',
    'Иванов',
    'admin@gmail.ru',
    '$2a$12$j4OaI9w5NIUGKLynXyPuf.4LEAzW62x0/fqYvt3kJS/GY06EXFCOS'
) ON CONFLICT (username) DO NOTHING;


-- Связывание пользователя с ролями
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
    JOIN roles r ON r.name IN ('USER', 'ADMIN')
WHERE u.username = 'admin'
    ON CONFLICT DO NOTHING;
