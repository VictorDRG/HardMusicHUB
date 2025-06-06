-- Insertar roles
INSERT INTO roles(id, name) VALUES(1, 'ROLE_USER') ON CONFLICT (id) DO NOTHING;
INSERT INTO roles(id, name) VALUES(2, 'ROLE_ADMIN') ON CONFLICT (id) DO NOTHING;

-- Insertar usuarios (asegúrate de que las contraseñas son válidas y hasheadas)
-- Contraseña 'admin1234' para admin (asegúrate de que este hash es el tuyo)
INSERT INTO users (id, name, email, password, username) VALUES (1, 'Admin User', 'admin@example.com', '$2a$10$TU_HASH_DE_ADMIN_AQUI', 'admin') ON CONFLICT (id) DO NOTHING;
-- Contraseña 'user1234' para user (asegúrate de que este hash es el tuyo)
INSERT INTO users (id, name, email, password, username) VALUES (2, 'Regular User', 'user@example.com', '$2a$10$TU_HASH_DE_USER_AQUI', 'user') ON CONFLICT (id) DO NOTHING;

-- Asignar roles a los usuarios
-- Si la tabla user_roles es generada por Hibernate, puede que no necesites inserciones aquí
-- a menos que Hibernate no maneje las relaciones ManyToMany en data.sql automáticamente.
-- Si tu sistema de user_roles es explícito y no se genera automáticamente, necesitas estas:
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2) ON CONFLICT (user_id, role_id) DO NOTHING; -- Admin tiene ROLE_ADMIN
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1) ON CONFLICT (user_id, role_id) DO NOTHING; -- Admin también tiene ROLE_USER
INSERT INTO user_roles (user_id, role_id) VALUES (2, 1) ON CONFLICT (user_id, role_id) DO NOTHING; -- User tiene ROLE_USER

-- IMPORTANTE: Reiniciar las secuencias de ID después de inserciones manuales (si no las tienes)
-- Asume que tus tablas son 'users' y 'roles' y sus secuencias son 'users_id_seq' y 'roles_id_seq'
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users), TRUE);
SELECT setval('roles_id_seq', (SELECT MAX(id) FROM roles), TRUE);