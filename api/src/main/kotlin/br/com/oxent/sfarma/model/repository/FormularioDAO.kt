package br.com.oxent.sfarma.model.repository

import br.com.oxent.sfarma.model.entity.Formulario
import br.com.oxent.sfarma.model.entity.Question
import getListString
import localTime
import org.intellij.lang.annotations.Language
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.Connection
import java.sql.JDBCType
import java.sql.ResultSet
import java.sql.SQLException


@Repository
class FormularioDAO @Autowired
constructor(private val jdbc: JdbcTemplate) {
    companion object {
        const val NEXTVAL_FORM = "SELECT nextval('formulario_id_seq')"
        const val NEXTVAL_QUEST = "SELECT nextval('question_id_seq')"
        const val NEXTVAL_FORM_RESP = "SELECT nextval('formulario_resposta_id_seq')"
        const val SELECT = "SELECT id, titulo, descricao, upload, uuid FROM formulario  where uuid = ? and ativo = true "

        const val SELECT_EMAIL = "SELECT email FROM formulario  where uuid = ? and ativo = true "

        const val LIST = "SELECT id,\n" +
                "       titulo,\n" +
                "       descricao,\n" +
                "       upload,\n" +
                "       uuid,\n" +
                "       email,\n" +
                "       ativo,\n" +
                "       dt_criacao,\n" +
                "       (select count(*) from formulario_resposta fr where fr.form_id = formulario.id) as qtd\n" +
                "FROM formulario\n" +
                "order by dt_criacao desc "

        const val SELECT_QUEST = "select id, form_id, titulo, tipo, obrigatorio, opcoes from question where form_id = ? "

        @Language("PostgreSQL")
        const val INSERIR_FORM_RESP = "insert into formulario_resposta(id,form_id, nome, email, fone, empresa, cidade, dt_resposta) values (?,?,?,?,?,?,?, current_timestamp)";
        const val INSERIR_QUEST_RESP = "insert into question_reposta(question_id, valor, form_resp_id) VALUES (?, ?, ?)";

        const val UPINSERT_FORM = "INSERT INTO formulario (id, titulo, descricao, uuid, email, dt_criacao, ativo)\n" +
                "VALUES (?, ?, ?, ?, ?, current_timestamp, ?)\n" +
                "ON CONFLICT (id)\n" +
                "    DO UPDATE SET titulo    = EXCLUDED.titulo,\n" +
                "                  descricao = EXCLUDED.descricao,\n" +
                "                  uuid      = EXCLUDED.uuid,\n" +
                "                  email     = EXCLUDED.email,\n" +
                "                  ativo     = EXCLUDED.ativo,\n" +
                "                  dt_criacao = EXCLUDED.dt_criacao ;"

        const val UPINSER_QUEST = "INSERT INTO question (id, form_id, titulo, tipo, obrigatorio, opcoes)\n" +
                "VALUES (?, ?, ?, ?, ?, ?)\n" +
                "ON CONFLICT (id)\n" +
                "    DO UPDATE SET titulo      = EXCLUDED.titulo,\n" +
                "                  tipo        = EXCLUDED.tipo,\n" +
                "                  obrigatorio = EXCLUDED.obrigatorio,\n" +
                "                  opcoes      = excluded.opcoes;"

        const val LIST_FORM_RESP = "select fr.id,\n" +
                "       fr.form_id,\n" +
                "       fr.nome,\n" +
                "       fr.email,\n" +
                "       fr.fone,\n" +
                "       fr.empresa,\n" +
                "       fr.cidade,\n" +
                "       fr.dt_resposta,\n" +
                "       f.titulo\n" +
                "from formulario_resposta fr\n" +
                "         inner join formulario f on f.id = fr.form_id\n" +
                "where fr.form_id = ? order by fr.dt_resposta;"

        const val LIST_QUEST_RESP = "select qr.valor, q.opcoes, q.id, q.form_id, q.titulo, q.tipo, q.obrigatorio from question_reposta qr\n" +
                "inner join question q on qr.question_id = q.id\n" +
                "where qr.form_resp_id = ?;"

    }

    fun setForm(f: Formulario) {
        this.jdbc.update(UPINSERT_FORM, f.id, f.titulo, f.descricao, f.uuid, f.email, f.ativo)
    }

    fun setQuest(q: Question) {

        val a = (q.opcoes as ArrayList).toArray()

        val idSqlArray = jdbc.execute { c: Connection -> c.createArrayOf(JDBCType.VARCHAR.getName(), a) }

        this.jdbc.update(UPINSER_QUEST, q.id, q.formID, q.titulo, q.tipo, q.obrigatorio, idSqlArray)
    }

    fun getNextID(): Int = jdbc.queryForObject(NEXTVAL_FORM, Int::class.java)!!
    fun getNextIDQuest(): Int = jdbc.queryForObject(NEXTVAL_QUEST, Int::class.java)!!
    fun getNextIDFormResp(): Int = jdbc.queryForObject(NEXTVAL_FORM_RESP, Int::class.java)!!

    fun setFormResp(f: Formulario) {
        this.jdbc.update(INSERIR_FORM_RESP, f.id, f.formID, f.nome, f.email, f.fone, f.empresa, f.cidade)
    }

    fun set(f: Formulario) {
        this.jdbc.update(INSERIR_FORM_RESP, f.id, f.formID, f.nome, f.email, f.fone, f.empresa, f.cidade)
    }

    fun setQuestResp(q: Question) {
        this.jdbc.update(INSERIR_QUEST_RESP, q.id, q.valor, q.formID)
    }

    fun get(uuid: String) = jdbc.queryForObject(SELECT, FormularioRowMapper(), uuid)

    fun getEmailRespost(uuid: String) = jdbc.queryForObject(SELECT_EMAIL, String()::class.java, uuid)

    fun list() = jdbc.query(LIST, FormularioADMRowMapper())

    fun listRespostas(id: Int) = jdbc.query(LIST_FORM_RESP, FormularioRespostaRowMapper(), id)

    fun getQuest(formID: Int) = jdbc.query(SELECT_QUEST, QuestionRowMapper(), formID)
    fun getQuestRespostas(formID: Int) = jdbc.query(LIST_QUEST_RESP, QuestionRespostaRowMapper(), formID)


    internal class FormularioRowMapper : RowMapper<Formulario> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): Formulario {
            return Formulario(
                    id = rs.getInt("id"),
                    titulo = rs.getString("titulo"),
                    descricao = rs.getString("descricao"),
                    uuid = rs.getString("uuid")
            )
        }
    }

    internal class FormularioADMRowMapper : RowMapper<Formulario> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): Formulario {
            return Formulario(
                    id = rs.getInt("id"),
                    titulo = rs.getString("titulo"),
                    descricao = rs.getString("descricao"),
                    ativo = rs.getBoolean("ativo"),
                    email = rs.getString("email"),
                    dtCriacao = rs.getTimestamp("dt_criacao").localTime(),
                    uuid = rs.getString("uuid"),
                    qtdResposta = rs.getInt("qtd")
            )
        }
    }

    fun delete(id: Int) {
        jdbc.update("delete from question_reposta qr where exists( select * from formulario_resposta f where f.form_id = ? and qr.form_resp_id = f.id); ", id)
        jdbc.update("delete from formulario_resposta fr where fr.form_id = ? ", id)
        jdbc.update("delete from question q where q.form_id = ? ", id)
        jdbc.update("delete from formulario f where f.id = ? ", id)
    }

    internal class FormularioRespostaRowMapper : RowMapper<Formulario> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): Formulario {
            return Formulario(
                    id = rs.getInt("id"),
                    titulo = rs.getString("titulo"),
                    nome = rs.getString("nome"),
                    email = rs.getString("email"),
                    fone = rs.getString("fone"),
                    empresa = rs.getString("empresa"),
                    cidade = rs.getString("cidade"),
                    dtCriacao = rs.getTimestamp("dt_resposta").localTime(),
                    uuid = ""
            )
        }
    }

    internal class QuestionRowMapper : RowMapper<Question> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): Question {
            return Question(
                    id = rs.getInt("id"),
                    formID = rs.getInt("form_ID"),
                    titulo = rs.getString("titulo"),
                    tipo = rs.getInt("tipo"),
                    obrigatorio = rs.getBoolean("obrigatorio"),
                    opcoes = rs.getListString("opcoes")
            )
        }
    }

    internal class QuestionRespostaRowMapper : RowMapper<Question> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): Question {
            return Question(
                    id = rs.getInt("id"),
                    formID = rs.getInt("form_ID"),
                    titulo = rs.getString("titulo"),
                    tipo = rs.getInt("tipo"),
                    obrigatorio = rs.getBoolean("obrigatorio"),
                    opcoes = rs.getListString("opcoes"),
                    valor = rs.getString("valor")
            )
        }
    }

}