create table chamado_comentario
(
	chamado_id integer not null
		constraint chamado_comentario_chamado_id_fk
			references chamado
				on update cascade on delete cascade,
	usuario_id integer not null
		constraint chamado_comentario_usuario_id_fk
			references usuario
				on update cascade on delete cascade,
	comentario varchar(300) not null,
	dat_comentario timestamp not null
);

alter table chamado_comentario owner to postgres;

