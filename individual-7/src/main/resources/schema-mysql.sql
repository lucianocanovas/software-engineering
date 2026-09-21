-- =========================================================================================
-- ESQUEMA DDL MYSQL: club_deportivo_db
-- =========================================================================================
-- Definición formal de tablas relacionales con integridad referencial, llaves foráneas,
-- restricciones de unicidad, auditoría y relaciones de Herencia, Composición y Agregación.

CREATE DATABASE IF NOT EXISTS `club_deportivo_db` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `club_deportivo_db`;

-- Desactivar temporalmente verificación de claves foráneas para recreación segura
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `registros_acceso`;
DROP TABLE IF EXISTS `puntos_de_acceso`;
DROP TABLE IF EXISTS `actividad_inscriptos`;
DROP TABLE IF EXISTS `profesor_actividades`;
DROP TABLE IF EXISTS `actividades`;
DROP TABLE IF EXISTS `pagos_mercadopago`;
DROP TABLE IF EXISTS `pagos_transferencia`;
DROP TABLE IF EXISTS `pagos_efectivo`;
DROP TABLE IF EXISTS `pagos`;
DROP TABLE IF EXISTS `medios_pago`;
DROP TABLE IF EXISTS `cuotas`;
DROP TABLE IF EXISTS `familiares_adherentes`;
DROP TABLE IF EXISTS `grupos_familiares`;
DROP TABLE IF EXISTS `profesores`;
DROP TABLE IF EXISTS `empleados`;
DROP TABLE IF EXISTS `socios`;
DROP TABLE IF EXISTS `fotografias`;
DROP TABLE IF EXISTS `usuarios`;
DROP TABLE IF EXISTS `personas`;

-- 1. TABLA BASE PERSONAS (Herencia JOINED con campos de Auditoría)
CREATE TABLE `personas` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `dni` VARCHAR(20) NOT NULL UNIQUE,
    `nombre` VARCHAR(80) NOT NULL,
    `apellido` VARCHAR(80) NOT NULL,
    `fecha_nacimiento` DATE NOT NULL,
    `telefono` VARCHAR(30),
    `email` VARCHAR(100),
    `activo` BOOLEAN NOT NULL DEFAULT TRUE,
    `fotografia_id` BIGINT,
    `fecha_creacion` DATETIME NOT NULL,
    `fecha_modificacion` DATETIME,
    `creado_por` VARCHAR(80),
    `modificado_por` VARCHAR(80)
) ENGINE=InnoDB;

-- 2. COMPOSICIÓN: FOTOGRAFÍAS BIOMÉTRICAS (Ciclo de vida subordinado a Persona)
CREATE TABLE `fotografias` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `ruta_archivo` LONGTEXT NOT NULL,
    `formato` VARCHAR(30),
    `fecha_captura` DATETIME NOT NULL,
    `tamanio_bytes` BIGINT,
    `fecha_creacion` DATETIME NOT NULL,
    `fecha_modificacion` DATETIME,
    `creado_por` VARCHAR(80),
    `modificado_por` VARCHAR(80)
) ENGINE=InnoDB;

ALTER TABLE `personas` ADD CONSTRAINT `fk_persona_fotografia` FOREIGN KEY (`fotografia_id`) REFERENCES `fotografias` (`id`) ON DELETE CASCADE;

