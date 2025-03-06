/* Populate tabla clientes */

INSERT INTO tipo_documentos (acronimo, nombre) VALUES ('DNI', 'Documento Nacional de Identificación');
INSERT INTO tipo_documentos (acronimo, nombre) VALUES ('CEX', 'Carnet de Extranjeria');
INSERT INTO tipo_documentos (acronimo, nombre) VALUES ('RUC', 'Registro único del contribuyente');



INSERT INTO clientes (tipo_documentos_id, nombres, apellidos, create_at, numero_documento, celular) VALUES(1, 'Andrés', 'Guzmán', '2018-01-01','41953480','054292');
INSERT INTO clientes (tipo_documentos_id, nombres, apellidos, create_at, numero_documento, celular) VALUES(1, 'Mr. John', 'Doe', '2018-01-02','11953480','0544222');
INSERT INTO clientes (tipo_documentos_id, nombres, apellidos, create_at, numero_documento, celular) VALUES(1, 'Linus', 'Torvalds', '2018-01-03','02953480','0543292');
INSERT INTO clientes (tipo_documentos_id, nombres, apellidos, create_at, numero_documento, celular) VALUES(1, 'Rasmus', 'Lerdorf',  '2018-01-04','71953480','9544292');
INSERT INTO clientes (tipo_documentos_id, nombres, apellidos, create_at, numero_documento, celular) VALUES(2, 'Erich', 'Gamma',  '2018-02-01','41955680','8544292');
INSERT INTO clientes (tipo_documentos_id, nombres, apellidos, create_at, numero_documento, celular) VALUES(2, 'Richard', 'Helm',  '2018-02-10','40053480','7544292');
INSERT INTO clientes (tipo_documentos_id, nombres, apellidos, create_at, numero_documento, celular) VALUES(1, 'Ralph', 'Johnson',  '2018-02-18','41953120','6544292');
INSERT INTO clientes (tipo_documentos_id, nombres, apellidos, create_at, numero_documento, celular) VALUES(1, 'John', 'Vlissides',  '2018-02-28','21953480','5544292');
INSERT INTO clientes (tipo_documentos_id, nombres, apellidos, create_at, numero_documento, celular) VALUES(2, 'Dr. James', 'Gosling',  '2018-03-03','47653480','4444292');
INSERT INTO clientes (tipo_documentos_id, nombres, apellidos, create_at, numero_documento, celular) VALUES(2, 'Magma', 'Lee',  '2018-03-04','01953480','9944292');
INSERT INTO clientes (tipo_documentos_id, razon_social, create_at, numero_documento, celular) VALUES(3, 'Rico Pollo', '2018-03-05','10413534801','9744292');
INSERT INTO clientes (tipo_documentos_id, razon_social, create_at, numero_documento, celular) VALUES(3, 'Clinica Zorana', '2018-03-06','10418934802','99544292');

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
INSERT INTO tipo_pagos (nombre, moneda, cc, cci) VALUES ('EFECTIVO', 'soles',  '','');
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

/*Parametros generales COLORES*/
INSERT INTO colores (id, nombre) VALUES(0,'Vacio');
INSERT INTO colores (id, nombre) VALUES(1,'Café');
INSERT INTO colores (id, nombre) VALUES(2,'Rojo');
INSERT INTO colores (id, nombre) VALUES(3,'Blanco');
INSERT INTO colores (id, nombre) VALUES(4,'Negro');
INSERT INTO colores (id, nombre) VALUES(5,'Celeste');
INSERT INTO colores (id, nombre) VALUES(6,'Verde');
INSERT INTO colores (id, nombre) VALUES(7,'Azul');
INSERT INTO colores (id, nombre) VALUES(8,'Naranja');
INSERT INTO colores (id, nombre) VALUES(9,'Amarillo');
INSERT INTO colores (id, nombre) VALUES(10,'Rosdo');
INSERT INTO colores (id, nombre) VALUES(11,'Blanco con verde');
INSERT INTO colores (id, nombre) VALUES(12,'Blanco con rojo');
INSERT INTO colores (id, nombre) VALUES(13,'Blanco con naranja');
INSERT INTO colores (id, nombre) VALUES(14,'Blanco con amarillo');
INSERT INTO colores (id, nombre) VALUES(15,'Blanco con celeste');
INSERT INTO colores (id, nombre) VALUES(16,'Blanco con rosado');
INSERT INTO colores (id, nombre) VALUES(17,'Otro color');

