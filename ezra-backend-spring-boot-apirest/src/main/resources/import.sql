/* Populate tabla clientes */

INSERT INTO tipo_documentos (acronimo, nombre) VALUES ('DNI', 'Documento Nacional de Identificación');
INSERT INTO tipo_documentos (acronimo, nombre) VALUES ('CEX', 'Carnet de Extranjeria');
INSERT INTO tipo_documentos (acronimo, nombre) VALUES ('RUC', 'Registro único del contribuyente');



INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular, usuario_id) VALUES(1, 'Beisaga Arenas Bhernard', '2018-01-01','41953483','054292',1);
INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular) VALUES(1, 'John Doe', '2018-01-02','11953480','0544222');
INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular) VALUES(1, 'Linus Torvalds', '2018-01-03','02953480','0543292');
INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular) VALUES(1, 'Rasmus Lerdorf',  '2018-01-04','71953480','9544292');
INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular) VALUES(2, 'Erich Gamma',  '2018-02-01','41955680','8544292');
INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular) VALUES(2, 'Richard Helm',  '2018-02-10','40053480','7544292');
INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular) VALUES(1, 'Ralph Johnson',  '2018-02-18','41953120','6544292');
INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular) VALUES(1, 'John Vlissides',  '2018-02-28','21953480','5544292');
INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular) VALUES(2, 'James Gosling',  '2018-03-03','47653480','4444292');
INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular) VALUES(2, 'Magma Lee',  '2018-03-04','01953480','9944292');
INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular) VALUES(3, 'Rico Pollo', '2018-03-05','10413534801','9744292');
INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular) VALUES(3, 'Clinica Zorana', '2018-03-06','10418934802','99544292');

/* Creamos algunos usuarios con sus roles */
INSERT INTO `usuarios` (username, password, activo, bloqueado,reintentos, nombres, apellidos) VALUES ('admin','$2a$10$RmdEsvEfhI7Rcm9f/uZXPebZVCcPC7ZXZwV51efAvMAp1rIaRAfPK',1,0,0, 'Bhernard Shomert', 'Beisaga Arenas');
INSERT INTO `usuarios` (username, password, activo, bloqueado,reintentos, nombres, apellidos) VALUES ('steven','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1,0,0, 'Steven Albert', 'Beisaga Arenas');
INSERT INTO `usuarios` (username, password, activo, bloqueado,reintentos, nombres, apellidos) VALUES ('sonia','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1,0,0, 'Sonia Yanet', 'Arenas Alvarez');
INSERT INTO `usuarios` (username, password, activo, bloqueado,reintentos, nombres, apellidos) VALUES ('vane','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1,0,0, 'Vanesa', 'Cojoma');

INSERT INTO `modulos` (nombre) VALUES ('CLIENTES PROVEEDORES');
INSERT INTO `modulos` (nombre) VALUES ('PEDIDOS');
INSERT INTO `modulos` (nombre) VALUES ('MOVIMIENTOS CAJA');
INSERT INTO `modulos` (nombre) VALUES ('CAJA USUARIO');
INSERT INTO `modulos` (nombre) VALUES ('PRODUCTOS');
INSERT INTO `modulos` (nombre) VALUES ('USUARIOS');


INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (1,1, 'ROLE_LIST_CLIENTES','Listar clientes o proveedores');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (1,1, 'ROLE_SEARCH_CLIENTE','Buscar cliente o proveedor');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (1,1, 'ROLE_REGISTER_CLIENTE','Registrar cliente o proveedor');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (1,1, 'ROLE_UPDATE_CLIENTE','Actualizar cliente o proveedor');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (1,1, 'ROLE_DELETE_CLIENTE','Borrar cliente o proveedor');

INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_CREATE_PEDIDO','Crear pedido');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_LIST_VENTAS','Listar venta');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_SEARCH_VENTA','Buscar pedido venta');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_REPORT_VENTA','Reporte de venta');

INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_LIST_COMPRAS','Listar compras');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_SEARCH_COMPRA','Buscar pedido compra');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_REPORT_COMPRA','Reporte de compra');	

INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_VIEW_DETAILS_PEDIDO','Ver detalle del pedido');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_REGISTER_PAGO_PEDIDO','Registrar pago por pedido');

INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (3,1, 'ROLE_REGISTER_MOVCAJA','Registro ingreso/egreso en caja chica');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (3,1, 'ROLE_REPORT_MOVCAJA','Reporte de movimientos (Todo los movimientos y cajeros)');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (3,1, 'ROLE_REPORT_MOVCAJA_USUARIO','Reporte de movimientos por cajero');

INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (4,1, 'ROLE_OPEN_CJU','Apertura caja');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (4,1, 'ROLE_CLOSE_CJU','Cierre caja');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (4,1, 'ROLE_REPORT_CJU','Reporte de caja (Todos los cajas y cajeros)');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (4,1, 'ROLE_REPORT_USUARIO_CJU','Reporte de caja por cajero');

INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_LIST_PRODUCTOS','Listar productos');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_SEARCH_PRODUCTO','Buscar producto');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_REGISTER_PRODUCTO','Rgistrar producto');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_UPDATE_PRODUCTO','Actualizar producto');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_DELETE_PRODUCTO','Borrar producto');

INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (6,1, 'ROLE_LIST_USUARIOS','Listar usuario');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (6,1, 'ROLE_SEARCH_USUARIO','Buscar usuario');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (6,1, 'ROLE_ASIGNAR_ROL_USUARIO','Asignar rol a usuario');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 3);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 4);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 5);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 6);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 7);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 8);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 9);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 10);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 11);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 12);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 13);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 14);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 15);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 16);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 17);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 18);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 19);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 20);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 21);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 22);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 23);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 24);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 25);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 26);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 27);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 28);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 29);



INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 2);

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (3, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (4, 2);


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

/* creamos tipo movimiento PEDIDO*/
INSERT INTO tipo_movimientos_pedido (movimiento, tipo) VALUES ('Pago del cliente','I');
INSERT INTO tipo_movimientos_pedido (movimiento, tipo) VALUES ('Devolución de pago al Cliente','E');
INSERT INTO tipo_movimientos_pedido (movimiento, tipo) VALUES ('Egreso por Compra','E');
INSERT INTO tipo_movimientos_pedido (movimiento, tipo) VALUES ('Ingreso por devolución de compra','I');

/* creamos tipo movimiento CAJA*/
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Ingreso cápital','I');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Sobrante en caja','I');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Otros ingresos','I');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Alimentos','E');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Asignación familiar','E');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Movilidad','E');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Regalos','E');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Dias festivos','E');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Cumpleaños','E');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Comisiones','E');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Bonos','E');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Fondos de pensiones','E');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Sistema nacion de pensiones','E');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Seguro Invalidez','E');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('5ta Categria','E');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('ESSALUD','E');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Seguro vida ley','E');
INSERT INTO tipo_movimientos_caja (movimiento, tipo) VALUES ('Otros gastos','E');




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
INSERT INTO categorias (id, nombre,orden,activa,descripcion) VALUES(1,'Otros',8,1,'Todos los dmas');
INSERT INTO categorias (id, nombre,orden,activa,descripcion) VALUES(2,'Envase de Pizza',7,1,'Tosdsdsdsds dmas');
INSERT INTO categorias (id, nombre,orden,activa) VALUES(3,'Envase de Tortas',6,1);
INSERT INTO categorias (id, nombre,orden,activa) VALUES(4,'Envase de Pasteles',5,1);
INSERT INTO categorias (id, nombre,orden,activa) VALUES(5,'Envase de Comida',3,1);
INSERT INTO categorias (id, nombre,orden,activa) VALUES(6,'Empaque de bolsas',2,1);
INSERT INTO categorias (id, nombre,orden,activa) VALUES(7,'Empaque de joyas',4,1);

/*Parametros generales USO*/
INSERT INTO usos (id, nombre) VALUES(0,'Seleccione el uso Interno');
INSERT INTO usos (id, nombre) VALUES(1,'Insumo');
INSERT INTO usos (id, nombre) VALUES(2,'Producto reventa');

