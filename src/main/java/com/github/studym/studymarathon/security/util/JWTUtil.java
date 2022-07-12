package com.github.studym.studymarathon.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
public class JWTUtil {
    @Autowired
    private UserDetailsService userDetailsService;

    private String secret = "studymarathon";
    byte[] secretBytes = DatatypeConverter.parseBase64Binary(secret);
    Key secretKey = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    //유효기간
    private long expire = 60 * 10;

    public String generateToken(Authentication authentication) throws Exception{

        Claims claims = Jwts.claims().setSubject(authentication.getName());

        Date now = new Date();
        Date expiresIn = new Date(now.getTime() + expire);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresIn)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAndExtract(String tokenStr) {
        String contentValue = null;

        try{

            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(tokenStr);
            return true;
        } catch (JwtException e) {
            e.printStackTrace();

            throw new JwtException("Error on Token");
        }
    }
}
