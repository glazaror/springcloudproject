-- Ahora guardamos las contrasenas encriptadas con bcrypt
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('admin', '$2a$10$siOW9zhlaV6OfUFJp2inuOzUmyFEgReoMgMAPRqFKPFvEaVnTq4xG', 1, 'Dafne', 'Lazaro', 'dlazarob18@gmail.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('gerardo', '$2a$10$2GHgy4Zq8MrTLAE9XQJI9.iZkkGIsLs6cJA39AQ22nZylTeMsgfBi', 1, 'Gerardo', 'Lazaro', 'glazaror85@gmail.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('guicela', '$2a$10$lTQq8qFwAuGHKKTYYzjypux9kEox/dfq/k6ts8rHvoqR0amuQUeR6', 1, 'Guicela', 'Becerra', 'gbecerra@gmail.com');

-- Por estandar de spring security los nombre de los roles deben tener el prefijo "ROLE_"
INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (3, 1);