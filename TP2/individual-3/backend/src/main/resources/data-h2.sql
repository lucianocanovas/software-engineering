MERGE INTO posicion (id, nombre) KEY(id) VALUES (1, 'Arquero');
MERGE INTO posicion (id, nombre) KEY(id) VALUES (2, 'Defensa');
MERGE INTO posicion (id, nombre) KEY(id) VALUES (3, 'Mediocampista');
MERGE INTO posicion (id, nombre) KEY(id) VALUES (4, 'Delantero');

MERGE INTO futbolista (id, nombres, apellidos, fecha_nacimiento, caracteristicas, posicion_id) KEY(id) VALUES 
(1, 'Juan', 'Pérez', '1990-01-01', 'Alto, rápido', 1),
(2, 'Carlos', 'Gómez', '1992-02-02', 'Fuerte, ágil', 2),
(3, 'Luis', 'Martínez', '1994-03-03', 'Inteligente, técnico', 3),
(4, 'Pedro', 'López', '1996-04-04', 'Resistente, hábil', 4),
(5, 'Jorge', 'Díaz', '1998-05-05', 'Rápido, fuerte', 1);
