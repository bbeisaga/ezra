-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.4.32-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Volcando estructura para tabla db_ezra.cajas
CREATE TABLE IF NOT EXISTS `cajas` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `ubicacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.caja_usuarios
CREATE TABLE IF NOT EXISTS `caja_usuarios` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activa` bit(1) NOT NULL,
  `egreso_esperado` decimal(38,2) DEFAULT NULL,
  `ingreso_esperado` decimal(38,2) DEFAULT NULL,
  `saldo_caja` decimal(38,2) DEFAULT NULL,
  `saldo_por_conteo` decimal(38,2) DEFAULT NULL,
  `fecha_actualizacion` datetime(6) DEFAULT NULL,
  `fecha_apertura` datetime(6) DEFAULT NULL,
  `fecha_cierre` datetime(6) DEFAULT NULL,
  `caja_id` tinyint(4) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh6wjivlrwl4yt0kcjud6ua4ky` (`caja_id`),
  KEY `FKguyinp7f7ibwgi1lku4ukftk7` (`usuario_id`),
  CONSTRAINT `FKguyinp7f7ibwgi1lku4ukftk7` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `FKh6wjivlrwl4yt0kcjud6ua4ky` FOREIGN KEY (`caja_id`) REFERENCES `cajas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.categorias
CREATE TABLE IF NOT EXISTS `categorias` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activa` bit(1) NOT NULL,
  `orden` int(11) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.clientes
CREATE TABLE IF NOT EXISTS `clientes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tipo_documentos_id` bigint(20) DEFAULT NULL,
  `numero_documento` varchar(255) DEFAULT NULL,
  `nom_apell_rz` varchar(255) NOT NULL,
  `celular` varchar(255) DEFAULT NULL,
  `clave` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2viccgf178bd74vfbq8ctsv8t` (`numero_documento`),
  KEY `FKomlnp1mb1g0u5og27d7x7xg7r` (`tipo_documentos_id`),
  CONSTRAINT `FKomlnp1mb1g0u5og27d7x7xg7r` FOREIGN KEY (`tipo_documentos_id`) REFERENCES `tipo_documentos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.colores
CREATE TABLE IF NOT EXISTS `colores` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.estados_producto
CREATE TABLE IF NOT EXISTS `estados_producto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.estado_pedido
CREATE TABLE IF NOT EXISTS `estado_pedido` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.genericos_app
CREATE TABLE IF NOT EXISTS `genericos_app` (
  `codigo` varchar(255) NOT NULL,
  `valor1` decimal(38,2) DEFAULT NULL,
  `valor2` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.margenes_producto
CREATE TABLE IF NOT EXISTS `margenes_producto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `min_cantidad` int DEFAULT 1,
  `max_cantidad` bigint(20) DEFAULT NULL,
  `margen` decimal(10,2) DEFAULT 0.00,
  `precio_neto` decimal(10,2) DEFAULT 0.00,
  `precio_neto_sugerido` decimal(10,2) DEFAULT 0.00,
  `producto_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8tqcbudnn4dpbi5t399i1rxmi` (`producto_id`),
  CONSTRAINT `FK8tqcbudnn4dpbi5t399i1rxmi` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.materiales
CREATE TABLE IF NOT EXISTS `materiales` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.modulos
CREATE TABLE IF NOT EXISTS `modulos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKt5wsps1raxojltdfevtcpwr2v` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.movimientos
CREATE TABLE IF NOT EXISTS `movimientos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ingreso_dinero` decimal(38,2) DEFAULT NULL,
  `egreso_dinero` decimal(38,2) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `pedido_id` bigint(20) DEFAULT NULL,
  `caja_usuario_id` bigint(20) DEFAULT NULL,
  `tipo_movimiento_pedido_id` bigint(20) DEFAULT NULL,
  `tipo_pago_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbll98tdvtgu085pjqj7od3hxj` (`caja_usuario_id`),
  KEY `FKjq3op904pi5h8nmbxx2vwtrpe` (`pedido_id`),
  KEY `FKq2sfccx69bs5y7e8t72m2abxo` (`tipo_movimiento_pedido_id`),
  KEY `FKmt0enkj7aw8thohrgyxgckm46` (`tipo_pago_id`),
  CONSTRAINT `FKbll98tdvtgu085pjqj7od3hxj` FOREIGN KEY (`caja_usuario_id`) REFERENCES `caja_usuarios` (`id`),
  CONSTRAINT `FKjq3op904pi5h8nmbxx2vwtrpe` FOREIGN KEY (`pedido_id`) REFERENCES `pedidos` (`id`),
  CONSTRAINT `FKmt0enkj7aw8thohrgyxgckm46` FOREIGN KEY (`tipo_pago_id`) REFERENCES `tipo_pagos` (`id`),
  CONSTRAINT `FKq2sfccx69bs5y7e8t72m2abxo` FOREIGN KEY (`tipo_movimiento_pedido_id`) REFERENCES `tipo_movimientos_pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.movimientos_caja
CREATE TABLE IF NOT EXISTS `movimientos_caja` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ingreso_dinero` decimal(38,2) DEFAULT NULL,
  `egreso_dinero` decimal(38,2) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `caja_usuario_id` bigint(20) DEFAULT NULL,
  `tipo_movimiento_caja_id` bigint(20) DEFAULT NULL,
  `tipo_pago_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfnrdcm9afmqk9egl99ra2scnu` (`caja_usuario_id`),
  KEY `FK4ynnwym8et8n1tcn629tbkw6m` (`tipo_movimiento_caja_id`),
  KEY `FK6qfrfdreg8icpfsl8pbnripn6` (`tipo_pago_id`),
  CONSTRAINT `FK4ynnwym8et8n1tcn629tbkw6m` FOREIGN KEY (`tipo_movimiento_caja_id`) REFERENCES `tipo_movimientos_caja` (`id`),
  CONSTRAINT `FK6qfrfdreg8icpfsl8pbnripn6` FOREIGN KEY (`tipo_pago_id`) REFERENCES `tipo_pagos` (`id`),
  CONSTRAINT `FKfnrdcm9afmqk9egl99ra2scnu` FOREIGN KEY (`caja_usuario_id`) REFERENCES `caja_usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.pedidos
CREATE TABLE IF NOT EXISTS `pedidos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `costo_bruto_total` decimal(38,2) DEFAULT NULL,
  `costo_neto_total` decimal(38,2) DEFAULT NULL,
  `precio_bruto_total` decimal(38,2) DEFAULT NULL,
  `precio_neto_total` decimal(38,2) DEFAULT NULL,
  `saldo_pedido` decimal(38,2) DEFAULT NULL,
  `pago_total` decimal(38,2) DEFAULT NULL,
  `vuelto_total` decimal(38,2) DEFAULT NULL,
  `flujo_efectivo_total` decimal(38,2) DEFAULT NULL,
  `devuelto` bit(1) NOT NULL,
  `pagado` bit(1) NOT NULL,
  `observacion` varchar(255) DEFAULT NULL,
  `create_at` date DEFAULT NULL,
  `entregado_en` date DEFAULT NULL,
  `adquirido_en` date DEFAULT NULL,
  `nom_apell_rz_envio` varchar(255) DEFAULT NULL,
  `direccion_envio` varchar(255) DEFAULT NULL,
  `celular_envio` varchar(255) DEFAULT NULL,
  `cliente_id` bigint(20) DEFAULT NULL,
  `estado_pedido_id` bigint(20) DEFAULT NULL,
  `tipo_pedido_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg7202lk0hwxn04bmdl2thth5b` (`cliente_id`),
  KEY `FKhqlmrw685jl487n6fsd0cghd7` (`estado_pedido_id`),
  KEY `FK2ydvrs3q71rr10aceqoro1dpm` (`tipo_pedido_id`),
  CONSTRAINT `FK2ydvrs3q71rr10aceqoro1dpm` FOREIGN KEY (`tipo_pedido_id`) REFERENCES `tipo_pedido` (`id`),
  CONSTRAINT `FKg7202lk0hwxn04bmdl2thth5b` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`),
  CONSTRAINT `FKhqlmrw685jl487n6fsd0cghd7` FOREIGN KEY (`estado_pedido_id`) REFERENCES `estado_pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.pedidos_items
CREATE TABLE IF NOT EXISTS `pedidos_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` int(20) DEFAULT NULL,
  `costo_unitario_item` float(10,2) DEFAULT 0.00,
  `importe` decimal(38,2) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `pedido_id` bigint(20) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4v5bywebd4t5re33nng352gex` (`producto_id`),
  KEY `FKdf000fowj0jk3mplu8w4woyis` (`pedido_id`),
  CONSTRAINT `FK4v5bywebd4t5re33nng352gex` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`),
  CONSTRAINT `FKdf000fowj0jk3mplu8w4woyis` FOREIGN KEY (`pedido_id`) REFERENCES `pedidos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.productos
