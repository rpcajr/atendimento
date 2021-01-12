package br.com.oxent.sfarma.model.repository

import br.com.oxent.sfarma.model.entity.Versao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class VersaoDAO @Autowired
constructor(private val jdbc: JdbcTemplate) {
    companion object {
        const val SELECT = "select versao from versao order by  CAST( replace(versao, '.','') AS integer) desc "
        const val UPINSERT = "INSERT INTO versao (versao)\n" +
                "VALUES (?)\n" +
                "ON CONFLICT (versao) DO UPDATE SET versao = EXCLUDED.versao"
    }

    fun getList(): List<String> = jdbc.queryForList(SELECT, String::class.java)

    fun getTotal(): Int = jdbc.queryForObject("select count(*) from versao", Int::class.java)!!

    fun set(v: Versao) {
        jdbc.update(UPINSERT, v.versao)
    }

    internal class VersaoRowMapper : RowMapper<Versao> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): Versao? {
            return Versao(
                    versao = rs.getString("versao")
            )
        }
    }

}