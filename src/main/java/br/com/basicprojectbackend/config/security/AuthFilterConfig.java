package br.com.basicprojectbackend.config.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthFilterConfig {

    private final JwtFilter jwtFilter;

    public AuthFilterConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> authFilter() {

        var filter= new FilterRegistrationBean<JwtFilter>();
        filter.setFilter(jwtFilter);

        filter.addUrlPatterns("/api/*");
        filter.addInitParameter("exclusions", "/auth/login");
        return filter;
    }
}
