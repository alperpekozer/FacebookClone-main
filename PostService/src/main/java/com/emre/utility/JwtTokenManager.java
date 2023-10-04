package com.emre.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {

    //@Value("${my-application.jwt.secret-key}")
    //private String secretKey;

    private final String sifreAnahtari="fr-geBr_j6Co+*t5Iz#jl74U?a6oVoswl8lTreb4_g*sT-sof?&?R9y6!epriw@!";

    private Long exDate=1000L*60*2; //2 dakika boyunca geçerli olucak demek için 2dk yazdık

    public Optional<String> createToken(Long id){
        String token;
        try{
            token= JWT.create().withAudience()
                    .withClaim("id",id) //payload dataları
                    .withClaim("howtopage", "AuthMicroService") //payload dataları
                    .withClaim("isOne", true) //payload dataları
                    .withIssuer("Java7")   //token ureten uygulama
                    .withIssuedAt(new Date()) //token oluştuurlma tarihi
                    .withExpiresAt(new Date(System.currentTimeMillis()+exDate)) //tokenin sona erme zamanı
                    .sign(Algorithm.HMAC512(sifreAnahtari));  //token algoritması
            return Optional.of(token);
        }catch(Exception e){
            return Optional.empty();
        }
    }
    public Optional<Long> getIdFromToken(String token){
        try{
            Algorithm algorithm=Algorithm.HMAC512(sifreAnahtari);
            JWTVerifier verifier= JWT.require(algorithm)
                    .withIssuer("Java7")
                    .build();
            DecodedJWT decodedJWT =verifier.verify(token);
            if (decodedJWT==null) return Optional.empty();
            return Optional.of(decodedJWT.getClaim("id").asLong());
        }catch (Exception e){
            return Optional.empty();
        }
    }



















}
