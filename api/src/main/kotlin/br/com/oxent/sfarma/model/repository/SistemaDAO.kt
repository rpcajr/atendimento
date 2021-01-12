package br.com.oxent.sfarma.model.repository

import br.com.oxent.sfarma.model.entity.Sistema
import br.com.oxent.sfarma.model.entity.Versao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class SistemaDAO @Autowired
constructor(private val jdbc: JdbcTemplate) {
    companion object {
        const val SELECT = "select seq, nome from sistema "
        const val UPINSERT = "INSERT INTO sistema (seq, nome)\n" +
                "VALUES (?,?)\n" +
                "ON CONFLICT (seq)\n" +
                "DO UPDATE SET nome = EXCLUDED.nome;"
    }

    fun getList(): List<Sistema> = jdbc.query(SELECT, SistemaRowMapper())

    fun getTotal(): Int = jdbc.queryForObject("select count(*) from sistema", Int::class.java)!!

    fun set(sistema: Sistema) {
        jdbc.update(UPINSERT, sistema.seq, sistema.nome)
    }

    internal class SistemaRowMapper : RowMapper<Sistema> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): Sistema {
            return Sistema(
                    seq = rs.getInt("seq"),
                    nome = rs.getString("nome")
            )
        }
    }

}