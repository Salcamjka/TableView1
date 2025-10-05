CREATE TABLE IF NOT EXISTS personas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL
);


INSERT INTO personas (nombre, apellido, fecha_nacimiento) VALUES
('Ashwin', 'Sharan', '2012-10-11'),
('Advik', 'Sharan', '2012-10-11'),
('Layne', 'Estes', '2011-12-16'),
('Mason', 'Boyd', '2003-04-20'),
('Bahar', 'Sharan', '2001-07-10');