CREATE TABLE IF NOT EXISTS `productos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `medidas` varchar(255) DEFAULT NULL,
  `peso` varchar(255) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `cantidad_stock` int DEFAULT 0,
  `cantidad_vendidos` int DEFAULT 0,
  `min_cantidad_pedido` int DEFAULT 1,
  `max_cantidad_pedido` int DEFAULT 100000,
  `grupos_de` int DEFAULT 1,
  `umbral_poca_cantidad` int DEFAULT 1,
  `umbral_cantidad_agotada` int DEFAULT 0,
  `costo_unitario` decimal(10,2) DEFAULT 0.00,
  `impuesto_igv` decimal(10,2) DEFAULT 18.00,
  `activo` bit(1) DEFAULT NULL,
  `visible_en_tienda` bit(1) DEFAULT NULL,
  `create_at` date DEFAULT NULL,
  `update_at` date DEFAULT NULL,
  `fch_ult_real_compra` datetime(6) DEFAULT NULL,
  `fch_ult_real_venta` datetime(6) DEFAULT NULL,
  `categoria_id` bigint(20) DEFAULT NULL,
  `color_id` bigint(20) DEFAULT NULL,
  `estado_producto_id` bigint(20) DEFAULT NULL,
  `material_id` bigint(20) DEFAULT NULL,
  `uso_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2fwq10nwymfv7fumctxt9vpgb` (`categoria_id`),
  KEY `FKlyeilcq4d1w9b1x98ksp52aok` (`color_id`),
  KEY `FKb4x5cc4uv57i9j96rogvsnjwe` (`estado_producto_id`),
  KEY `FKg9lj4w1poric1wy2ob17g0lk3` (`material_id`),
  KEY `FKfhhnrhx6ymblhpm0sn788ett1` (`uso_id`),
  CONSTRAINT `FK2fwq10nwymfv7fumctxt9vpgb` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`),
  CONSTRAINT `FKb4x5cc4uv57i9j96rogvsnjwe` FOREIGN KEY (`estado_producto_id`) REFERENCES `estados_producto` (`id`),
  CONSTRAINT `FKfhhnrhx6ymblhpm0sn788ett1` FOREIGN KEY (`uso_id`) REFERENCES `usos` (`id`),
  CONSTRAINT `FKg9lj4w1poric1wy2ob17g0lk3` FOREIGN KEY (`material_id`) REFERENCES `materiales` (`id`),
  CONSTRAINT `FKlyeilcq4d1w9b1x98ksp52aok` FOREIGN KEY (`color_id`) REFERENCES `colores` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `modulo_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKldv0v52e0udsh2h1rs0r0gw1n` (`nombre`),
  KEY `FKroab8i9mo2yomtjd9smc7l9kh` (`modulo_id`),
  CONSTRAINT `FKroab8i9mo2yomtjd9smc7l9kh` FOREIGN KEY (`modulo_id`) REFERENCES `modulos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.tipo_documentos
CREATE TABLE IF NOT EXISTS `tipo_documentos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acronimo` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.tipo_movimientos_caja
CREATE TABLE IF NOT EXISTS `tipo_movimientos_caja` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `movimiento` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.tipo_movimientos_pedido
CREATE TABLE IF NOT EXISTS `tipo_movimientos_pedido` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `movimiento` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.tipo_pagos
CREATE TABLE IF NOT EXISTS `tipo_pagos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `cc` varchar(255) DEFAULT NULL,
  `cci` varchar(255) DEFAULT NULL,
  `moneda` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.tipo_pedido
CREATE TABLE IF NOT EXISTS `tipo_pedido` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.usos
CREATE TABLE IF NOT EXISTS `usos` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nom_apell_rz` varchar(255) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `activo` bit(1) DEFAULT NULL,
  `bloqueado` bit(1) DEFAULT NULL,
  `reintentos` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKm2dvbwfge291euvmk6vkkocao` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla db_ezra.usuarios_roles
CREATE TABLE IF NOT EXISTS `usuarios_roles` (
  `role_id` bigint(20) NOT NULL,
  `usuario_id` bigint(20) NOT NULL,
  UNIQUE KEY `UKqjaspm7473pnu9y4jxhrds8r2` (`usuario_id`,`role_id`),
  KEY `FKihom0uklpkfpffipxpoyf7b74` (`role_id`),
  CONSTRAINT `FKihom0uklpkfpffipxpoyf7b74` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKqcxu02bqipxpr7cjyj9dmhwec` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
