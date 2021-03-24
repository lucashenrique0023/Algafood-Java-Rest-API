create table restaurante_usuario_responsavel (
	restaurante_id bigint not null,
	usuario_id bigint not null,
	
	primary key (restaurante_id, usuario_id)
) engine=InnoDB default charset=utf8;