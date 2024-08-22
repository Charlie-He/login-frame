package org.example.loginframe.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtil {

    //    @Value("${jwt.secret}")
    public static String secret="ahubasbnjai";

    public static Long expire=259200000L;//过期时间设置为三天
    public static String createJwt(UserDetails user, String username, int id){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //一般来讲，jwt中只用存id就可以了
        return JWT.create()
                .withClaim("id",id)
                .withClaim("username",username)
                .withClaim("authorities",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+expire))
                .sign(algorithm);
    }

    public static UserDetails resolveJwt(String token){
        //创建解析对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            Map<String, Claim> claims = verify.getClaims();
            if(new Date().after(claims.get("exp").asDate()))
                return null;
            else {
                return User
                        .withUsername(claims.get("username").toString())
                        .password("**********")
                        .authorities(claims.get("authorities").asArray(String.class))
                        .build();
            }
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