/* Populate tabla productos */
INSERT INTO productos (codigo,nombre,	descripcion,	medidas,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	precio_bruto,	precio_bruto_rebajado,	precio_neto,	precio_neto_reabajado,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id) VALUES('A1','Caja para pizza','Caja de carton corruugado para pizza con marca personalizada','10x15x20','0.25 kg','envase-de-carton-pizza.png',5,0,0,100,1000,10,0,40,30,47.2,50,now(),1,0,1,2,2,2);
INSERT INTO productos (codigo,nombre,	descripcion,	medidas,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	precio_bruto,	precio_bruto_rebajado,	precio_neto,	precio_neto_reabajado,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id) VALUES('B1','Caja para tortas','Caja de cartón corrugado para tortas con marca personalizada','10x15x20','1 kg','caja-para-torta.png',5,0,0,100,1000,30,0,40,30,47.2,50,now(),1,1,1,2,3,2);
INSERT INTO productos (codigo,nombre,	descripcion,	medidas,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	precio_bruto,	precio_bruto_rebajado,	precio_neto,	precio_neto_reabajado,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id) VALUES('B2','Envase de plastico para torta','Envase de plastico para torta con marca sticker personalizada','30x15x20','1 kg','envase-torta-redondo.png',5,0,0,100,1000,10,0,40,30,47.2,50,now(),1,0,3,2,4,2);
INSERT INTO productos (codigo,nombre,	descripcion,	medidas,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	precio_bruto,	precio_bruto_rebajado,	precio_neto,	precio_neto_reabajado,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id) VALUES('C1','Envase de comida blanco','Envase de bagazo de azucar para comida blanco con marca en forma de sticker personalizado','10x15x20','1 kg','envase-comida-blanco.png',5,0,0,100,1000,1,0,20,10,23.6,35,now(),1,1,1,3,5,2);
INSERT INTO productos (codigo,nombre,	descripcion,	medidas,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	precio_bruto,	precio_bruto_rebajado,	precio_neto,	precio_neto_reabajado,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id) VALUES('C2','Envase de comida café','Envase de bagazo de azucar para comida color café con marca en forma de sticker personalizado','30x15x20','1 kg','envase-comida-cafe.png',4,0,15,40,2000,1,0,20,10,23.6,35,now(),0,1,2,3,6,2);
INSERT INTO productos (codigo,nombre,	descripcion,	medidas,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	precio_bruto,	precio_bruto_rebajado,	precio_neto,	precio_neto_reabajado,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id) VALUES('E1','Bolsa de plastico blanca','Bolsa de plastico blanca para llevar artículos con marca personalizada','10x15x20','1 kg','bolsa-de-plastico-blanca.png',4,0,2,40,2000,2,0,20,10,23.6,35,now(),0,1,2,3,6,2);
INSERT INTO productos (codigo,nombre,	descripcion,	medidas,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	precio_bruto,	precio_bruto_rebajado,	precio_neto,	precio_neto_reabajado,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id) VALUES('F1','Bolsa de plastico negra','Bolsa de plastico negra para llevar artículos con marca personalizada','10x15x20','1 kg','bolsa-de-plastico-negra.png',4,0,3,40,2000,2,0,20,10,23.6,35,now(),1,1,3,3,6,2);


/* Creamos estado de pedidos */
INSERT INTO estado_pedido (estado, descripcion) VALUES ('Pendiente', 'Pendiente de realizar');
INSERT INTO estado_pedido (estado, descripcion) VALUES ('Vencido', 'Venció por imcumplimiento de fecha de entrega'); /*este campo se implementará con Schduled en segunda version*/
INSERT INTO estado_pedido (estado, descripcion) VALUES ('Devuelto', 'Devuelto por vencimiento o errores');
INSERT INTO estado_pedido (estado, descripcion) VALUES ('Cancelado', 'Cancelado por falta de inicial o desánimo');
INSERT INTO estado_pedido (estado, descripcion) VALUES ('Entregado', 'Entregado y aceptado por el cliente');
INSERT INTO estado_pedido (estado, descripcion) VALUES ('Adquirido', 'Adquirido del proveedor');


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



