package br.com.oxent.sfarma.resource

import br.com.oxent.sfarma.model.entity.Usuario
import br.com.oxent.sfarma.model.entity.UsuarioPerfil
import br.com.oxent.sfarma.model.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid


@RestController
@RequestMapping("/api/usuarios")
class UsuarioResource @Autowired
constructor(private val usuarioService: UsuarioService) {

    @GetMapping
    fun getList() = ResponseEntity.ok(usuarioService.getList())

    @GetMapping("/total")
    fun getTotal() = ResponseEntity.ok(usuarioService.getList().size)

    @PostMapping
    fun set(@Valid @RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        usuarioService.set(usuario)
        return ResponseEntity.status(HttpStatus.OK).body(usuario)
    }

    @PostMapping("/perfil")
    fun setPerfil(@Valid @RequestBody usuario: UsuarioPerfil): ResponseEntity<UsuarioPerfil> {
        usuarioService.setPerfil(usuario)
        return ResponseEntity.status(HttpStatus.OK).body(usuario)
    }

    @GetMapping("/perfil")
    fun getUsuarioPerfil(principal: Principal) = usuarioService.getUsuarioPerfil(principal.name)

    @GetMapping("/perfil/list")
    fun getListPerfil(principal: Principal) = usuarioService.getListPerfil(principal.name)


}