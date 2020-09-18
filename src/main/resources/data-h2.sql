--Datos roles
-- TBD
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
--Datos generos
insert into genero (nombre) values ('Sci-Fi');
insert into genero (nombre) values ('Aventura');
insert into genero (nombre) values ('Comedia');
insert into genero (nombre) values ('Drama');
insert into genero (nombre) values ('Infantil');
insert into genero (nombre) values ('Acción');
--Datos peliculas
insert into pelicula (director,titulo,fecha_estreno,poster,genero_id)
values ('Christopher Nolan','Interestelar','2014-11-7','http://t2.gstatic.com/images?q=tbn:ANd9GcQMHMl9U1z1txXWCBgKbSlwH0tV3wVIsxyd6CQLhR0CkgC8Nagf',1);
insert into pelicula (director,titulo,fecha_estreno,poster,genero_id)
values ('Josh Cooley','Toy Story 4','2019-06-11','https://as.com/meristation/imagenes/2019/03/19/noticias/1553025770_364735_1553025920_sumario_normal.jpg',5);
insert into pelicula (director,titulo,fecha_estreno,poster,genero_id)
values ('Guy Ritchie','Aladin','2019-05-08','https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTBurfE1onvBc_zbSzJArQXzpNgPGyGNpt8gpqqjhAOCFTLreLn',3);