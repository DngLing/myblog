package com.my.blog;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 19:37 2019/5/16
 * @Modified By:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Token {
    private static Logger logger = LoggerFactory.getLogger(Token.class);
    @Test
    public void test() {
        Date now = new Date();
        Instant instant = LocalDateTime.now().plusSeconds(60).atZone(ZoneId.systemDefault()).toInstant();
        Date exp = Date.from(instant);
        String token = "";
        long n = System.currentTimeMillis();
        token = JWT.create().withAudience("username").withClaim("role","admin").withClaim("us",n).withExpiresAt(exp).sign(Algorithm.HMAC256("password"));
        logger.info("token1: "+token);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        n = System.currentTimeMillis();
        String token2 = JWT.create().withAudience("username").withClaim("role","admin").withClaim("us",n).withExpiresAt(exp).sign(Algorithm.HMAC256("password"));
        logger.info("token2: "+ token2);
        boolean is = token2.endsWith(token);
        logger.info("is token1 == token2? "+ is);
    }

    @Test
    public void verify() {
        try {
            String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ1c2VybmFtZSIsInJvbGUiOiJhZG1pbiIsImV4cCI6MTU1ODAwOTE3M30.PVmE5BNNI5gf_9HIuS1veNlMHqtMRVBYROlp5_u0El0";
            Algorithm algorithm = Algorithm.HMAC256("password");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String role = JWT.decode(token).getClaim("role").asString();
            logger.info("role: "+role);
            if(jwt.getExpiresAt().before(new Date())){
                logger.info("token 过期了");
                return;
            }
        }catch (JWTVerificationException e){
            if(e instanceof TokenExpiredException){
                logger.info("token 过期了");
            }
            e.printStackTrace();
        }
    }
}