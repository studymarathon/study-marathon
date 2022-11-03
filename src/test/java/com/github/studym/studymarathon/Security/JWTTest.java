package com.github.studym.studymarathon.Security;

import com.github.studym.studymarathon.config.security.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;

@SpringBootTest
public class JWTTest {

    private JWTUtil jwtUtil;
    private SecureRandom secureRandom = new SecureRandom();


    @BeforeEach
    public void testBefore() {
        System.out.println("testBefore............");
        jwtUtil = new JWTUtil();
    }

    @Test
    public void testEncode() throws Exception {

        String str = "crowcrowcrowcrowcrowcrow";

        String key = jwtUtil.generateToken(str);

        System.out.println("-------testEncode------------------");
        System.out.println(key);
    }

    @Test
    public void testValidate() throws Exception {
        String email = "user5@studym.com";

        String str = jwtUtil.generateToken(email);

        Thread.sleep(5000);

        String resultEmail = jwtUtil.validateAndExtract(str);

        System.out.println(resultEmail);
    }
}
