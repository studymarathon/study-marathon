package com.github.studym.studymarathon.config.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Random;
import java.util.random.RandomGenerator;


@Log4j2
public class JWTUtil {

    // TODO: 2022-08-02 key 생성시  SecureRandom 사용해 항상 랜덤한 키 배정후 토큰 발급
//  specified key byte array is 256 bits 이상으로 지정해야함
//
    RandomGenerator gen = RandomGenerator.of("L128X256MixRandom");
    Random r = new Random();

    byte[] b = new byte[256];


    int s = 333;


    // private  String secretKey = "studymarathon"; @Deprecated 되서 String형이 필요없음
    @Autowired
    private UserDetailsService userDetailsService;
    private Key rkey = Keys.hmacShaKeyFor(nextBytes(b));
    private Key key = Keys.hmacShaKeyFor("aaaabbbbaaaabbbbaaaabbbbaaaabbbb".getBytes(StandardCharsets.UTF_8));
    //유효기간
    private long expire = 60 * 10;

    public byte[] nextBytes(byte[] bytes) {
        for (int i = 0; i < bytes.length; )
            for (int rnd = gen.nextInt(), n = Math.min(bytes.length - i, 4);
                 n-- > 0; rnd >>= 8)
                bytes[i++] = (byte) rnd;

        return bytes;
    }

    public String generateToken(String authentication) throws Exception {


        System.out.println("6오^");
        System.out.println(nextBytes(b));
        System.out.println("---------------------------------------------");
        System.out.println(rkey.getEncoded());
        System.out.println(rkey.getFormat());

        /*        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresIn)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();*/// 위 방식이 @Deprecated되서 아래의 방식으로 사용해야함 */

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
                .setSubject(authentication)
                .signWith(rkey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateAndExtract(String tokenStr) {
        String contentValue = null;

        try {

            Jws jws = Jwts.parserBuilder().setSigningKey(rkey).build().parseClaimsJws(tokenStr);
            log.info("--------------------------------");

            log.info(jws);
            log.info(jws.getBody().getClass());

            Claims claims = (Claims) jws.getBody();
            log.info("로그" + claims);

            log.info("--------------------------------");

            contentValue = claims.getSubject();


        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            contentValue = null;


        }
        return contentValue;
    }


}
