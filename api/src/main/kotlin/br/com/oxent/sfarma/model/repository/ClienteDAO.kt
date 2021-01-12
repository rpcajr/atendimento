package br.com.oxent.sfarma.model.repository

import br.com.oxent.sfarma.model.entity.Cliente
import org.intellij.lang.annotations.Language
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class ClienteDAO @Autowired
constructor(private val jdbc: JdbcTemplate) {
    companion object {
        const val SELECT = "SELECT id, cod_cliente, nome, fantasia, endereco, bairro, cidade, uf, cep, fone1, fone2, email, versao FROM cliente  "
        const val NEXTVAL = "SELECT nextval('cliente_id_seq')"
        @Language("PostgreSQL")
        const val INSERT = "INSERT INTo cliente( id, cod_cliente, nome, fantasia, endereco, bairro, cidade, uf, cep, fone1, fone2, email, versao) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"
        @Language("PostgreSQL")
        const val UPDATE = "update cliente set cod_cliente = ?, nome = ?, fantasia = ?, endereco = ?, bairro = ?, cidade = ?, uf = ?, cep=?, fone1 = ?, fone2 = ?, email = ?, versao = ? where id = ? "
        @Language("PostgreSQL")
        const val UPINSERT =
                "update cliente set nome = ?, fantasia = ?, uf = ?, versao = ? where cod_cliente = ?"


    }

    fun getNextID(): Int = jdbc.queryForObject(NEXTVAL, Int::class.java)!!

    fun getClientePesquisa(query: String): List<Cliente> {
        val sql = "select id,\n" +
                "       versao,\n" +
                "       cod_cliente,\n" +
                "       nome,\n" +
                "       fantasia,\n" +
                "       endereco,\n" +
                "       bairro,\n" +
                "       cidade,\n" +
                "       uf,\n" +
                "       cep,\n" +
                "       fone1,\n" +
                "       fone2,\n" +
                "       email\n" +
                "from cliente c\n" +
                "where c.cod_cliente like (?) or\n" +
                "        upper(unaccent(c.nome)) like upper(unaccent(?)) or\n" +
                "        upper(unaccent(c.fantasia)) like upper(unaccent(?)) or\n" +
                "        upper(unaccent(c.cidade)) like upper(unaccent(?))"

        return jdbc.query(sql, ClienteRowMapper(), "%$query%", "%$query%", "%$query%", "%$query%")

    }

    fun getClienteVersao(versao: String): List<Cliente> {
        var sql = "select id,\n" +
                "       versao,\n" +
                "       cod_cliente,\n" +
                "       nome,\n" +
                "       fantasia,\n" +
                "       endereco,\n" +
                "       bairro,\n" +
                "       cidade,\n" +
                "       uf,\n" +
                "       cep,\n" +
                "       fone1,\n" +
                "       fone2,\n" +
                "       email\n" +
                "from cliente c\n" +
                "where 1 =1 "

        sql += if (versao == "Não Informado") {
            " and c.versao is null "
        } else {
            " and c.versao = ('$versao')"
        }

        return jdbc.query(sql, ClienteRowMapper())

    }


    fun inserir(cliente: Cliente) {
        jdbc.update(INSERT,
                cliente.id,
                cliente.codCliente,
                cliente.nome,
                cliente.fantasia,
                cliente.endereco,
                cliente.bairro,
                cliente.cidade,
                cliente.uf,
                cliente.cep,
                cliente.fone,
                cliente.fone2,
                cliente.email,
                cliente.versao
        )
    }

    fun atualizar(cliente: Cliente) {
        jdbc.update(UPDATE,
                cliente.codCliente,
                cliente.nome,
                cliente.fantasia,
                cliente.endereco,
                cliente.bairro,
                cliente.cidade,
                cliente.uf,
                cliente.cep,
                cliente.fone,
                cliente.fone2,
                cliente.email,
                cliente.versao,
                cliente.id
        )
    }

    fun atualizarJFDados(cliente: Cliente) {
        //nome = ?, fantasia = ?, uf = ?, versao = ? where cod_cliente = ?
        jdbc.update(UPINSERT,
                cliente.nome,
                cliente.fantasia,
                cliente.uf,
                cliente.versao,
                cliente.codCliente
        )
    }

    fun getList(): List<Cliente> = jdbc.query("$SELECT ORDER BY id", ClienteRowMapper())

    fun getCliente(id: Int) = jdbc.queryForObject("$SELECT where id = ? ", ClienteRowMapper(), id)

    fun getCliente(codCliente: String) = jdbc.queryForObject("$SELECT where cod_cliente = ? ", ClienteRowMapper(), codCliente)

    fun getTotal(): Int = jdbc.queryForObject("select count(*) from cliente", Int::class.java)!!
    fun atualizaVersao(clienteID: Int, versao: String) {
        jdbc.update("update cliente set versao = ? where id = ? ", versao, clienteID)
    }

    internal class ClienteRowMapper : RowMapper<Cliente> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): Cliente {
            return Cliente(
                    id = rs.getInt("id"),
                    codCliente = rs.getString("cod_cliente"),
                    nome = rs.getString("nome"),
                    fantasia = rs.getString("fantasia"),
                    endereco = rs.getString("endereco"),
                    bairro = rs.getString("bairro"),
                    cidade = rs.getString("cidade"),
                    uf = rs.getString("uf"),
                    cep = rs.getString("cep"),
                    fone = rs.getString("fone1"),
                    fone2 = rs.getString("fone2"),
                    email = rs.getString("email"),
                    versao = rs.getString("versao")
            )
        }
    }

}