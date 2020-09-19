--Datos roles
insert into rol (id, nombre, descripcion)
values (1, 'ROLE_USER', 'Rol de usuario');
insert into rol (id, nombre, descripcion)
values (2, 'ROLE_ADMIN', 'Rol de administrador');
--Datos usuarios
insert into usuario (login, password)
values ('user', 'user');
insert into usuario (login, password)
values ('usr', 'usr');
insert into usuario (login, password)
values ('admin', 'admin');
insert into usuario (login, password)
values ('adm', 'adm');
--Datos roles usuarios
insert into usuario_roles (usuario_id, roles_id)
values (1, 1);
insert into usuario_roles (usuario_id, roles_id)
values (2, 1);
insert into usuario_roles (usuario_id, roles_id)
values (3, 2);
insert into usuario_roles (usuario_id, roles_id)
values (4, 2);
--Datos salas
insert into sala (nombre, responsable, fecha_de_reserva, tiempo_reserva_en_horas, icono) values ('Sala 1', 'Carlos C.', '2020-11-20 10:00:00', 2, 'https://cdn.pixabay.com/photo/2015/05/14/16/02/sandcastle-766949_960_720.jpg');
insert into sala (nombre, responsable, fecha_de_reserva, tiempo_reserva_en_horas, icono) values ('Sala 2', 'Mario D.', '2020-10-15 09:00:00', 2, 'https://cdn.pixabay.com/photo/2020/09/01/21/19/palace-5536801_960_720.jpg');
insert into sala (nombre, responsable, fecha_de_reserva, tiempo_reserva_en_horas, icono) values ('Sala 3', 'Daniela J.', '2020-11-15 14:00:00', 3, 'https://cdn.pixabay.com/photo/2020/08/28/06/13/building-5523630_960_720.jpg');
insert into sala (nombre, responsable, fecha_de_reserva, tiempo_reserva_en_horas, icono) values ('Sala 4', 'María P.', '2020-11-01 15:00:00', 3, 'https://cdn.pixabay.com/photo/2015/11/17/18/59/architecture-1048092_960_720.jpg');