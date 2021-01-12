package br.com.oxent.sfarma.config

import br.com.oxent.sfarma.model.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component


@Component
class AuthenticationProvider @Autowired
constructor(private val usuarioService: UsuarioService) : AuthenticationManager {
    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val usuario = usuarioService.getUsuario(authentication.name, authentication.credentials.toString()) ?: throw BadCredentialsException("Login ou Senha inválidos")
        val user = User(usuario.login, usuario.senha, AuthorityUtils.commaSeparatedStringToAuthorityList(usuario.permissoes))
        return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }
}