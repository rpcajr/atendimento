package br.com.oxent.sfarma.model.repository

import br.com.oxent.sfarma.model.entity.Contato
import org.intellij.lang.annotations.Language
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class ContatoDAO @Autowired
constructor(private val jdbc: JdbcTemplate) {
    companion object {
        @Language("PostgreSQL")
        const val SELECT = "select  c.id, nome, nascimento, email, fone, fone2, cliente_id, cargo from contato c where c.cliente_id = ? "
        const val UPINSERT =
                "INSERT INTO contato(id, nome, nascimento, email, fone, fone2, cliente_id, cargo) " +
                        "VALUES (?,?,?,?,?,?,?,?)\n" +
                        "ON CONFLICT (id)\n" +
                        "DO UPDATE SET cliente_id = EXCLUDED.cliente_id," +
                        "              nome = EXCLUDED.nome," +
                        "              nascimento = EXCLUDED.nascimento," +
                        "              email = EXCLUDED.email," +
                        "              fone = EXCLUDED.fone," +
                        "              fone2 = EXCLUDED.fone2," +
                        "              cargo = EXCLUDED.cargo ;"

        const val NEXTVAL = "SELECT nextval('contato_id_seq')"
    }

    fun getNextID(): Int = jdbc.queryForObject(NEXTVAL, Int::class.java)!!

    fun getContatos(id: Int): List<Contato> = jdbc.query("$SELECT order by id", ContatoRowMapper(), id)

    fun set(c: Contato) {
        jdbc.update(UPINSERT,
                c.id,
                c.nome,
                c.nascimento,
                c.email,
                c.fone,
                c.fone2,
                c.clienteID,
                c.cargo)
    }

    internal class ContatoRowMapper : RowMapper<Contato> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): Contato? {
            return Contato(
                    id = rs.getInt("id"),
                    clienteID = rs.getInt("cliente_id"),
                    nome = rs.getString("nome"),
                    nascimento = rs.getDate("nascimento"),
                    fone = rs.getString("fone"),
                    fone2 = rs.getString("fone2"),
                    cargo = rs.getString("cargo"),
                    email = rs.getString("email")
            )
        }
    }

}