/*Parametros generales MATERIAL*/
INSERT INTO materiales (id, nombre) VALUES(0,'Vacio');
INSERT INTO materiales (id, nombre) VALUES(1,'Papel');
INSERT INTO materiales (id, nombre) VALUES(2,'Papel corrugado');
INSERT INTO materiales (id, nombre) VALUES(3,'Cartulina');
INSERT INTO materiales (id, nombre) VALUES(4,'Cartón corrugado');
INSERT INTO materiales (id, nombre) VALUES(5,'Cerámica');
INSERT INTO materiales (id, nombre) VALUES(6,'Aluminio');
INSERT INTO materiales (id, nombre) VALUES(7,'Peltre');
INSERT INTO materiales (id, nombre) VALUES(8,'Otro material');

/*Parametros generales CATEGORIA*/
INSERT INTO categorias (id, nombre) VALUES(0,'Seleccione la categoria');
INSERT INTO categorias (id, nombre) VALUES(1,'Pizza');
INSERT INTO categorias (id, nombre) VALUES(2,'Tortas');
INSERT INTO categorias (id, nombre) VALUES(3,'Pasteles');
INSERT INTO categorias (id, nombre) VALUES(4,'Comida');
INSERT INTO categorias (id, nombre) VALUES(5,'Joyeria');
INSERT INTO categorias (id, nombre) VALUES(6,'Opticas');
INSERT INTO categorias (id, nombre) VALUES(7,'Merchandaising');

/*Parametros generales USO*/
INSERT INTO usos (id, nombre) VALUES(0,'Seleccione el uso Interno');
INSERT INTO usos (id, nombre) VALUES(1,'Insumo');
INSERT INTO usos (id, nombre) VALUES(2,'Producto reventa');

/* Populate tabla productos */
INSERT INTO productos (color_id,material_id,categoria_id,uso_id,cantidad_stock,nombre, precio_neto, create_at) VALUES(0,0,0,0,230,'Letrero luminoso', 10, NOW());
INSERT INTO productos (color_id,material_id,categoria_id,uso_id,cantidad_stock,nombre, precio_neto, create_at) VALUES(1,1,0,1,240,'Letrero en strucutura', 15, NOW());
INSERT INTO productos (color_id,material_id,categoria_id,uso_id,cantidad_stock,nombre, precio_neto, create_at) VALUES(0,2,0,1,200,'Letrero en parante', 20, NOW());
INSERT INTO productos (color_id,material_id,categoria_id,uso_id,cantidad_stock,nombre, precio_neto, create_at) VALUES(0,3,0,1,200,'Letrero en Roll Screen', 10, NOW());
INSERT INTO productos (color_id,material_id,categoria_id,uso_id,cantidad_stock,nombre, precio_neto, create_at) VALUES(3,4,0,1,210,'Sellos', 12, NOW());
INSERT INTO productos (color_id,material_id,categoria_id,uso_id,cantidad_stock,nombre, precio_neto, create_at) VALUES(0,0,0,1,260,'Fotocheck', 5, NOW());
INSERT INTO productos (color_id,material_id,categoria_id,uso_id,cantidad_stock,nombre, precio_neto, create_at) VALUES(0,0,0,1,200,'Tarjeta matrimonio', 2, NOW());
INSERT INTO productos (color_id,material_id,categoria_id,uso_id,cantidad_stock,nombre, precio_neto, create_at) VALUES(2,0,0,2,200,'Tarjeta bautizo', 1, NOW());
INSERT INTO productos (color_id,material_id,categoria_id,uso_id,cantidad_stock,nombre, precio_neto, create_at) VALUES(3,0,0,2,200,'Tarjeta personales', 5, NOW());
INSERT INTO productos (color_id,material_id,categoria_id,uso_id,cantidad_stock,nombre, precio_neto, create_at) VALUES(4,0,0,2,230,'Tarjeta varios', 8, NOW());
INSERT INTO productos (color_id,material_id,categoria_id,uso_id,cantidad_stock,nombre, precio_neto, create_at) VALUES(1,5,2,2,200,'Taza', 8, NOW());
INSERT INTO productos (color_id,material_id,categoria_id,uso_id,cantidad_stock,nombre, precio_neto, create_at) VALUES(2,5,3,2,20,'Taza', 8, NOW());
INSERT INTO productos (color_id,material_id,categoria_id,uso_id,cantidad_stock,nombre, precio_neto, create_at) VALUES(3,5,3,2,90,'Taza', 8, NOW());


