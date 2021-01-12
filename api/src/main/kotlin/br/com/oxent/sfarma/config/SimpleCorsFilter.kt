package br.com.oxent.sfarma.config

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class SimpleCorsFilter : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val response = res as HttpServletResponse
        val request = req as HttpServletRequest
        response.setHeader("Access-Control-Allow-Credentials", "true")
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"))


        if ("OPTIONS" == request.method) {
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS")
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept")
            response.setHeader("Access-Control-Max-Age", "3600")
            response.status = HttpServletResponse.SC_OK
        } else {
            chain.doFilter(req, response)
        }

    }

    override fun init(filterConfig: FilterConfig?) {}

    override fun destroy() {}
}