package br.com.basicprojectbackend.config.security;

import br.com.basicprojectbackend.domain.model.User;

import java.util.Map;

public interface JwtGenerator {
    Map<String, String> generateToken(User user);
}
