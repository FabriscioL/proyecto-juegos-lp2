-- Script de Creación y Carga de Datos Iniciales para JUEGOS.NET
CREATE DATABASE IF NOT EXISTS db_juegos_net;
USE db_juegos_net;

CREATE TABLE IF NOT EXISTS categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS proveedor (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(100),
    telefono VARCHAR(15),
    correo VARCHAR(100),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS producto (
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

CREATE TABLE IF NOT EXISTS cliente (
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

CREATE TABLE IF NOT EXISTS venta (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    fecha_venta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    id_cliente INT,
    CONSTRAINT fk_venta_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

CREATE TABLE IF NOT EXISTS detalle_venta (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT,
    id_producto INT,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detalle_venta FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
    CONSTRAINT fk_detalle_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- 1. CATEGORÍAS
INSERT INTO categoria (id_categoria, nombre) VALUES  
(1, 'Acción y Aventura'),
(2, 'RPG y Mundo Abierto'),
(3, 'Shooter / FPS'),
(4, 'Deportes y Carreras'),
(5, 'Estrategia')
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

-- 2. PROVEEDORES (10 empresas oficiales del sector gaming)
INSERT INTO proveedor (id_proveedor, nombre, contacto, telefono, correo, activo) VALUES
(1, 'Sony Interactive Entertainment', 'Carlos Mendoza', '987654321', 'ventas.pe@sony.com', true),
(2, 'Microsoft Xbox Store Perú', 'Lucía Fernández', '987112233', 'comercial@xbox.pe', true),
(3, 'Nintendo Latinoamérica Distribución', 'Mario Paredes', '998877665', 'contacto@nintendo.la', true),
(4, 'Electronic Arts (EA) Perú', 'Gabriel Soto', '976543210', 'distribucion@ea.pe', true),
(5, 'Ubisoft Latino Digital', 'Valeria Rojas', '965432187', 'partners@ubisoft.la', true),
(6, 'Capcom Co. Latam', 'Kenji Tanaka', '954321876', 'ventas@capcomlatam.com', true),
(7, 'Bandai Namco Entertainment', 'Renato Quispe', '943218765', 'contacto@bandainamco.pe', true),
(8, 'CD Projekt Red Mayorista', 'Elena Morales', '932187654', 'distribuidor@cdprojekt.com', true),
(9, 'Blizzard Activision Gaming', 'Martín Silva', '921876543', 'soporte.retail@blizzard.com', true),
(10, 'Valve Steam Distribution S.A.C.', 'Andrés Castro', '910987654', 'mayoristas@steampartner.pe', true)
ON DUPLICATE KEY UPDATE 
    nombre = VALUES(nombre),
    contacto = VALUES(contacto),
    telefono = VALUES(telefono),
    correo = VALUES(correo),
    activo = VALUES(activo);

-- 3. CATÁLOGO COMPLETO DE VIDEOJUEGOS (25 títulos correspondientes a cada imagen)
INSERT INTO producto (id_producto, nombre, descripcion, precio, stock, imagen, id_categoria, activo) VALUES
(1, 'Once Human', 'Videojuego multijugador de supervivencia postapocalíptica en un mundo abierto alienígena.', 0.00, 150, 'Once Human.jpg', 2, true),
(2, 'Call of Duty: Modern Warfare III', 'Shooter en primera persona de combate táctico militar, incluye multijugador y modo Zombis.', 249.90, 45, 'Call of Duty Modern Warfare III.jpg', 3, true),
(3, 'God of War Ragnarök', 'Aventura nórdica épica de Kratos y Atreus enfrentando los nueve reinos y el fin del mundo.', 229.00, 30, 'God of War Ragnarök.jpg', 1, true),
(4, 'EA Sports FC 25', 'El simulador definitivo de fútbol con tecnología Hypermotion, Ultimate Team y ligas oficiales.', 259.90, 60, 'EA Sports FC 25.jpg', 4, true),
(5, 'Cyberpunk 2077: Ultimate Edition', 'RPG de acción futurista en Night City con la expansión Phantom Liberty y mejoras gráficas.', 169.90, 35, 'Cyberpunk 2077 Ultimate Edition.jpg', 2, true),
(6, 'Grand Theft Auto V', 'Aclamado juego de acción y crimen en mundo abierto recorriendo Los Santos y Blaine County.', 89.90, 80, 'Grand Theft Auto V.jpg', 1, true),
(7, 'Helldivers 2', 'Intenso shooter cooperativo galáctico en tercera persona para defender la Supertierra.', 149.00, 40, 'Helldivers 2.jpg', 3, true),
(8, 'Civilization VI', 'Juego legendario de estrategia por turnos para construir un imperio duradero a través de la historia.', 79.90, 25, 'Civilization VI.jpg', 5, true),
(9, 'Counter-Strike 2 - Prime Status', 'Acceso al matchmaking premium, objetos exclusivos y cajas semanales del shooter competitivo #1.', 54.90, 120, 'Counter-Strike 2 - Prime Status.jpg', 3, true),
(10, 'Elden Ring', 'Aclamado RPG de acción y fantasía oscura de FromSoftware y George R. R. Martin en las Tierras Intermedias.', 199.90, 32, 'Elden Ring.jpg', 2, true),
(11, 'FC 24 - Ultimate Edition', 'Entrega futbolística con FIFA Points, licencias oficiales europeas y acceso al modo Clubes.', 139.90, 20, 'FC 24 - Ultimate Edition.jpg', 4, true),
(12, 'Forza Horizon 5', 'Espectacular festival automovilístico en los vibrantes y diversos paisajes de México.', 189.90, 28, 'Forza Horizon 5.jpg', 4, true),
(13, 'Gran Turismo 7', 'El simulador de conducción real para fanáticos de los autos deportivos con física realista.', 219.00, 22, 'Gran Turismo 7-.jpg', 4, true),
(14, 'Marvels Spider-Man 2', 'Acción vertiginosa con Peter Parker y Miles Morales enfrentando a Venom y Kraven el Cazador.', 269.00, 38, 'Marvels Spider-Man 2-.jpg', 1, true),
(15, 'Minecraft', 'Construye, explora y sobrevive en un universo infinito de bloques con infinitas posibilidades.', 89.00, 95, 'Minecraft.jpg', 1, true),
(16, 'Monster Hunter Wilds', 'Próxima entrega de caza extrema en ecosistemas vivos dinámicos con clima cambiante y bestias colosales.', 279.90, 42, 'Monster Hunter Wilds-.jpg', 2, true),
(17, 'Overwatch 2 - Hero Pack', 'Pack de bienvenida con acceso instantáneo a héroes selectos, créditos del juego y skins épicas.', 69.90, 50, 'Overwatch 2 - Hero Pack.jpg', 3, true),
(18, 'Red Dead Redemption 2', 'Obra maestra de forajidos del salvaje oeste con la historia inolvidable de Arthur Morgan.', 129.90, 48, 'Red Dead Redemption 2.jpg', 1, true),
(19, 'Resident Evil 4 Remake', 'Survival horror de acción reimaginado donde el agente Leon S. Kennedy rescata a la hija del presidente.', 159.00, 26, 'Resident Evil 4 Remake.jpg', 1, true),
(20, 'Starcraft II Campaign Collection', 'La trilogía completa de estrategia en tiempo real de ciencia ficción: Terran, Zerg y Protoss.', 119.00, 18, 'Starcraft II Campaign Collection.jpg', 5, true),
(21, 'Street Fighter 6', 'El referente de los juegos de pelea arcade con World Tour, Battle Hub y Fighting Ground.', 189.00, 24, 'Street Fighter 6-.jpg', 1, true),
(22, 'Super Mario Bros. Wonder', 'Aventura en plataformas 2D con flores maravilla que transforman los mundos del Reino Flor.', 229.00, 36, 'Super Mario Bros. Wonder-.jpg', 1, true),
(23, 'The Last of Us Part I', 'Experiencia emocional y cinemática en un mundo devastado acompañando a Joel y Ellie.', 239.00, 29, 'The Last of Us Part I.jpg', 1, true),
(24, 'The Witcher 3: Wild Hunt', 'Aventura de rol en mundo abierto como Geralt de Rivia en busca de la Niña de la Profecía.', 79.90, 65, 'The Witcher 3 Wild Hunt.jpg', 2, true),
(25, 'Valorant - Points Pack', 'Moneda premium virtual para desbloquear el pase de batalla, aspectos de armas y agentes.', 49.90, 110, 'Valorant - Points Pack.jpg', 3, true)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    precio = VALUES(precio),
    stock = VALUES(stock),
    imagen = VALUES(imagen),
    id_categoria = VALUES(id_categoria),
    activo = VALUES(activo);

-- 4. USUARIOS (Administradores, Vendedores y Clientes para pruebas)
-- Use the unique email instead of fixed IDs so existing IDs cannot redirect an upsert to another account.
INSERT INTO cliente (nombres, apellidos, correo, password, telefono, direccion, rol, activo) VALUES
('Administrador', 'Principal', 'admin@juegos.net', '12345', '999999999', 'Oficina Central Juegos.Net, Lima', 'ADMIN', true),
('Vendedor', 'Uno', 'vendedor1@juegos.net', '12345', '988888888', 'Tienda Miraflores, Lima', 'VENDEDOR', true),
('Vendedora', 'Camila Torres', 'vendedor2@juegos.net', '12345', '987777777', 'Tienda San Isidro, Lima', 'VENDEDOR', true),
('Cristofer', 'Labán Guerrero', 'cristofer@gmail.com', '123456', '987654321', 'Av. Argentina 123, Lima', 'CLIENTE', true),
('Valentino', 'Melendez Ninancuro', 'valentino@gmail.com', '123456', '912345678', 'Jr. Los Rosales 456, Lima', 'CLIENTE', true),
('Fabriscio', 'Lopez Cusipuma', 'fabricio.m@gmail.com', '123456', '923456321', 'Calle Las Begonias 789, San Isidro', 'CLIENTE', true),
('Juan', 'Huatuco Rojas', 'juan.m@gmail.com', '123456', '923456123', 'Calle Las Palmeras 789, Rímac', 'CLIENTE', true),
('Diego', 'Alvarez Castro', 'diego.a@gmail.com', '123456', '934567891', 'Av. Javier Prado 2040, San Borja', 'CLIENTE', true),
('Mariana', 'Gutiérrez Peña', 'mariana.g@gmail.com', '123456', '945678912', 'Jr. Ayacucho 312, Surco', 'CLIENTE', true),
('Rodrigo', 'Benavides Lara', 'rodrigo.b@gmail.com', '123456', '956789123', 'Av. Universitaria 1500, San Miguel', 'CLIENTE', true),
('Andrea', 'Palacios Romero', 'andrea.p@gmail.com', '123456', '967891234', 'Calle Los Sauces 450, Magdalena', 'CLIENTE', true),
('Sebastián', 'Vargas Salazar', 'sebastian.v@gmail.com', '123456', '978912345', 'Av. La Marina 2500, Pueblo Libre', 'CLIENTE', true),
('Luciana', 'Mendoza Chávez', 'luciana.m@gmail.com', '123456', '989012345', 'Calle Berlín 820, Miraflores', 'CLIENTE', true),
('Carlos', 'Espinoza Navarro', 'carlos.e@gmail.com', '123456', '990123456', 'Av. Salaverry 1120, Jesús María', 'CLIENTE', true),
('Valeria', 'Quispe Huamán', 'valeria.q@gmail.com', '123456', '911234567', 'Jr. Huancavelica 540, Cercado de Lima', 'CLIENTE', true)
ON DUPLICATE KEY UPDATE
    nombres = VALUES(nombres),
    apellidos = VALUES(apellidos),
    password = VALUES(password),
    telefono = VALUES(telefono),
    direccion = VALUES(direccion),
    rol = VALUES(rol),
    activo = VALUES(activo);

-- 5. HISTORIAL DE VENTAS Y DETALLE
INSERT INTO venta (id_venta, fecha_venta, total, id_cliente) VALUES  
(1, '2026-10-01 14:30:00', 359.89, (SELECT id_cliente FROM cliente WHERE correo = 'cristofer@gmail.com')),
(2, '2026-10-05 18:15:00', 249.99, (SELECT id_cliente FROM cliente WHERE correo = 'valentino@gmail.com')),
(3, '2026-10-10 20:00:00', 229.00, (SELECT id_cliente FROM cliente WHERE correo = 'fabricio.m@gmail.com'))
ON DUPLICATE KEY UPDATE total = VALUES(total);

INSERT INTO detalle_venta (id_detalle, id_venta, id_producto, cantidad, precio_unitario, subtotal) VALUES  
(1, 1, 2, 1, 199.99, 199.99),
(2, 1, 5, 1, 159.90, 159.90),
(3, 2, 4, 1, 249.99, 249.99),
(4, 3, 3, 1, 229.00, 229.00)
ON DUPLICATE KEY UPDATE subtotal = VALUES(subtotal);
