package br.com.oxent.sfarma.model.repository

import br.com.oxent.sfarma.model.entity.estatistica.EstatisticaVersao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class EstatisticaDAO @Autowired
constructor(private val jdbc: JdbcTemplate) {
    companion object {
        const val SELECT_ESTATISTICA_VERSAO = "select coalesce(c.versao, 'Não Informado') as versao, count(*) as total, (select count(*) from cliente) as geral\n" +
                "                from cliente c\n" +
                "                group by versao\n" +
                "                order by CAST(coalesce(REPLACE(c.versao, '.', '' ), '0') AS integer) desc"
    }

    fun getListVersao(): List<EstatisticaVersao> = jdbc.query(SELECT_ESTATISTICA_VERSAO, EstatisticaVersaoRowMapper())

    internal class EstatisticaVersaoRowMapper : RowMapper<EstatisticaVersao> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): EstatisticaVersao {
            return EstatisticaVersao(
                    versao = rs.getString("versao"),
                    total = rs.getInt("total"),
                    totalGeral = rs.getInt("geral")
            )
        }
    }

}