/* Creamos estado de pedidos */
INSERT INTO estado_pedido (estado, descripcion) VALUES ('Registrado', 'No venció, no aceptado');
INSERT INTO estado_pedido (estado, descripcion) VALUES ('Vencido', 'Venció, no aceptado'); /*este campo se implementará con Schduled en segunda version*/
INSERT INTO estado_pedido (estado, descripcion) VALUES ('Devuelto', 'Devuelto por errores');
INSERT INTO estado_pedido (estado, descripcion) VALUES ('Entregado', 'Pagado, entregado al cliente');

/* Creamos estado de pedidos */
INSERT INTO tipo_pedido (id, nombre) VALUES (1, 'VENTA AL CLIENTE');
INSERT INTO tipo_pedido (id, nombre) VALUES (2, 'COMPRA O ADQUISICION');

 /*Creamos algunos pedidos */
/*INSERT INTO pedidos (observacion, cliente_id,tipo_pedido_id, estado_pedido_id, create_at, entregado_en,vencido, aceptado, pagado, costo_total, precio_bruto_total, precio_neto_total,pago_bruto_total, pago_neto_total, saldo_pedido) VALUES('Envío delivery el dia miercoles en la tarde, contactar al señor ramirez', 1, 1,1, NOW(),'2024-11-16',false,false,false, 0,0,44,0,0,44);*/

/*INSERT INTO pedidos_items (cantidad,importe, pedido_id, producto_id, descripcion) VALUES(1,25, 1, 1, 'Traera diseño de letrero en USB');*/
/*INSERT INTO pedidos_items (cantidad,importe, pedido_id, producto_id, descripcion) VALUES(2,30, 1, 4, 'Enviara texto por Whatssap, para que nosotros diseñemos');*/
/*INSERT INTO pedidos_items (cantidad,importe, pedido_id, producto_id, descripcion) VALUES(1,20, 1, 5, 'RALPH GODIER, ASESOR INMOBILIARIO, 974798823,rgodier@gmail.com');*/
/*INSERT INTO pedidos_items (cantidad,importe, pedido_id, producto_id, descripcion) VALUES(1,21, 1, 7, 'Se escogio em modelo DORADO de matrimonio con letra San Serif');*/


/*INSERT INTO pedidos (observacion, cliente_id,tipo_pedido_id, estado_pedido_id, create_at, entregado_en,vencido, aceptado, pagado, costo_total, precio_bruto_total, precio_neto_total,pago_bruto_total, pago_neto_total, saldo_pedido) VALUES('Vendra a recoger en el local de Jerusalen el miercols en la tarde', 2, 1,3, NOW(), '2024-11-14',false,false, false, 0, 0, 15,0,0,15);*/
/*INSERT INTO pedidos_items (cantidad,importe, pedido_id, producto_id, descripcion) VALUES(3,45, 2, 6, 'Enviara por correo o whattsao excel con cargas de empleados y el logo de la empresa');*/

/*Parametros generales ORIGEN*/
INSERT INTO genericos_app (codigo, valor1) VALUES('IGV',0.18);



