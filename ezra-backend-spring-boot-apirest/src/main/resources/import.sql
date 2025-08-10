/* Populate tabla clientes */

INSERT INTO tipo_documentos (acronimo, nombre) VALUES ('DNI', 'Documento Nacional de Identificación');
INSERT INTO tipo_documentos (acronimo, nombre) VALUES ('CEX', 'Carnet de Extranjeria');
INSERT INTO tipo_documentos (acronimo, nombre) VALUES ('RUC', 'Registro único del contribuyente');
INSERT INTO tipo_documentos (acronimo, nombre) VALUES ('OTR', 'Otro documento de indentificación');


INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular, usuario_id) VALUES(1, 'Beisaga Arenas Bhernard', '2018-01-01','41953483','054292',1);
INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular, usuario_id) VALUES(1, 'Arenas Alvares Soni Yanet', '2018-01-02','11953480','0544222',2);
INSERT INTO clientes (tipo_documentos_id, nom_apell_rz, create_at, numero_documento, celular, usuario_id) VALUES(1, 'Cojoma Vanesa', '2018-01-03','02953480','0543292',3);


/* Creamos algunos usuarios con sus roles */
INSERT INTO `usuarios` (username, password, activo, bloqueado,reintentos,nom_apell_rz) VALUES ('admin','$2a$10$RmdEsvEfhI7Rcm9f/uZXPebZVCcPC7ZXZwV51efAvMAp1rIaRAfPK',1,0,0, 'Bhernard Shomert Beisaga Arenas');
INSERT INTO `usuarios` (username, password, activo, bloqueado,reintentos,nom_apell_rz) VALUES ('sonia','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1,0,0, 'Sonia Yanet Arenas Alvarez');
INSERT INTO `usuarios` (username, password, activo, bloqueado,reintentos,nom_apell_rz) VALUES ('vane','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1,0,0, 'Vanesa Cojoma');

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

INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_CREATE_VENTA','Crear pedido venta (cliente) ');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_LIST_MY_ORDERS','Listar mis pedidos');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_LIST_VENTAS','Listar pedidos');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_SEARCH_VENTA','Buscar pedido venta');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_REPORT_VENTA','Reporte de venta');

INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_CREATE_COMPRA','Crear pedido compra (Proveedor) ');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_LIST_COMPRAS','Listar compras');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_SEARCH_COMPRA','Buscar pedido compra');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_REPORT_COMPRA','Reporte de compra');	

INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_VIEW_DETAILS_PEDIDO','Ver detalle del pedido');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (2,1, 'ROLE_REGISTER_PAGO_PEDIDO','Registrar pago por pedido');

INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (3,1, 'ROLE_REGISTER_MOVCAJA','Registro ingreso/egreso en caja chica');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (3,1, 'ROLE_REPORT_MOVCAJA','Reporte de movimientos (Todo los movimientos y cajeros)');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (3,1, 'ROLE_RPTE_MOVCAJA_USUARIO','Reporte de movimientos por cajero');

INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (4,1, 'ROLE_OPEN_CJU','Apertura caja');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (4,1, 'ROLE_CLOSE_CJU','Cierre caja');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (4,1, 'ROLE_REPORT_CJU','Reporte de caja (Todos los cajas y cajeros)');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (4,1, 'ROLE_REPORT_USUARIO_CJU','Reporte de caja por cajero');

INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_LIST_PRODUCTOS','Listar productos');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_SEARCH_PRODUCTO','Buscar producto');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_IMAGE_PRODUCTO','Cargar imagen producto');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_REGISTER_PRODUCTO','Registrar producto');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_UPDATE_PRODUCTO','Actualizar producto');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_DELETE_PRODUCTO','Borrar producto');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_DELETE_MARGEN_PRODUCTO','Borrar margen de ganancia del producto');

INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_LIST_CATEGORIAS','Listar categorías');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_REGISTER_CATEGORIA','Crear categoría');
INSERT INTO `roles` (modulo_id,activated, nombre,descripcion) VALUES (5,1, 'ROLE_UPDATE_CATEGORIA','Actualizar categoría');


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
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 30);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 31);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 32);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 33);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 34);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 35);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 36);

/* ROL DE CAJERA 1*/
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 8);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 9);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 14);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 15);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 16);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 18);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 19);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 20);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 22);

/* ROL DE CAJERA 2*/
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (3, 8);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (3, 9);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (3, 14);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (3, 15);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (3, 16);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (3, 18);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (3, 19);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (3, 20);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (3, 22);




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
INSERT INTO colores (id, nombre) VALUES(10,'Rosado');
INSERT INTO colores (id, nombre) VALUES(11,'Blanco con verde');
INSERT INTO colores (id, nombre) VALUES(12,'Blanco con rojo');
INSERT INTO colores (id, nombre) VALUES(13,'Blanco con naranja');
INSERT INTO colores (id, nombre) VALUES(14,'Blanco con amarillo');
INSERT INTO colores (id, nombre) VALUES(15,'Blanco con celeste');
INSERT INTO colores (id, nombre) VALUES(16,'Blanco con rosado');
INSERT INTO colores (id, nombre) VALUES(17,'Transparente');
INSERT INTO colores (id, nombre) VALUES(18,'Otro color');

/*Parametros generales MATERIAL*/
INSERT INTO materiales (id, nombre) VALUES(0,'Vacio');
INSERT INTO materiales (id, nombre) VALUES(1,'Papel');
INSERT INTO materiales (id, nombre) VALUES(2,'Papel corrugado');
INSERT INTO materiales (id, nombre) VALUES(3,'Cartulina');
INSERT INTO materiales (id, nombre) VALUES(4,'Cartón corrugado');
INSERT INTO materiales (id, nombre) VALUES(5,'Cerámica');
INSERT INTO materiales (id, nombre) VALUES(6,'Aluminio');
INSERT INTO materiales (id, nombre) VALUES(7,'Peltre');
INSERT INTO materiales (id, nombre) VALUES(8,'Vidrio');
INSERT INTO materiales (id, nombre) VALUES(9,'Otro material');

/*Parametros generales CATEGORIA*/
INSERT INTO categorias (id,imagen,orden,activa, nombre, descripcion) VALUES(1,'no-imagen.jpg',1,1,'Tienda', 'Categoria muestra todos los productos de nuestra tienda');
INSERT INTO categorias (id,imagen,orden,activa, nombre, descripcion) VALUES(2,'no-imagen.jpg',2,1,'Tazas', 'Una descripcion para tazas personalizadas');
INSERT INTO categorias (id,imagen,orden,activa, nombre, descripcion) VALUES(3,'no-imagen.jpg',3,1,'Termos y botellas', 'Una descripcion para termos y botellas');
INSERT INTO categorias (id,imagen,orden,activa, nombre, descripcion) VALUES(4,'no-imagen.jpg',4,1,'Vasos', 'Una descripcion para vasos de vidirio');
INSERT INTO categorias (id,imagen,orden,activa, nombre, descripcion) VALUES(5,'no-imagen.jpg',5,1,'Libretas', 'Una descripcion para articulos de libreria');
INSERT INTO categorias (id,imagen,orden,activa, nombre, descripcion) VALUES(6,'no-imagen.jpg',6,1,'Lapiceros publicitarios', 'Una descripcion para articulos de libreria');
INSERT INTO categorias (id,imagen,orden,activa, nombre, descripcion) VALUES(7,'no-imagen.jpg',7,1,'Máquinas e insumos', 'Una descripcion para máquinas e insumos');
INSERT INTO categorias (id,imagen,orden,activa, nombre, descripcion) VALUES(8,'no-imagen.jpg',8,2,'Otros', 'Una descripcion para otros');


