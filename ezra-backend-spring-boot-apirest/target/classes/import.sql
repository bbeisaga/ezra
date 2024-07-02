/* Populate tabla clientes */

INSERT INTO regiones (id, nombre) VALUES (1, 'Sudamérica');
INSERT INTO regiones (id, nombre) VALUES (2, 'Centroamérica');
INSERT INTO regiones (id, nombre) VALUES (3, 'Norteamérica');
INSERT INTO regiones (id, nombre) VALUES (4, 'Europa');
INSERT INTO regiones (id, nombre) VALUES (5, 'Asia');
INSERT INTO regiones (id, nombre) VALUES (6, 'Africa');
INSERT INTO regiones (id, nombre) VALUES (7, 'Oceanía');
INSERT INTO regiones (id, nombre) VALUES (8, 'Antártida');

INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(1, 'Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(2, 'Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(4, 'Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(4, 'Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(4, 'Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(5, 'Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(6, 'Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(7, 'Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06');

/* Creamos algunos usuarios con sus roles */
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('andres','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1, 'Andres', 'Guzman','profesor@bolsadeideas.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('admin','$2a$10$RmdEsvEfhI7Rcm9f/uZXPebZVCcPC7ZXZwV51efAvMAp1rIaRAfPK',1, 'John', 'Doe','jhon.doe@bolsadeideas.com');

INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 1);

/* Populate tabla productos */
INSERT INTO productos (nombre, precio, create_at) VALUES('Letrero luminoso', 259990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Letrero en strucutura', 123490, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Letrero en parante', 1499990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Letrero en Roll Screen', 37990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sellos', 69990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Fotocheck', 69990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Tarjeta matrimonio', 299990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Tarjeta bautizo', 299990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Tarjeta personales', 299990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Tarjeta varios', 299990, NOW());

/* Creamos algunas pedidos */
INSERT INTO pedidos (descripcion, observacion, cliente_id, create_at, acuenta) VALUES('Pedido varios', null, 1, NOW(), 30);

INSERT INTO pedidos_items (cantidad, pedido_id, producto_id) VALUES(1, 1, 1);
INSERT INTO pedidos_items (cantidad, pedido_id, producto_id) VALUES(2, 1, 4);
INSERT INTO pedidos_items (cantidad, pedido_id, producto_id) VALUES(1, 1, 5);
INSERT INTO pedidos_items (cantidad, pedido_id, producto_id) VALUES(1, 1, 7);

INSERT INTO pedidos (descripcion, observacion, cliente_id, create_at, acuenta) VALUES('Pedido Tarjeta matrimonio', 'Alguna nota importante!', 1, NOW(), 0);
INSERT INTO pedidos_items (cantidad, pedido_id, producto_id) VALUES(3, 2, 6);