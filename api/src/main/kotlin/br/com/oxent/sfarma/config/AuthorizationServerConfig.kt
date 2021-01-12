package br.com.oxent.sfarma.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync

import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore


@Configuration
@EnableAsync
class AuthorizationServerConfig @Autowired
constructor(private val authenticationManager: AuthenticationProvider) : AuthorizationServerConfigurerAdapter() {

    @Value("\${ox.token.secret}")
    private val secret: String? = null

    @Value("\${ox.token.expiration}")
    private val expiration: Int = 0

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient("angular")
                .secret("$2a$10$4CvdsdqhNu/A1ERtlyqOYeSbwnRbL7xCbPclZ7k3o6HvWw0oU3v1u")
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(expiration)
                .refreshTokenValiditySeconds(3600 * 24)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .reuseRefreshTokens(false)
                .authenticationManager(authenticationManager)
    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val accessTokenConverter = JwtAccessTokenConverter()
        accessTokenConverter.setSigningKey(secret)
        return accessTokenConverter
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JwtTokenStore(accessTokenConverter())
    }

}