/*Parametros generales USO*/
INSERT INTO usos (id, nombre) VALUES(0,'Seleccione el uso Interno');
INSERT INTO usos (id, nombre) VALUES(1,'Producto reventa');
INSERT INTO usos (id, nombre) VALUES(2,'Máquinas reventa');
INSERT INTO usos (id, nombre) VALUES(3,'Insumos reventa');
INSERT INTO usos (id, nombre) VALUES(4,'Sublimación servicio');
INSERT INTO usos (id, nombre) VALUES(5,'Diseño gráfico servicio');
INSERT INTO usos (id, nombre) VALUES(6,'Envío servicio');

/*Parametros generales USO*/
INSERT INTO estados_producto (nombre) VALUES('Nuevo');
INSERT INTO estados_producto (nombre) VALUES('En stock');
INSERT INTO estados_producto (nombre) VALUES('Por agotarse');
INSERT INTO estados_producto (nombre) VALUES('Agotado');
INSERT INTO estados_producto (nombre) VALUES('Próximo');


/* Populate tabla productos */
INSERT INTO productos (codigo,nombre,	descripcion,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id, impuesto_igv, estado_producto_id) VALUES('SR1','Servicio de sublimación','Servicio de sublimación de artículo',NULL,'no-imagen.jpg',1,0,99999999,1,1,1,7,now(),0,1,18,9,8,4,18,5);
INSERT INTO productos (codigo,nombre,	descripcion,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id, impuesto_igv, estado_producto_id) VALUES('SR2','Servicio de diseño gráfico','Servicio diseño y retoque de imágen',NULL,'no-imagen.jpg',1,0,99999999,1,1,1,7,now(),0,1,18,9,8,5,18,5);
INSERT INTO productos (codigo,nombre,	descripcion,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id, impuesto_igv, estado_producto_id) VALUES('SR3','Servicio de entrega local','Servicio de envió a domicilio en la ciudad',NULL,'no-imagen.jpg',1,0,99999999,1,1,1,11,now(),0,1,18,9,8,6,18,5);
INSERT INTO productos (codigo,nombre,	descripcion,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id, impuesto_igv, estado_producto_id) VALUES('SR4','Servicio de entrega a provincias','Servicio de envió a domicilio en otra ciudad',NULL,'no-imagen.jpg',1,0,99999999,1,1,1,50,now(),0,1,18,9,8,6,18,5);
INSERT INTO productos (codigo,nombre,	descripcion,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id, impuesto_igv, estado_producto_id) VALUES('TZ1','Taza de cerámica blanco de 11oz','Taza de cerámica blanco de 11oz, para colocar su marca','1 kg','taza_ceramica_blanco_11oz.png',10,5,0,5,30,5,1.41,now(),1,1,3,5,2,1,18,5);
INSERT INTO productos (codigo,nombre,	descripcion,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id, impuesto_igv, estado_producto_id) VALUES('TZ2','Taza de cerámica blanco de 15oz','Taza de cerámica blanco de 15oz, para colocar su marca','2 kg','taza_ceramica_blanco_15oz.png',20,10,0,5,50,10,1.50,now(),1,1,3,5,2,1,18,5);
INSERT INTO productos (codigo,nombre,	descripcion,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id, impuesto_igv, estado_producto_id) VALUES('TZ3','Taza de cerámica negro de 11oz','Taza de cerámica negro de 11oz, para colocar su marca','1.50 kg','taza_ceramica_negra_11oz.png',10,5,0,5,100,5,1.60,now(),1,1,4,5,2,1,18,5);
INSERT INTO productos (codigo,nombre,	descripcion,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id, impuesto_igv, estado_producto_id) VALUES('TZ4','Taza de cerámica blanca 11 oz en V','Taza de cerámica blanca 11 oz en V, para colocar su marca','1.50 kg','vaso_de_ceramica_11oz_v.png',30,10,0,10,200,10,1.80,now(),1,1,3,5,2,1,18,5);


