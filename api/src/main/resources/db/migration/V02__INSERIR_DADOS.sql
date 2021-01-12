--instalar
CREATE EXTENSION fuzzystrmatch;
CREATE EXTENSION unaccent;

-- INSERIR DADOS INICIAIS
insert into setor(id, id_pai, nome, cor)
values (0, 0, 'NÃO INFORMADO', '');

insert into ocorrencia(id, nome, setor_id)
values (0, 'NÃO INFORMADO', 0);


COMMIT WORK;




