package br.com.oxent.sfarma.model.repository

import br.com.oxent.sfarma.model.entity.Ocorrencia
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class OcorrenciaDAO @Autowired
constructor(private val jdbc: JdbcTemplate) {
    companion object {
        const val SELECT = "SELECT  oco.id, oco.nome, oco.setor_id, s.nome as setor_nome  from ocorrencia oco inner join setor s on oco.setor_id = s.id"
        const val UPINSERT = "INSERT INTO ocorrencia (id, setor_id, nome)\n" +
                "VALUES (?,?,?)\n" +
                "ON CONFLICT (id)\n" +
                "DO UPDATE SET setor_id = EXCLUDED.setor_id," +
                "              nome = EXCLUDED.nome;"
        const val NEXTVAL = "SELECT nextval('ocorrencia_id_seq')"
        const val OCORRENCIAS_DO_SETOR = "WITH RECURSIVE subordinates AS (\n" +
                "    SELECT id\n" +
                "    FROM setor\n" +
                "    where id = (select u.setor_id from usuario u where u.login = ? )\n" +
                "    UNION\n" +
                "    SELECT se.id\n" +
                "    FROM setor se\n" +
                "             INNER JOIN subordinates s ON se.id_pai = s.id\n" +
                ")\n" +
                "select oc.id, oc.setor_id, oc.nome, '' as setor_nome\n" +
                "from ocorrencia oc\n" +
                "where exists(select id from subordinates where id = oc.setor_id) or oc.setor_id = 0 \n" +
                "order by 1\n" +
                "\n"
    }

    fun getNextID(): Int = jdbc.queryForObject(NEXTVAL, Int::class.java)!!

    fun getList(): List<Ocorrencia> = jdbc.query(SELECT, OcorrenciaRowMapper())

    fun getOcorrenciaDoSetor(login: String) =  jdbc.query(OCORRENCIAS_DO_SETOR, OcorrenciaRowMapper(), login)


    fun getTotal(): Int = jdbc.queryForObject("select count(*) from ocorrencia", Int::class.java)!!

    fun set(ocorrencia: Ocorrencia) {
        jdbc.update(UPINSERT,
                ocorrencia.id,
                ocorrencia.setorID,
                ocorrencia.nome)
    }

    internal class OcorrenciaRowMapper : RowMapper<Ocorrencia> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): Ocorrencia? {
            return Ocorrencia(
                    id = rs.getInt("id"),
                    nome = rs.getString("nome"),
                    setorID = rs.getInt("setor_id"),
                    setorNome = rs.getString("setor_nome")
            )
        }
    }

}