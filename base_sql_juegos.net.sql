CREATE DATABASE IF NOT EXISTS db_juegos_net;
USE db_juegos_net;

CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE proveedor (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(100),
    telefono VARCHAR(15),
    correo VARCHAR(100),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    imagen VARCHAR(255),
    id_categoria INT,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(80) NOT NULL,
    apellidos VARCHAR(80) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(150),
    rol VARCHAR(20) NOT NULL DEFAULT 'CLIENTE',
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE venta (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    fecha_venta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    id_cliente INT,
    CONSTRAINT fk_venta_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

CREATE TABLE detalle_venta (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT,
    id_producto INT,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detalle_venta FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
    CONSTRAINT fk_detalle_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

INSERT INTO categoria (id_categoria, nombre) VALUES  
(1, 'Acción y Aventura'),
(2, 'RPG y Mundo Abierto'),
(3, 'Shooter / FPS'),
(4, 'Deportes y Carreras'),
(5, 'Estrategia');

INSERT INTO proveedor (id_proveedor, nombre, contacto, telefono, correo, activo) VALUES
(1, 'Sony Interactive Entertainment', 'Atención Mayorista', '987654321', 'ventas@sony.pe', true),
(2, 'Microsoft Xbox Store', 'Soporte Comercial', '987112233', 'comercial@xbox.pe', true),
(3, 'Nintendo Latinoamérica', 'Distribución Retail', '998877665', 'contacto@nintendo.la', true);

INSERT INTO producto (nombre, descripcion, precio, stock, imagen, id_categoria, activo) VALUES  
('Once Human', 'Videojuego de supervivencia de mundo abierto en un futuro postapocalíptico.', 0.00, 500, 'Once Human.jpg', 2, true),
('Call of Duty: Modern Warfare III', 'Juego de disparos en primera persona con modos campaña y multijugador.', 199.99, 45, 'Call of Duty Modern Warfare III.jpg', 3, true),
('God of War Ragnarök', 'Aventura épica de Kratos y Atreus en la mitología nórdica.', 229.00, 30, 'God of War Ragnarök.jpg', 1, true),
('EA Sports FC 25', 'El simulador de fútbol más realista con licencias oficiales.', 249.99, 60, 'EA Sports FC 25.jpg', 4, true),
('Cyberpunk 2077: Ultimate Edition', 'RPG de acción futurista ambientado en Night City.', 159.90, 25, 'Cyberpunk 2077 Ultimate Edition.jpg', 2, true),
('Grand Theft Auto V', 'Mundo abierto de acción y crimen en Los Santos.', 89.90, 80, 'Grand Theft Auto V.jpg', 1, true),
('Helldivers 2', 'Shooter cooperativo galáctico para erradicar amenazas alienígenas.', 139.00, 40, 'Helldivers 2.jpg', 3, true);

INSERT INTO cliente (nombres, apellidos, correo, password, telefono, direccion, rol, activo) VALUES  
('Administrador', 'Sistema', 'admin@juegos.net', '12345', '999999999', 'Oficina Central', 'ADMIN', true),
('Vendedor', 'Uno', 'vendedor1@juegos.net', '12345', '988888888', 'Tienda Principal', 'VENDEDOR', true),
('Cristofer', 'Labán Guerrero', 'cristofer@gmail.com', '123456', '987654321', 'Av. Argentina 123, Lima', 'CLIENTE', true),
('Valentino', 'Melendez Ninancuro', 'valentino@gmail.com', '123456', '912345678', 'Jr. Los Rosales 456, Lima', 'CLIENTE', true),
('Fabriscio', 'Lopez Cusipuma', 'fabricio.m@gmail.com', '123456', '923456321', 'Calle Las Begonias 789, San Isidro', 'CLIENTE', true),
('Juan', 'Huatuco Jojas', 'juan.m@gmail.com', '123456', '923456123', 'Calle Las Palmeras 789, Rimac', 'CLIENTE', true);

INSERT INTO venta (id_venta, fecha_venta, total, id_cliente) VALUES  
(1, '2026-10-01 14:30:00', 359.89, 3),
(2, '2026-10-05 18:15:00', 249.99, 4),
(3, '2026-10-10 20:00:00', 229.00, 5);

INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, subtotal) VALUES  
(1, 2, 1, 199.99, 199.99),
(1, 5, 1, 159.90, 159.90),
(2, 4, 1, 249.99, 249.99),
(3, 3, 1, 229.00, 229.00);

SELECT * FROM categoria;
SELECT * FROM proveedor;
SELECT * FROM producto;
SELECT * FROM cliente;
