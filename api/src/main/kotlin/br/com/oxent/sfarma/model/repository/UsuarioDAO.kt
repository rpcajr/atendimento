package br.com.oxent.sfarma.model.repository

import br.com.oxent.sfarma.model.entity.Usuario
import br.com.oxent.sfarma.model.entity.UsuarioPerfil
import getURLImage
import org.intellij.lang.annotations.Language
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class UsuarioDAO @Autowired
constructor(private val jdbc: JdbcTemplate) {
    companion object {
        @Language("PostgreSQL")
        const val SELECT = "SELECT  id, setor_id, nome, fone, email, nascimento, login, senha, foto, inativo, permissoes from usuario order by nome"
        @Language("PostgreSQL")
        const val SELECT_USUARIO = "SELECT  id, setor_id, nome, fone, email, nascimento, login, senha, foto, inativo, permissoes from usuario where upper(login) = upper(?) and  senha = ? and inativo = false"
        @Language("PostgreSQL")
        const val INSERT = "INSERT INTO usuario(id, setor_id, nome, fone, email, nascimento, login, senha, foto, permissoes, inativo) VALUES (?,?,?,?,?,?,?,?,?,?,?)"
        @Language("PostgreSQL")
        const val UPDATE = "update usuario  set nome = ? , login = ? , foto = ?, email = ? , inativo = ?, fone = ? , nascimento = ?, senha = ?, setor_id = ?, permissoes = ? where id = ? "
        const val NEXTVAL = "SELECT nextval('usuario_id_seq')"
    }

    fun getList(): List<Usuario> = jdbc.query(SELECT, UsuarioRowMapper())
    fun getUsuario(login: String, senha: String): Usuario? = jdbc.queryForObject(SELECT_USUARIO, UsuarioRowMapper(), login, senha)
    fun getUsuarioPerfil(login: String): UsuarioPerfil? = jdbc.queryForObject("select usu.id, usu.nome, usu.foto, usu.nascimento, s.nome as nome_setor from usuario usu inner join setor s on usu.setor_id = s.id where usu.login = ? ", UsuarioPerfilRowMapper(), login)
    fun getListPerfil(login: String): List<UsuarioPerfil> = jdbc.query("select usu.id, usu.nome, usu.foto, usu.nascimento, s.nome as nome_setor from usuario usu inner join setor s on usu.setor_id = s.id where usu.login <> ? ", UsuarioPerfilRowMapper(), login)
    fun getNextID(): Int = jdbc.queryForObject(NEXTVAL, Int::class.java)!!
    fun inserir(usuario: Usuario) {
        jdbc.update(INSERT,
                usuario.id,
                usuario.setorID,
                usuario.nome,
                usuario.fone,
                usuario.email,
                usuario.nascimento,
                usuario.login,
                usuario.senha,
                usuario.foto,
                usuario.permissoes,
                usuario.inativo)
    }
    fun atualizar(usuario: Usuario) {
        jdbc.update(UPDATE,
                usuario.nome,
                usuario.login,
                usuario.foto,
                usuario.email,
                usuario.inativo,
                usuario.fone,
                usuario.nascimento,
                usuario.senha,
                usuario.setorID,
                usuario.permissoes,
                usuario.id)
    }
    fun atualizarPerfil(usuario: UsuarioPerfil) {
        jdbc.update("update usuario  set nome =  ?, foto = ? where id = ? ", usuario.nome, usuario.foto, usuario.id)
    }

    internal class UsuarioRowMapper : RowMapper<Usuario> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): Usuario? {
            return Usuario(
                    id = rs.getInt("id"),
                    nome = rs.getString("nome"),
                    fone = rs.getString("fone"),
                    email = rs.getString("email"),
                    nascimento = rs.getDate("nascimento"),
                    login = rs.getString("login"),
                    senha = rs.getString("senha"),
                    foto = rs.getString("foto"),
                    inativo = rs.getBoolean("inativo"),
                    setorID = rs.getInt("setor_id"),
                    permissoes = rs.getString("permissoes"))
        }
    }

    internal class UsuarioPerfilRowMapper : RowMapper<UsuarioPerfil> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, i: Int): UsuarioPerfil {
            return UsuarioPerfil(
                    id = rs.getInt("id"),
                    nome = rs.getString("nome"),
                    foto = rs.getString("foto"),
                    setor = rs.getString("nome_setor"),
                    url = getURLImage(rs.getString("foto"))
            )
        }
    }

}