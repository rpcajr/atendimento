CREATE TABLE sistema (
                         seq SERIAL  NOT NULL ,
                         nome VARCHAR(50)      ,
                         PRIMARY KEY(seq));




CREATE TABLE setor (
                       id SERIAL  NOT NULL ,
                       id_pai INTEGER    ,
                       nome VARCHAR(50)    ,
                       cor VARCHAR(50)      ,
                       PRIMARY KEY(id)  ,
                       FOREIGN KEY(id_pai)
                           REFERENCES setor(id)
                           ON DELETE CASCADE
                           ON UPDATE CASCADE);


CREATE INDEX setor_FKIndex1 ON setor (id_pai);


CREATE INDEX IFK_Rel_setor_pai ON setor (id_pai);


CREATE TABLE versao (
                        versao VARCHAR(15)   NOT NULL   ,
                        PRIMARY KEY(versao));




CREATE TABLE ocorrencia (
                            id SERIAL  NOT NULL ,
                            setor_id INTEGER    ,
                            nome VARCHAR(30)      ,
                            PRIMARY KEY(id)  ,
                            FOREIGN KEY(setor_id)
                                REFERENCES setor(id));


CREATE INDEX ocorrencia_FKIndex1 ON ocorrencia (setor_id);


CREATE INDEX IFK_Rel_ocorrencia_setor ON ocorrencia (setor_id);


CREATE TABLE usuario (
                         id SERIAL  NOT NULL ,
                         setor_id INTEGER   NOT NULL ,
                         nome VARCHAR(50)    ,
                         fone VARCHAR(15)    ,
                         email VARCHAR(100)    ,
                         nascimento DATE    ,
                         login VARCHAR(20)    ,
                         senha VARCHAR(100)    ,
                         foto VARCHAR(100)    ,
                         inativo BOOL  DEFAULT false NOT NULL ,
                         permissoes VARCHAR      ,
                         PRIMARY KEY(id)  ,
                         FOREIGN KEY(setor_id)
                             REFERENCES setor(id));


CREATE INDEX cad_usuario_FKIndex1 ON usuario (setor_id);


CREATE INDEX IFK_Rel_usuario_setor ON usuario (setor_id);


CREATE TABLE cliente (
                         id SERIAL  NOT NULL ,
                         versao VARCHAR(15)    ,
                         cod_cliente VARCHAR(8)   NOT NULL ,
                         nome VARCHAR(100)    ,
                         fantasia VARCHAR(100)    ,
                         endereco VARCHAR(150)    ,
                         bairro VARCHAR(50)    ,
                         cidade VARCHAR(50)    ,
                         uf VARCHAR(2)    ,
                         cep VARCHAR(15)    ,
                         fone1 VARCHAR(30)    ,
                         fone2 VARCHAR(30)    ,
                         email VARCHAR(150)      ,
                         PRIMARY KEY(id)  ,
                         FOREIGN KEY(versao)
                             REFERENCES versao(versao));


CREATE INDEX cliente_FKIndex1 ON cliente (versao);


CREATE INDEX IFK_Rel_cliente_versao ON cliente (versao);


CREATE TABLE contato (
                         id SERIAL  NOT NULL ,
                         cliente_id INTEGER   NOT NULL ,
                         nome VARCHAR(100)    ,
                         nascimento DATE    ,
                         email VARCHAR(150)    ,
                         fone VARCHAR(15)    ,
                         fone2 VARCHAR(15)    ,
                         cargo VARCHAR(50)      ,
                         PRIMARY KEY(id)  ,
                         FOREIGN KEY(cliente_id)
                             REFERENCES cliente(id));


CREATE INDEX contato_FKCliente ON contato (cliente_id);


CREATE INDEX IFK_Rel_contato_cliente ON contato (cliente_id);


CREATE TABLE cliente_sistema (
                                 cliente_id INTEGER   NOT NULL ,
                                 seq_sistema INTEGER   NOT NULL   ,
                                 PRIMARY KEY(cliente_id, seq_sistema)    ,
                                 FOREIGN KEY(cliente_id)
                                     REFERENCES cliente(id),
                                 FOREIGN KEY(seq_sistema)
                                     REFERENCES sistema(seq));


CREATE INDEX cliente_has_sistema_FKIndex1 ON cliente_sistema (cliente_id);
CREATE INDEX cliente_has_sistema_FKIndex2 ON cliente_sistema (seq_sistema);


CREATE INDEX IFK_Rel_cliente_sistema ON cliente_sistema (cliente_id);
CREATE INDEX IFK_Rel_sistema_cliente ON cliente_sistema (seq_sistema);


CREATE TABLE chamado (
                         id SERIAL  NOT NULL ,
                         versao VARCHAR(15)    ,
                         contato_id INTEGER    ,
                         atribuido_usuario_id INTEGER    ,
                         usuario_id INTEGER   NOT NULL ,
                         ocorrencia_id INTEGER   NOT NULL ,
                         cliente_id INTEGER    ,
                         dat_abertura TIMESTAMP   NOT NULL ,
                         dat_fechamento TIMESTAMP    ,
                         detalhamento VARCHAR    ,
                         sistemas VARCHAR    ,
                         palavra_chave VARCHAR    ,
                         prioridade INTEGER  DEFAULT 0 NOT NULL ,
                         situacao INTEGER   NOT NULL ,
                         titulo VARCHAR(100)    ,
                         atendimento VARCHAR(1)    ,
                         solucao VARCHAR      ,
                         PRIMARY KEY(id)            ,
                         FOREIGN KEY(cliente_id)
                             REFERENCES cliente(id),
                         FOREIGN KEY(ocorrencia_id)
                             REFERENCES ocorrencia(id),
                         FOREIGN KEY(usuario_id)
                             REFERENCES usuario(id),
                         FOREIGN KEY(atribuido_usuario_id)
                             REFERENCES usuario(id),
                         FOREIGN KEY(versao)
                             REFERENCES versao(versao),
                         FOREIGN KEY(contato_id)
                             REFERENCES contato(id));


CREATE INDEX chamado_FKCliente ON chamado (cliente_id);
CREATE INDEX chamado_FKocorrencia ON chamado (ocorrencia_id);
CREATE INDEX chamado_FKUsuario ON chamado (usuario_id);
CREATE INDEX chamado_FKUsuarioAtribuido ON chamado (atribuido_usuario_id);
CREATE INDEX chamado_FKVersao ON chamado (versao);
CREATE INDEX chamado_FKContato ON chamado (contato_id);


CREATE INDEX IFK_Rel_chamado_cliente ON chamado (cliente_id);
CREATE INDEX IFK_Rel_chamado_ocorrencia ON chamado (ocorrencia_id);
CREATE INDEX IFK_Rel_chamado_usuario ON chamado (usuario_id);
CREATE INDEX IFK_Rel_atribuido ON chamado (atribuido_usuario_id);
CREATE INDEX IFK_Rel_chamado_versao ON chamado (versao);
CREATE INDEX IFK_Rel_chamado_contato ON chamado (contato_id);


CREATE TABLE anexo (
                       chamado_id INTEGER   NOT NULL ,
                       nome VARCHAR(100)   NOT NULL ,
                       descricao VARCHAR(100)      ,
                       FOREIGN KEY(chamado_id)
                           REFERENCES chamado(id));


CREATE INDEX anexo_FKIndex1 ON anexo (chamado_id);


CREATE INDEX IFK_Rel_chamado_anexo ON anexo (chamado_id);


