package br.com.basicprojectbackend.config.security.impl;

import br.com.basicprojectbackend.config.security.JwtGenerator;
import br.com.basicprojectbackend.domain.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtGeneratorImpl implements JwtGenerator {

    private final String secret;
    private final String message;

    public JwtGeneratorImpl(@Value("${jwt.secret}") String secret, @Value("${app.jwttoken.message}") String message) {
        this.secret = secret;
        this.message = message;
    }

    @Override
    public Map<String, String> generateToken(User user) {
        var jwtToken="";
        var expiresIn = 3600000;

        //TODO criptografar o token antes de setar
        //TODO nao passar o user inteiro no claim, pensar no que será passado
        jwtToken = Jwts.builder()
                .claim("user", user.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiresIn)) //1 hora
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("token", jwtToken);
        jwtTokenGen.put("message", message);
        jwtTokenGen.put("expiresIn", String.valueOf(expiresIn + "ms"));
        //TODO adicionar msgs adicionais

        return jwtTokenGen;
    }
}
