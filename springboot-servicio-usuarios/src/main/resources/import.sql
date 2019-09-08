INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('admin', '12345', 1, 'Dafne', 'Lazaro', 'dlazarob18@gmail.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('gerardo', '12345', 1, 'Gerardo', 'Lazaro', 'glazaror85@gmail.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('guicela', '12345', 1, 'Guicela', 'Becerra', 'gbecerra@gmail.com');

-- Por estandar de spring security los nombre de los roles deben tener el prefijo "ROLE_"
INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (3, 1);