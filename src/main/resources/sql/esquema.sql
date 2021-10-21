CREATE DATABASE dbViajes;

USE dbViajes;

CREATE TABLE cliente (
	id_cliente integer NOT NULL AUTO_INCREMENT,
	id_viaje integer NOT NULL,
	nombres text NOT NULL,
	apellidos text NOT NULL,
	fecha_nacimiento timestamp NOT NULL,
	CONSTRAINT cliente_pk PRIMARY KEY (id_cliente)

);

CREATE TABLE empleado (
	id_empleado integer NOT NULL AUTO_INCREMENT,
	nombres text NOT NULL,
	apellidos text NOT NULL,
	fecha_nacimiento timestamp NOT NULL,
	correo text NOT NULL,
	password text NOT NULL,
	admin boolean NOT NULL,
	CONSTRAINT usuario_pk PRIMARY KEY (id_empleado)

);

CREATE TABLE viaje (
	id_viaje integer NOT NULL AUTO_INCREMENT,
	nombre_viaje text NOT NULL,
	nombre_destino text NOT NULL,
	fecha_viaje timestamp NOT NULL,
	precio decimal(5,2) NOT NULL,
	CONSTRAINT viaje_pk PRIMARY KEY (id_viaje)

);


ALTER TABLE cliente ADD CONSTRAINT viaje_fk FOREIGN KEY (id_viaje)
REFERENCES viaje (id_viaje) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION;

SET NAMES utf8mb4;
INSERT INTO `empleado` (`nombres`, `apellidos`, `fecha_nacimiento`, `correo`, `password`, `admin`) VALUES
('Admin',	'System',	'2021-10-20 22:57:59',	'admin@system.com',	'admin',	1);
