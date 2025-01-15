/* Populate tabla clientes */

INSERT INTO tipo_documentos (acronimo, nombre) VALUES ('DNI', 'Documento Nacional de Identificación');
INSERT INTO tipo_documentos (acronimo, nombre) VALUES ('CEX', 'Carnet de Extranjeria');


INSERT INTO clientes (tipo_documentos_id, nombre, apellido, email, create_at,numero_documento) VALUES(1, 'Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01','41953480');
INSERT INTO clientes (tipo_documentos_id, nombre, apellido, email, create_at,numero_documento) VALUES(1, 'Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02','11953480');
INSERT INTO clientes (tipo_documentos_id, nombre, apellido, email, create_at,numero_documento) VALUES(1, 'Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03','02953480');
INSERT INTO clientes (tipo_documentos_id, nombre, apellido, email, create_at,numero_documento) VALUES(1, 'Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04','71953480');
INSERT INTO clientes (tipo_documentos_id, nombre, apellido, email, create_at,numero_documento) VALUES(2, 'Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01','41955680');
INSERT INTO clientes (tipo_documentos_id, nombre, apellido, email, create_at,numero_documento) VALUES(2, 'Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10','40053480');
INSERT INTO clientes (tipo_documentos_id, nombre, apellido, email, create_at,numero_documento) VALUES(1, 'Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18','41953120');
INSERT INTO clientes (tipo_documentos_id, nombre, apellido, email, create_at,numero_documento) VALUES(1, 'John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28','21953480');
INSERT INTO clientes (tipo_documentos_id, nombre, apellido, email, create_at,numero_documento) VALUES(2, 'Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03','47653480');
INSERT INTO clientes (tipo_documentos_id, nombre, apellido, email, create_at,numero_documento) VALUES(2, 'Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04','01953480');
INSERT INTO clientes (tipo_documentos_id, nombre, apellido, email, create_at,numero_documento) VALUES(1, 'Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05','41353480');
INSERT INTO clientes (tipo_documentos_id, nombre, apellido, email, create_at,numero_documento) VALUES(2, 'Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06','41893480');

/* Creamos algunos usuarios con sus roles */
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('andres','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1, 'Andres', 'Guzman','profesor@bolsadeideas.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('admin','$2a$10$RmdEsvEfhI7Rcm9f/uZXPebZVCcPC7ZXZwV51efAvMAp1rIaRAfPK',1, 'John', 'Doe','jhon.doe@bolsadeideas.com');

INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 1);

/* creamos las cajas*/

INSERT INTO cajas (nombre, ubicacion) VALUES ('Caja1', 'Francisco pizarro 214 f');
INSERT INTO cajas (nombre, ubicacion) VALUES ('Caja2', 'Jeruslaen 108');
INSERT INTO cajas (nombre, ubicacion) VALUES ('Caja3', 'Mercaderes 129');

/* creamos tipo pago*/

INSERT INTO tipo_pagos (nombre, moneda, cc, cci) VALUES ('YAPE', 'soles',  '','');
INSERT INTO tipo_pagos (nombre, moneda, cc, cci) VALUES ('PLIN', 'soles',  '','');
INSERT INTO tipo_pagos (nombre, moneda, cc, cci) VALUES ('BCP Cuenta', 'soles','11122333','44556677');
INSERT INTO tipo_pagos (nombre, moneda, cc, cci) VALUES ('INTERBANK Cuenta', 'soles','14522333','45556677');
INSERT INTO tipo_pagos (nombre, moneda, cc, cci) VALUES ('BBVA Cuenta', 'soles','14522345','4555667745');


/* creamos tipo movimiento*/
INSERT INTO tipo_movimientos (movimiento, tipo) VALUES ('Venta','I');
INSERT INTO tipo_movimientos (movimiento, tipo) VALUES ('Sobrante','I');
INSERT INTO tipo_movimientos (movimiento, tipo) VALUES ('Saldo','I');
INSERT INTO tipo_movimientos (movimiento, tipo) VALUES ('Otros ingresos','I');
INSERT INTO tipo_movimientos (movimiento, tipo) VALUES ('Alimento','E');
INSERT INTO tipo_movimientos (movimiento, tipo) VALUES ('Taxis','E');
INSERT INTO tipo_movimientos (movimiento, tipo) VALUES ('Regalos','E');
INSERT INTO tipo_movimientos (movimiento, tipo) VALUES ('Otros gastos','E');

/* Populate tabla productos */
INSERT INTO productos (nombre, precio, create_at) VALUES('Letrero luminoso', 10, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Letrero en strucutura', 15, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Letrero en parante', 20, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Letrero en Roll Screen', 10, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sellos', 12, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Fotocheck', 5, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Tarjeta matrimonio', 2, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Tarjeta bautizo', 1, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Tarjeta personales', 5, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Tarjeta varios', 8, NOW());

/* Creamos estado de pedidos */
INSERT INTO estado_pedido (estado, descripcion) VALUES ('Registrado', 'No venció, no aceptado');
INSERT INTO estado_pedido (estado, descripcion) VALUES ('Vencido', 'Venció, no aceptado'); /*este campo se implementará con Schduled en segunda version*/
INSERT INTO estado_pedido (estado, descripcion) VALUES ('Devuelto', 'Devuelto por errores');
INSERT INTO estado_pedido (estado, descripcion) VALUES ('Entregado', 'Pagado, entregado al cliente');

 /*Creamos algunos pedidos */
/*INSERT INTO pedidos (observacion, cliente_id, estado_pedido_id, create_at, entregado_en,vencido, aceptado, pagado, pago, total, saldo) VALUES('Envío delivery el dia miercoles en la tarde, contactar al señor ramirez', 1, 1, NOW(),'2024-11-16',false,false,false, 30,64,14);*/

/*INSERT INTO pedidos_items (cantidad, pedido_id, producto_id, descripcion) VALUES(1, 1, 1, 'Traera diseño de letrero en USB');*/
/*INSERT INTO pedidos_items (cantidad, pedido_id, producto_id, descripcion) VALUES(2, 1, 4, 'Enviara texto por Whatssap, para que nosotros diseñemos');*/
/*INSERT INTO pedidos_items (cantidad, pedido_id, producto_id, descripcion) VALUES(1, 1, 5, 'RALPH GODIER, ASESOR INMOBILIARIO, 974798823,rgodier@gmail.com');*/
/*INSERT INTO pedidos_items (cantidad, pedido_id, producto_id, descripcion) VALUES(1, 1, 7, 'Se escogio em modelo DORADO de matrimonio con letra San Serif');*/


/*INSERT INTO pedidos (observacion, cliente_id, estado_pedido_id, create_at, entregado_en,vencido, aceptado, pagado, pago, total, saldo) VALUES('Vendra a recoger en el local de Jerusalen el miercols en la tarde', 2, 3, NOW(), '2024-11-14',false,false, false, 0, 45, 45);*/
/*INSERT INTO pedidos_items (cantidad, pedido_id, producto_id, descripcion) VALUES(3, 2, 6, 'Enviara por correo o whattsao excel con cargas de empleados y el logo de la empresa');*/
