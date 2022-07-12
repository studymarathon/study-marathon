package com.github.studym.studymarathon.security.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

    // private  String secretKey = "studymarathon";

    // specified key byte array is 256 bits 이상으로 지정해야함
    private Key key = Keys.hmacShaKeyFor("studymarathon12345678studymarathon12345678".getBytes(StandardCharsets.UTF_8));
    /*Key secretKey = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());*/
    //유효기간
    private long expire = 60 * 10;

    public String generateToken(String authentication) throws Exception{


/*        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresIn)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();*/

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
                .setSubject("Test")
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAndExtract(String tokenStr) {
        String contentValue = null;

        try{

            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenStr);
            return true;
        } catch (JwtException e) {
            e.printStackTrace();

            throw new JwtException("Error on Token");
        }
    }
}