/*INSERT INTO productos (codigo,nombre,	descripcion,	medidas,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id, impuesto_igv, estado_producto_id) VALUES('C2','Envase de comida café','Envase de bagazo de azucar para comida color café con marca en forma de sticker personalizado','30x15x20 cm','1 kg','envase-comida-cafe.png',4,0,15,40,2000,20,10,now(),0,1,2,3,6,2,18,5);*/
/*INSERT INTO productos (codigo,nombre,	descripcion,	medidas,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id, impuesto_igv, estado_producto_id ) VALUES('E1','Bolsa de plastico blanca','Bolsa de plastico blanca para llevar artículos con marca personalizada','10x15x20 cm','1 kg','bolsa-de-plastico-blanca.png',4,0,1200,1,2000,1,10,now(),0,1,2,3,6,2,18,1);*/
/*INSERT INTO productos (codigo,nombre,	descripcion,	medidas,	peso,	imagen,	umbral_poca_cantidad,	umbral_cantidad_agotada,	cantidad_stock,	min_cantidad_pedido,	max_cantidad_pedido,	grupos_de,	costo_unitario,	create_at,	visible_en_tienda,	activo,	color_id,	material_id	,categoria_id,	uso_id, impuesto_igv, estado_producto_id ) VALUES('F1','Bolsa de plastico negra','Bolsa de plastico negra para llevar artículos con marca personalizada','10x15x20 cm','1 kg','bolsa-de-plastico-negra.png',1,0,1000,1,2000,1,10,now(),1,1,3,3,6,2,18,4);*/


/*margenes del producto*/
INSERT INTO margenes_producto (margen, precio_neto, precio_neto_sugerido, max_cantidad, min_cantidad, producto_id) VALUES (1.00	, 6, 7, NULL, '1', 1);
INSERT INTO margenes_producto (margen, precio_neto, precio_neto_sugerido, max_cantidad, min_cantidad, producto_id) VALUES (5.00	, 10, 11, NULL, '1', 2);
INSERT INTO margenes_producto (margen, precio_neto, precio_neto_sugerido, max_cantidad, min_cantidad, producto_id) VALUES (5.00	, 45, 50, NULL, '1', 3);
INSERT INTO margenes_producto (margen, precio_neto, precio_neto_sugerido, max_cantidad, min_cantidad, producto_id) VALUES (60.00, 2.85, 2.85, 5, '1', 4);
INSERT INTO margenes_producto (margen, precio_neto, precio_neto_sugerido, max_cantidad, min_cantidad, producto_id) VALUES (50.00, 2.69, 2.69, 10, '5', 4);
INSERT INTO margenes_producto (margen, precio_neto, precio_neto_sugerido, max_cantidad, min_cantidad, producto_id) VALUES (40.00, 2.53, 2.53, 15, '11', 4);
INSERT INTO margenes_producto (margen, precio_neto, precio_neto_sugerido, max_cantidad, min_cantidad, producto_id) VALUES (30.00, 2.37, 2.37, NULL, '16', 4);
INSERT INTO margenes_producto (margen, precio_neto, precio_neto_sugerido, max_cantidad, min_cantidad, producto_id) VALUES (70.00, 2.82, 2.82, 10, '1', 5);
INSERT INTO margenes_producto (margen, precio_neto, precio_neto_sugerido, max_cantidad, min_cantidad, producto_id) VALUES (60.00, 2.67, 2.67, NULL, '11', 5);
INSERT INTO margenes_producto (margen, precio_neto, precio_neto_sugerido, max_cantidad, min_cantidad, producto_id) VALUES (70.00, 2.65, 2.65, NULL, '1', 6);
INSERT INTO margenes_producto (margen, precio_neto, precio_neto_sugerido, max_cantidad, min_cantidad, producto_id) VALUES (80.00, 3.56, 3.56, NULL, '1', 7);



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



