INSERT INTO origenes (nombre, vacio) VALUES ("americano", FALSE);
INSERT INTO origenes (nombre, vacio) VALUES ("europeo", FALSE);

INSERT INTO paises (nombre, origen_id, vacio) VALUES ("Alemania", 2, FALSE);
INSERT INTO paises (nombre, origen_id, vacio) VALUES ("España", 2, FALSE);
INSERT INTO paises (nombre, origen_id, vacio) VALUES ("Francia", 2, FALSE);
INSERT INTO paises (nombre, origen_id, vacio) VALUES ("Italia", 2, FALSE);
INSERT INTO paises (nombre, origen_id, vacio) VALUES ("USA", 1, FALSE);

INSERT INTO marcas (nombre, pais_id, vacia) VALUES ("Chevrolet", 5, FALSE);
INSERT INTO marcas (nombre, pais_id, vacia) VALUES ("Citroën", 3, TRUE);
INSERT INTO marcas (nombre, pais_id, vacia) VALUES ("Delorean", 5, FALSE);
INSERT INTO marcas (nombre, pais_id, vacia) VALUES ("Dodge", 5, FALSE);
INSERT INTO marcas (nombre, pais_id, vacia) VALUES ("Ferrari", 4, FALSE);
INSERT INTO marcas (nombre, pais_id, vacia) VALUES ("Ford", 5, FALSE);
INSERT INTO marcas (nombre, pais_id, vacia) VALUES ("Lamborghini", 4, TRUE);
INSERT INTO marcas (nombre, pais_id, vacia) VALUES ("Seat", 2, FALSE);
INSERT INTO marcas (nombre, pais_id, vacia) VALUES ("Shelby", 5, FALSE);
INSERT INTO marcas (nombre, pais_id, vacia) VALUES ("Volkswagen", 1, true);

INSERT INTO usuarios (nombre_usuario, contrasena, correo, foto, habilitado) VALUES('rober', '$2a$10$Dic96EQSyy4fTXzgkv70NOTuY0m.K4ZP5VUvjGKeh./I2PDbK4tH6', 'rober@correo.com', '', 1);
INSERT INTO usuarios (nombre_usuario, contrasena, correo, foto, habilitado) VALUES('admin', '$2a$10$dYX9GVBM2k2/MBER7raDB.u0nZo3AgzTsm0G.AzHy2mOq6tyOZHLW', 'admin@correo.com', '', 1);
INSERT INTO usuarios (nombre_usuario, contrasena, correo, foto, habilitado) VALUES('armando', '$2a$10$dYX9GVBM2k2/MBER7raDB.u0nZo3AgzTsm0G.AzHy2mOq6tyOZHLW', 'armando@correo.com', '', 1);
INSERT INTO usuarios (nombre_usuario, contrasena, correo, foto, habilitado) VALUES('benito', '$2a$10$dYX9GVBM2k2/MBER7raDB.u0nZo3AgzTsm0G.AzHy2mOq6tyOZHLW', 'benito@correo.com', '', 1);
INSERT INTO usuarios (nombre_usuario, contrasena, correo, foto, habilitado) VALUES('carmelo', '$2a$10$dYX9GVBM2k2/MBER7raDB.u0nZo3AgzTsm0G.AzHy2mOq6tyOZHLW', 'carmelo@correo.com', '', 1);

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO clasicos (nombre, marca_id, anio, foto, aprobado, eliminado, usuario_id) VALUES ("Mustang Fastback", 6, 1967, "Ford_Mustang_Fastback_1967.jpg",TRUE,FALSE, 2);
INSERT INTO clasicos (nombre, marca_id, anio, foto, aprobado, eliminado, usuario_id) VALUES ("Bel Air", 1, 1957, "Chevrolet_Bel_Air_1957.jpeg",TRUE,FALSE, 3);
INSERT INTO clasicos (nombre, marca_id, anio, foto, aprobado, eliminado, usuario_id) VALUES ("Cobra 427", 9, 1966, "Shelby_Cobra_ 427_1966.jpg",FALSE,TRUE, 3);
INSERT INTO clasicos (nombre, marca_id, anio, foto, aprobado, eliminado, usuario_id) VALUES ("Mercury", 6, 1951, "Ford_Mercury_1951.jpg",TRUE,FALSE, 4);
INSERT INTO clasicos (nombre, marca_id, anio, foto, aprobado, eliminado, usuario_id) VALUES ("Fairlane 500 Skyliner", 6, 1957, "Ford_Fairlane_500_Skyliner_1957.jpg",FALSE,FALSE, 5);
INSERT INTO clasicos (nombre, marca_id, anio, foto, aprobado, eliminado, usuario_id) VALUES ("Gran Torino", 6, 1975, "Ford_Gran_Torino_1975.JPG",TRUE,TRUE, 5);
INSERT INTO clasicos (nombre, marca_id, anio, foto, aprobado, eliminado, usuario_id) VALUES ("Charger 500", 4, 1970, "Dodge_Charger_1970.jpg",FALSE,FALSE, 3);
INSERT INTO clasicos (nombre, marca_id, anio, foto, aprobado, eliminado, usuario_id) VALUES ("DMC-12", 3, 1981, "Delorean_DMC-12_1981.jpeg",FALSE,FALSE, 4);
INSERT INTO clasicos (nombre, marca_id, anio, foto, aprobado, eliminado, usuario_id) VALUES ("600", 8, 1957, "Seat_600.jpg",TRUE,FALSE, 1);
INSERT INTO clasicos (nombre, marca_id, anio, foto, aprobado, eliminado, usuario_id) VALUES ("Testarossa", 5, 1984, "Ferrari_Testarossa_1984.jpg",TRUE,FALSE, 1);

USE automoviles_clasicos;
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (3, 1);
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (4, 1);
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (5, 1);

