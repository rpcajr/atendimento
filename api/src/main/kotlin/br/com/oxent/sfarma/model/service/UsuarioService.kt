package br.com.oxent.sfarma.model.service


import br.com.oxent.sfarma.model.entity.Usuario
import br.com.oxent.sfarma.model.entity.UsuarioPerfil
import br.com.oxent.sfarma.model.repository.UsuarioDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class UsuarioService @Autowired
constructor(private val usuarioDAO: UsuarioDAO, private val fileService: FileService) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails? {
        return null
    }

    fun getUsuario(login: String, senha: String): Usuario? {
        return try {
            usuarioDAO.getUsuario(login, senha)
        } catch (e: EmptyResultDataAccessException) {
            getUsuarioSistema(login, senha)
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun set(usuario: Usuario) {
        if (usuario.id == null) {
            usuario.id = getNextID()
            usuarioDAO.inserir(usuario)
        } else {
            usuarioDAO.atualizar(usuario)
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun getNextID() = usuarioDAO.getNextID()

    fun getList() = usuarioDAO.getList()

    fun getUsuarioPerfil(login: String): UsuarioPerfil? {


        if (login == "admin") {
            return UsuarioPerfil(
                    id = 0,
                    nome = "GERENTE",
                    foto = "",
                    setor = "JF",
                    url = ""
            )
        }
        return usuarioDAO.getUsuarioPerfil(login)
    }


    fun getListPerfil(login: String): List<UsuarioPerfil> {
        return try {
            usuarioDAO.getListPerfil(login)
        } catch (e: EmptyResultDataAccessException) {
            emptyList()
        }
    }


    fun getUsuarioSistema(login: String, senha: String): Usuario? {
        if (login == "admin" && senha == "senha@123") {
            return Usuario(
                    id = 0,
                    nome = "administrador",
                    fone = "",
                    email = "",
                    nascimento = null,
                    login = "admin",
                    senha = "senha@123",
                    foto = "",
                    inativo = false,
                    setorID = 0,
                    permissoes = "0,1,2,3,4,5")
        } else {
            return null
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun setPerfil(usuario: UsuarioPerfil) {
        usuarioDAO.atualizarPerfil(usuario)
    }
}
