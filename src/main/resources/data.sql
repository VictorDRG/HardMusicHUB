
INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN') ON CONFLICT (name) DO NOTHING;
INSERT INTO roles (id, name) VALUES (2, 'ROLE_USER') ON CONFLICT (name) DO NOTHING;


INSERT INTO users (id, name, email, password, username) VALUES (1, 'Admin User', 'admin@example.com', '$2a$10$Z4DXxi0CE8z.veoboU3FmuWMvUTK.F0mNx4L2ZcA5fAkt8UW8bs62', 'admin') ON CONFLICT (username) DO NOTHING;
INSERT INTO users (id, name, email, password, username) VALUES (2, 'Regular User', 'user@example.com', '$2a$10$eaOStFf0uFjVfjhJiazsB.0qmN/jyJSnrAX4Ufyg4/SE3VJtqnWDe', 'user') ON CONFLICT (username) DO NOTHING;


-- admin tiene ROLE_ADMIN y ROLE_USER
INSERT INTO user_roles (user_id, role_id) VALUES (1, (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')) ON CONFLICT DO NOTHING;
INSERT INTO user_roles (user_id, role_id) VALUES (1, (SELECT id FROM roles WHERE name = 'ROLE_USER')) ON CONFLICT DO NOTHING;
-- user tiene ROLE_USER
INSERT INTO user_roles (user_id, role_id) VALUES (2, (SELECT id FROM roles WHERE name = 'ROLE_USER')) ON CONFLICT DO NOTHING;