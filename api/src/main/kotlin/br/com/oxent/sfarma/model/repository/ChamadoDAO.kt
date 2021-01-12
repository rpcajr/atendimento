package br.com.oxent.sfarma.model.repository

import br.com.oxent.sfarma.model.entity.*
import getURLImage
import localTime
import org.intellij.lang.annotations.Language
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import zeroNull
import java.sql.ResultSet
import java.sql.SQLException
import java.text.SimpleDateFormat

@Repository
class ChamadoDAO @Autowired
constructor(private val jdbc: JdbcTemplate) {
    companion object {
        @Language("PostgreSQL")
        const val SELECT = "select id, versao, contato_id, atribuido_usuario_id, usuario_id, ocorrencia_id, cliente_id, dat_abertura, dat_fechamento, detalhamento, sistemas, palavra_chave, prioridade, situacao, titulo, atendimento, solucao from chamado c where c.id = ? "

        const val SELECT_PESQUISA =
                "WITH RECURSIVE subordinates AS (\n" +
                        "    SELECT id\n" +
                        "    FROM setor\n" +
                        "    where id = (select u.setor_id from usuario u where u.login = ? )\n" +
                        "    UNION\n" +
                        "    SELECT se.id\n" +
                        "    FROM setor se\n" +
                        "             INNER JOIN subordinates s ON se.id_pai = s.id\n" +
                        ")\n" +
                        "select c.id,\n" +
                        "       cli.cod_cliente,\n" +
                        "       cli.nome,\n" +
                        "       sup.nome as suporte,\n" +
                        "       c.dat_fechamento,\n" +
                        "       c.detalhamento,\n" +
                        "       c.solucao,\n" +
                        "       c.sistemas,\n" +
                        "       cli.fantasia as nome_cliente,\n" +
                        "       u.foto,\n" +
                        "       u.nome       as nome_usuario,\n" +
                        "       c.atendimento,\n" +
                        "       c.titulo,\n" +
                        "       c.situacao,\n" +
                        "       o.nome       as nome_ocorrencia,\n" +
                        "       c.prioridade,\n" +
                        "       c.dat_abertura,\n" +
                        "       c.versao,\n" +
                        "       cont.nome as contato from chamado c\n" +
                        "                                     inner join usuario u on c.atribuido_usuario_id = u.id\n" +
                        "                                     inner join ocorrencia o on c.ocorrencia_id = o.id\n" +
                        "                                     left join cliente cli on c.cliente_id = cli.id\n" +
                        "                                     left join contato cont on cont.id = c.contato_id\n" +
                        "                                     inner join usuario sup on sup.id = c.usuario_id "

        const val UPINSERT = "INSERT INTO chamado(id, versao, atribuido_usuario_id,  usuario_id, ocorrencia_id, contato_id, cliente_id, dat_abertura, dat_fechamento, detalhamento, sistemas, palavra_chave, prioridade, situacao, titulo, atendimento, solucao)    \n" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)\n" +
                "ON CONFLICT (id)\n" +
                "DO UPDATE SET versao = EXCLUDED.versao," +
                "              atribuido_usuario_id = EXCLUDED.atribuido_usuario_id," +
                "              ocorrencia_id = EXCLUDED.ocorrencia_id," +
                "              contato_id = EXCLUDED.contato_id," +
                "              cliente_id = EXCLUDED.cliente_id," +
                "              dat_abertura = EXCLUDED.dat_abertura," +
                "              dat_fechamento = EXCLUDED.dat_fechamento," +
                "              detalhamento = EXCLUDED.detalhamento," +
                "              sistemas = EXCLUDED.sistemas," +
                "              palavra_chave = EXCLUDED.palavra_chave," +
                "              prioridade = EXCLUDED.prioridade," +
                "              atendimento = EXCLUDED.atendimento," +
                "              titulo = EXCLUDED.titulo," +
                "              solucao = EXCLUDED.solucao," +
                "              situacao = EXCLUDED.situacao;"

        const val NEXTVAL = "SELECT nextval('chamado_id_seq')"

        const val SQL_COMENTARIO = "select cc.chamado_id,\n" +
                "       cc.usuario_id,\n" +
                "       cc.comentario,\n" +
                "       u.nome,\n" +
                "       u.foto, dat_comentario\n" +
                "from chamado_comentario cc\n" +
                "         inner join usuario u on u.id = cc.usuario_id\n" +
                "where cc.chamado_id = ? "

    }

    fun getNextID(): Int = jdbc.queryForObject(NEXTVAL, Int::class.java)!!

    fun get(id: Int) = jdbc.queryForObject(SELECT, ChamadoRowMapper(), id)

    fun set(c: Chamado) {
        jdbc.update(
                UPINSERT,
                c.id,
                c.versao,
                c.atribuicaoID,
                c.usuarioID,
                c.ocorrenciaID,
                c.contatoID,
                c.clienteID,
                c.datAbertura,
                c.datFechamento,
                c.detalhamento,
                c.sistemas,
                c.palavaChave,
                c.prioridade,
                c.situacao,
                c.titulo,
                c.atendimento,
                c.solucao
        )
    }

    fun list(f: Filter?, p: Pageable, name: String): Page<ChamadoPesquisa> {
        val total = getTotalChamado(f, name)
        val list = jdbc.query("$SELECT_PESQUISA ${getWhereFilter(f)}  order by c.prioridade desc, c.situacao desc, c.dat_abertura desc LIMIT ${p.pageSize} OFFSET ${p.offset}", ChamadoPesquisaRowMapper(), name)
        return PageImpl<ChamadoPesquisa>(list, p, total!!)
    }

    fun getChamados(f: Filter?, login: String) = jdbc.query("$SELECT_PESQUISA ${getWhereFilter(f)} order  by cli.cod_cliente , c.dat_abertura ", ChamadoImpressaoRowMapper() , login )

    fun deleteChamado(id: Int) {
        jdbc.update("delete from chamado c where c.id = ? ", id)
    }

    fun getWhereFilter(f: Filter?): String {
        //val sql = "select * from chamado c where c. "
        var temp = " where 1 = 1 "

        if (f == null) {
            return temp
        }

        if (f.clienteID != null) {
            temp = temp.plus("and c.cliente_id = ${f.clienteID}\n")
        }

        if (f.datInicial != null) {
            temp = temp.plus("and c.dat_abertura >= '${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(f.datInicial)}'\n")
        }

        if (f.datFinal != null) {
            temp = temp.plus("and c.dat_abertura <= '${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(f.datFinal)}'\n")
        }

        if (f.situacao.isNotEmpty()) {
            temp = temp.plus("and c.situacao in( ${f.situacao})\n")
        }

        if (f.ocorrenciaID != null) {
            temp = temp.plus("and c.ocorrencia_id = ${f.ocorrenciaID}\n")
        } else {
            temp = temp.plus("and (exists(select id from subordinates where id = o.setor_id) or o.setor_id = 0) ")
        }

        if (f.atribuidoID != null) {
            temp = temp.plus("and atribuido_usuario_id = ${f.atribuidoID}\n")
        }

        if (f.sistema != null) {
            temp = temp.plus("and upper(c.sistemas) = '${f.sistema.toUpperCase()}'\n")
        }

        if (f.atribuidoID != null) {
            temp = temp.plus("and c.atribuido_usuario_id = ${f.atribuidoID}\n")
        }

        if (f.versao != null) {
            temp = temp.plus("and c.versao = '${f.versao}'\n")
        }

        return "$temp "
    }

    private fun getTotalChamado(f: Filter?, name: String): Long? {
        val rowCountSql =
                "WITH RECURSIVE subordinates AS (\n" +
                        "    SELECT id\n" +
                        "    FROM setor\n" +
                        "    where id = (select u.setor_id from usuario u where u.login = ? )\n" +
                        "    UNION\n" +
                        "    SELECT se.id\n" +
                        "    FROM setor se\n" +
                        "             INNER JOIN subordinates s ON se.id_pai = s.id\n" +
                        ")\n" +
                        "select count(*) from chamado c\n" +
                        "         inner join usuario u on c.atribuido_usuario_id = u.id\n" +
                        "         inner join ocorrencia o on c.ocorrencia_id = o.id\n" +
                        "         left join cliente cli on c.cliente_id = cli.id " +
                        " ${getWhereFilter(f)}"
        return jdbc.queryForObject(rowCountSql, name) { rs, _ -> rs.getLong(1) }
    }

    fun inserirAnexo(f: JFFile, chamadoID: Int) {
        this.jdbc.update("insert into  anexo (chamado_id, nome, descricao) values (?, ?, ? )", chamadoID, f.nome, f.descricao)
    }

    fun inserirComentario(comentario: Comentario) {
        this.jdbc.update(
                "insert into chamado_comentario(chamado_id, usuario_id, comentario, dat_comentario) VALUES (?,?,?,?)"
                , comentario.chamadoId, comentario.usuario.id, comentario.comentario, comentario.datComentario)
    }

    fun deleteComentario(chamadoID: Int) {
        this.jdbc.update("delete from chamado_comentario cc where cc.chamado_id = ?", chamadoID)
    }

    fun getAnexos(id: Int) = jdbc.query("select * from anexo a where a.chamado_id = ? ", JFFileRowMapper(), id)

    fun deleteAnexo(chamadoID: Int) {
        this.jdbc.update("delete from anexo a where a.chamado_id = ? ", chamadoID)
    }

    internal class ChamadoPesquisaRowMapper : RowMapper<ChamadoPesquisa> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): ChamadoPesquisa {
            return ChamadoPesquisa(
                    id = rs.getInt("id"),
                    cliente = rs.getString("nome_cliente"),
                    imagemUsuario = getURLImage(rs.getString("foto")),
                    nomeUsuario = rs.getString("nome_usuario"),
                    atendimento = rs.getString("atendimento"),
                    titulo = rs.getString("titulo"),
                    situacao = rs.getInt("situacao"),
                    ocorrencia = rs.getString("nome_ocorrencia"),
                    prioridade = rs.getInt("prioridade"),
                    dataAbertura = rs.getTimestamp("dat_abertura").toLocalDateTime(),
                    versao = rs.getString("versao"),
                    contato = rs.getString("contato")
            )
        }
    }

    fun getComentarios(id: Int) = jdbc.query(SQL_COMENTARIO, ComentarioRowMapper(), id)

    internal class ComentarioRowMapper : RowMapper<Comentario> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): Comentario {
            return Comentario(
                    chamadoId = rs.getInt("chamado_id"),
                    comentario = rs.getString("comentario"),
                    usuario = UsuarioPerfil(
                            id = rs.getInt("usuario_id"),
                            nome = rs.getString("nome"),
                            url = getURLImage(rs.getString("foto")),
                            foto = rs.getString("foto"),
                            setor = ""
                    ),
                    datComentario = rs.getTimestamp("dat_comentario").toLocalDateTime()
            )
        }
    }

    internal class ChamadoImpressaoRowMapper : RowMapper<ChamadoImpressao> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): ChamadoImpressao {
            return ChamadoImpressao(
                    codCliente = rs.getString("cod_cliente"),
                    nomeCliente = rs.getString("nome"),
                    cidade = rs.getString("nome_cliente"),
                    versao = rs.getString("versao"),
                    datAbertura = rs.getDate("dat_abertura"),
                    datFechamento = rs.getDate("dat_fechamento"),
                    titulo = rs.getString("titulo"),
                    detalhamento = rs.getString("detalhamento"),
                    solucao = rs.getString("solucao"),
                    contato = rs.getString("contato"),
                    sistema = rs.getString("sistemas"),
                    situacao = rs.getInt("situacao"),
                    prioridade = rs.getInt("prioridade"),
                    suporte = rs.getString("suporte"),
                    ocorrencia = rs.getString("nome_ocorrencia")
            )
        }
    }

    internal class JFFileRowMapper : RowMapper<JFFile> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): JFFile {
            return JFFile(
                    nome = rs.getString("nome"),
                    descricao = rs.getString("descricao"),
                    url = getURLImage(rs.getString("nome"))
            )
        }
    }

    internal class ChamadoRowMapper : RowMapper<Chamado> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): Chamado {
            return Chamado(
                    id = rs.getInt("id"),
                    versao = rs.getString("versao"),
                    titulo = rs.getString("titulo"),
                    usuarioID = rs.getInt("usuario_id"),
                    atribuicaoID = rs.getInt("atribuido_usuario_id"),
                    ocorrenciaID = rs.getInt("ocorrencia_id"),
                    contatoID = rs.getInt("contato_id").zeroNull(),
                    clienteID = rs.getInt("cliente_id").zeroNull(),
                    datAbertura = rs.getTimestamp("dat_abertura").toLocalDateTime(),
                    datFechamento = rs.getTimestamp("dat_fechamento").localTime(),
                    detalhamento = rs.getString("detalhamento"),
                    sistemas = rs.getString("sistemas"),
                    prioridade = rs.getInt("prioridade"),
                    atendimento = rs.getString("atendimento"),
                    situacao = rs.getInt("situacao"),
                    solucao = rs.getString("solucao")
            )
        }
    }

}