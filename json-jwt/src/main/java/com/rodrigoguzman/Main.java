package com.rodrigoguzman;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class Main {
    public static void main(String[] args) {
        //construimos extra claims
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", "prueba jwt");
            
        //construimos jwt
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (1 * 60 * 1000));
            
        String jwt;
        jwt = Jwts.builder()

                //cabecera
                .header()
                .type("JWT")
                .and()

                //payload
                .subject("pruebajwt")
                .expiration(expiration)
                .issuedAt(issuedAt)
                .claims(extraClaims)

                //firma
                .signWith(generateKey(), Jwts.SIG.HS256)

                .compact();
                    
        //vemos nuestro jwt generado por pantalla
        System.out.println(jwt);
    }
        
    public static SecretKey generateKey() {
        //tiene que ser larga porque dijimos que cumple con HS256
        String secretKey = "tengo que generar una key que sea larga 1234556789 suscribite a TodoCode";
        
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}