-- 3. ESPECIALIZACIÓN / HERENCIA: SOCIOS TITULARES
CREATE TABLE `socios` (
    `persona_id` BIGINT PRIMARY KEY,
    `numero_socio` VARCHAR(30) NOT NULL UNIQUE,
    `fecha_alta` DATE NOT NULL,
    `categoria` VARCHAR(30) NOT NULL,
    CONSTRAINT `fk_socio_persona` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 4. AGREGACIÓN: GRUPOS FAMILIARES (El socio titular encabeza el grupo)
CREATE TABLE `grupos_familiares` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `codigo` VARCHAR(40) NOT NULL UNIQUE,
    `fecha_constitucion` DATE NOT NULL,
    `observaciones` VARCHAR(255),
    `socio_titular_id` BIGINT NOT NULL UNIQUE,
    `fecha_creacion` DATETIME NOT NULL,
    `fecha_modificacion` DATETIME,
    `creado_por` VARCHAR(80),
    `modificado_por` VARCHAR(80),
    CONSTRAINT `fk_grupo_socio_titular` FOREIGN KEY (`socio_titular_id`) REFERENCES `socios` (`persona_id`) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 5. ESPECIALIZACIÓN / AGREGACIÓN: FAMILIARES ADHERENTES
CREATE TABLE `familiares_adherentes` (
    `persona_id` BIGINT PRIMARY KEY,
    `parentesco` VARCHAR(30) NOT NULL,
    `grupoFamiliar_id` BIGINT,
    CONSTRAINT `fk_adherente_persona` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_adherente_grupo` FOREIGN KEY (`grupoFamiliar_id`) REFERENCES `grupos_familiares` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB;

-- 6. ESPECIALIZACIÓN / HERENCIA: EMPLEADOS
CREATE TABLE `empleados` (
    `persona_id` BIGINT PRIMARY KEY,
    `legajo` VARCHAR(30) NOT NULL UNIQUE,
    `cargo` VARCHAR(80) NOT NULL,
    `fecha_ingreso` DATE NOT NULL,
    CONSTRAINT `fk_empleado_persona` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 7. ESPECIALIZACIÓN / HERENCIA: PROFESORES
CREATE TABLE `profesores` (
    `persona_id` BIGINT PRIMARY KEY,
    `especialidad` VARCHAR(80) NOT NULL,
    `matricula` VARCHAR(40) NOT NULL UNIQUE,
    CONSTRAINT `fk_profesor_empleado` FOREIGN KEY (`persona_id`) REFERENCES `empleados` (`persona_id`) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 8. ACTIVIDADES DEPORTIVAS Y RELACIÓN CON PROFESORES (AGREGACIÓN)
CREATE TABLE `actividades` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `codigo` VARCHAR(30) NOT NULL UNIQUE,
    `nombre` VARCHAR(100) NOT NULL,
    `dias_horarios` VARCHAR(150) NOT NULL,
    `cupo_maximo` INT NOT NULL,
    `fecha_creacion` DATETIME NOT NULL,
    `fecha_modificacion` DATETIME,
    `creado_por` VARCHAR(80),
    `modificado_por` VARCHAR(80)
) ENGINE=InnoDB;

CREATE TABLE `profesor_actividades` (
    `profesor_id` BIGINT NOT NULL,
    `actividad_id` BIGINT NOT NULL,
    PRIMARY KEY (`profesor_id`, `actividad_id`),
    CONSTRAINT `fk_pa_profesor` FOREIGN KEY (`profesor_id`) REFERENCES `profesores` (`persona_id`) ON DELETE CASCADE,
    CONSTRAINT `fk_pa_actividad` FOREIGN KEY (`actividad_id`) REFERENCES `actividades` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `actividad_inscriptos` (
    `actividad_id` BIGINT NOT NULL,
    `persona_id` BIGINT NOT NULL,
    PRIMARY KEY (`actividad_id`, `persona_id`),
    CONSTRAINT `fk_ai_actividad` FOREIGN KEY (`actividad_id`) REFERENCES `actividades` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_ai_persona` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 9. PUNTOS DE ACCESO FÍSICOS (MOLINETES)
CREATE TABLE `puntos_de_acceso` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(80) NOT NULL,
    `ubicacion` VARCHAR(120) NOT NULL,
    `estado` VARCHAR(30) NOT NULL,
    `ip_lector` VARCHAR(45),
    `fecha_creacion` DATETIME NOT NULL,
    `fecha_modificacion` DATETIME,
    `creado_por` VARCHAR(80),
    `modificado_por` VARCHAR(80)
) ENGINE=InnoDB;

-- 10. ASOCIACIÓN: REGISTROS DE ACCESO
CREATE TABLE `registros_acceso` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `fecha_hora_entrada` DATETIME,
    `fecha_hora_salida` DATETIME,
    `tipo_movimiento` VARCHAR(20) NOT NULL,
    `observacion` VARCHAR(255),
    `persona_id` BIGINT NOT NULL,
    `punto_de_acceso_id` BIGINT NOT NULL,
    `fecha_creacion` DATETIME NOT NULL,
    `fecha_modificacion` DATETIME,
    `creado_por` VARCHAR(80),
    `modificado_por` VARCHAR(80),
    CONSTRAINT `fk_ra_persona` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_ra_punto` FOREIGN KEY (`punto_de_acceso_id`) REFERENCES `puntos_de_acceso` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 11. CUOTAS SOCIALES MENSUALES FAMILIARES
CREATE TABLE `cuotas` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `periodo` VARCHAR(30) NOT NULL,
    `monto_base` DECIMAL(10,2) NOT NULL,
    `monto_recargo` DECIMAL(10,2) DEFAULT 0.00,
    `fecha_vencimiento` DATE NOT NULL,
    `fecha_pago` DATE,
    `estado` VARCHAR(30) NOT NULL DEFAULT 'PENDIENTE',
    `socio_titular_id` BIGINT NOT NULL,
    `grupo_familiar_id` BIGINT,
    `fecha_creacion` DATETIME NOT NULL,
    `fecha_modificacion` DATETIME,
    `creado_por` VARCHAR(80),
    `modificado_por` VARCHAR(80),
    CONSTRAINT `fk_cuota_socio` FOREIGN KEY (`socio_titular_id`) REFERENCES `socios` (`persona_id`) ON DELETE CASCADE,
    CONSTRAINT `fk_cuota_grupo` FOREIGN KEY (`grupo_familiar_id`) REFERENCES `grupos_familiares` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB;

-- 12. HERENCIA Y POLIMORFISMO: MEDIOS DE PAGO (Efectivo, Transferencia, Mercado Pago)
CREATE TABLE `medios_pago` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `tipo_medio_pago` VARCHAR(30) NOT NULL,
    `fecha_creacion` DATETIME NOT NULL,
    `fecha_modificacion` DATETIME,
    `creado_por` VARCHAR(80),
    `modificado_por` VARCHAR(80)
) ENGINE=InnoDB;

CREATE TABLE `pagos_efectivo` (
    `medio_pago_id` BIGINT PRIMARY KEY,
    `descuento_aplicado` DECIMAL(10,2) DEFAULT 0.00,
    `numero_caja` VARCHAR(20) NOT NULL,
    `cajero_responsable` VARCHAR(80) NOT NULL,
    CONSTRAINT `fk_pe_medio` FOREIGN KEY (`medio_pago_id`) REFERENCES `medios_pago` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `pagos_transferencia` (
    `medio_pago_id` BIGINT PRIMARY KEY,
    `cbu_origen` VARCHAR(30),
    `cbu_destino` VARCHAR(30) NOT NULL,
    `banco_origen` VARCHAR(80),
    `numero_operacion` VARCHAR(60) NOT NULL,
    `comprobante_hash` VARCHAR(100),
    CONSTRAINT `fk_pt_medio` FOREIGN KEY (`medio_pago_id`) REFERENCES `medios_pago` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `pagos_mercadopago` (
    `medio_pago_id` BIGINT PRIMARY KEY,
    `mp_payment_id` VARCHAR(60) NOT NULL,
    `mp_preference_id` VARCHAR(80),
    `qr_code_url` VARCHAR(255),
    `status_detail` VARCHAR(80),
    CONSTRAINT `fk_pmp_medio` FOREIGN KEY (`medio_pago_id`) REFERENCES `medios_pago` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 13. COMPOSICIÓN: PAGOS Y RECIBOS OFICIALES
CREATE TABLE `pagos` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `numero_recibo` VARCHAR(40) NOT NULL UNIQUE,
    `fecha_pago` DATETIME NOT NULL,
    `monto_abonado` DECIMAL(10,2) NOT NULL,
    `estado` VARCHAR(30) NOT NULL DEFAULT 'COMPLETADO',
    `notas` VARCHAR(255),
    `cuota_id` BIGINT NOT NULL UNIQUE,
    `medio_pago_id` BIGINT NOT NULL UNIQUE,
    `socio_id` BIGINT NOT NULL,
    `fecha_creacion` DATETIME NOT NULL,
    `fecha_modificacion` DATETIME,
    `creado_por` VARCHAR(80),
    `modificado_por` VARCHAR(80),
    CONSTRAINT `fk_pago_cuota` FOREIGN KEY (`cuota_id`) REFERENCES `cuotas` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_pago_medio` FOREIGN KEY (`medio_pago_id`) REFERENCES `medios_pago` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_pago_socio` FOREIGN KEY (`socio_id`) REFERENCES `socios` (`persona_id`) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 14. SEGURIDAD: USUARIOS Y CREDENCIALES RBAC
CREATE TABLE `usuarios` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(60) NOT NULL UNIQUE,
    `password` VARCHAR(120) NOT NULL,
    `nombre_completo` VARCHAR(100) NOT NULL,
    `rol` VARCHAR(30) NOT NULL DEFAULT 'ROLE_OPERADOR',
    `activo` BOOLEAN NOT NULL DEFAULT TRUE,
    `socio_id` BIGINT,
    `fecha_creacion` DATETIME NOT NULL,
    `fecha_modificacion` DATETIME,
    `creado_por` VARCHAR(80),
    `modificado_por` VARCHAR(80),
    CONSTRAINT `fk_usuario_socio` FOREIGN KEY (`socio_id`) REFERENCES `socios` (`persona_id`) ON DELETE SET NULL
) ENGINE=InnoDB;

SET FOREIGN_KEY_CHECKS = 1;

