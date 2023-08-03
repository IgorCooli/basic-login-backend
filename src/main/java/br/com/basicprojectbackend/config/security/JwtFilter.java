package br.com.basicprojectbackend.config.security;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {

    private final String secret;

    public JwtFilter(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        final var request = (HttpServletRequest) servletRequest;
        final var response = (HttpServletResponse) servletResponse;
        final var authHeader = request.getHeader("authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("An exception occurred");
        }

        final var token = authHeader.substring(7);
        var claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        request.setAttribute("claims", claims);
        //TODO verificar a questao do claim, o que faz e como configurar?
        request.setAttribute("blog", servletRequest.getParameter("id"));

        filterChain.doFilter(request, response);
    }
}