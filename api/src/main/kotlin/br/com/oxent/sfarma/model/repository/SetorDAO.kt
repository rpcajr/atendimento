package br.com.oxent.sfarma.model.repository

import br.com.oxent.sfarma.model.entity.Setor
import org.intellij.lang.annotations.Language
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class SetorDAO @Autowired
constructor(private val jdbc: JdbcTemplate) {
    companion object {
        @Language("PostgreSQL")
        const val SELECT = "SELECT s.id, s.id_pai, s.nome, s.cor , pai.nome as nome_pai from setor s inner join setor pai on pai.id = s.id_pai "

        const val UPINSERT = "INSERT INTO setor (id, id_pai, nome, cor)\n" +
                "VALUES (?,?,?,?)\n" +
                "ON CONFLICT (id)\n" +
                "DO UPDATE SET id_pai = EXCLUDED.id_pai," +
                "              nome = EXCLUDED.nome," +
                "              cor = EXCLUDED.cor ;"
        const val NEXTVAL = "SELECT nextval('setor_id_seq')"

        const val SETORES_USUARIO =
                "WITH RECURSIVE subordinates AS (\n" +
                "    SELECT\n" +
                "        ss.id,\n" +
                "        ss.id_pai,\n" +
                "        ss.nome,\n" +
                "        '' AS nome_pai\n" +
                "    FROM setor ss\n" +
                " inner join usuario u on u.setor_id = ss.id " +
                " where u.login = ? " +
                "\n" +
                "    UNION\n" +
                "    SELECT\n" +
                "        se.id,\n" +
                "        se.id_pai,\n" +
                "        se.nome,\n" +
                "        '' AS nome_pai\n" +
                "    FROM\n" +
                "        setor se\n" +
                "            INNER JOIN subordinates s ON se.id_pai = s.id\n" +
                ") SELECT\n" +
                "    *\n" +
                "FROM\n" +
                "    subordinates "

        const val ORGANOGRAMA = "WITH RECURSIVE subordinates AS (\n" +
                "    SELECT\n" +
                "        id,\n" +
                "        id_pai,\n" +
                "        nome,\n" +
                "        '' AS nome_pai\n" +
                "    FROM\n" +
                "        setor\n" +
                "\n" +
                "    UNION\n" +
                "    SELECT\n" +
                "        se.id,\n" +
                "        se.id_pai,\n" +
                "        se.nome,\n" +
                "        '' AS nome_pai\n" +
                "    FROM\n" +
                "        setor se\n" +
                "            INNER JOIN subordinates s ON se.id_pai = s.id\n" +
                ") SELECT\n" +
                "    *\n" +
                "FROM\n" +
                "    subordinates where id > 0;"

        const val SETORES_DEPENDENTES = "WITH RECURSIVE subordinates AS (\n" +
                "    SELECT\n" +
                "        id,\n" +
                "        id_pai,\n" +
                "        nome,\n" +
                "        '' AS nome_pai\n" +
                "    FROM setor WHERE nome = ? \n" +
                "    UNION\n" +
                "    SELECT\n" +
                "        se.id,\n" +
                "        se.id_pai,\n" +
                "        se.nome,\n" +
                "        '' AS nome_pai\n" +
                "    FROM\n" +
                "        setor se\n" +
                "            INNER JOIN subordinates s ON se.id_pai = s.id\n" +
                ") SELECT\n" +
                "    *\n" +
                "FROM subordinates ;"


    }

    fun getNextID(): Int = jdbc.queryForObject(NEXTVAL, Int::class.java)!!

    fun getList(): List<Setor> = jdbc.query("$SELECT order by id", SetorRowMapper())

    fun getOrganograma(): List<Setor> = jdbc.query(ORGANOGRAMA, SetorRowMapper())

    fun getSetoresDependentes(nome: String) = jdbc.query(SETORES_DEPENDENTES, SetorRowMapper(), nome)

    fun getListUsuario(login: String): List<Setor> = jdbc.query(SETORES_USUARIO, SetorRowMapper(), login)

    fun set(setor: Setor) {
        jdbc.update(UPINSERT,
                setor.id,
                setor.paiID,
                setor.nome,
                setor.cor)
    }

    fun get(id: Int): Setor? = jdbc.queryForObject("$SELECT where id = ? ", SetorRowMapper(), id)

    internal class SetorRowMapper : RowMapper<Setor> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): Setor? {
            return Setor(
                    id = rs.getInt("id"),
                    nome = rs.getString("nome"),
                    paiID = rs.getInt("id_pai"),
                    nomePai = rs.getString("nome_pai")
            )
        }
    }

}