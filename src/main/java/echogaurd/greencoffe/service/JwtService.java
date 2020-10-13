package echogaurd.greencoffe.service;

import echogaurd.greencoffe.domain.Account;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.expmin}")
    private Long expireMin;

    public String create(final Account account) {
        final JwtBuilder builder = Jwts.builder();
        // JWT TOKEN = Header + Payload + Signature

        // Set Header
        builder.setHeaderParam("typ", "JWT");

        // Payload 설정 - claim 정보 포함
        builder.setSubject("Login Token")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
                .claim("Account",account);

        // signature - secret key를 이용한 암호화
        builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());

        // compact 처리
        final String jwt = builder.compact();
        return jwt;
    }

    public void checkValid(final String jwt) {
        Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
    }

    public Map<String,Object> get(final String jwt) {
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
        } catch (final Exception e) {
            throw new RuntimeException();
        }

        return claimsJws.getBody();
    }
}
