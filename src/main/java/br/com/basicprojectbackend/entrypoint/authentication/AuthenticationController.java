package br.com.basicprojectbackend.entrypoint.authentication;

import br.com.basicprojectbackend.config.security.JwtGenerator;
import br.com.basicprojectbackend.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    private final JwtGenerator jwtGenerator;

    public AuthenticationController(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            log.info("Authenticating");
            return new ResponseEntity<>(jwtGenerator.generateToken(user), HttpStatus.OK);
        } catch (Exception e) {
            //TODO trocar para status UNAUTHORIZED
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
