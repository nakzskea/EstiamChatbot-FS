package edu.ban7.e3chatbotback.controller;

import edu.ban7.e3chatbotback.dao.AppUserDao;
import edu.ban7.e3chatbotback.model.AppUser;
import edu.ban7.e3chatbotback.security.AppUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    protected AppUserDao appUserDao;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected AuthenticationProvider authenticationProvider;

    @PostMapping("/sign-in")
    public ResponseEntity<Void> inscription(@RequestBody @Validated(AppUser.OnCreate.class) AppUser user) {

        //on hash le mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        appUserDao.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Validated(AppUser.OnLogin.class) AppUser user) {

        try {
            AppUserDetails userDetails = (AppUserDetails)authenticationProvider
                    .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(),
                                user.getPassword()))
                    .getPrincipal();

            String jwt = Jwts.builder()
                    .setSubject(user.getEmail())
                    .addClaims(Map.of("role", userDetails.getUser().isAdmin() ? "admin": "user"))
                    .signWith(SignatureAlgorithm.HS256, "secret")
                    .compact();

            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
