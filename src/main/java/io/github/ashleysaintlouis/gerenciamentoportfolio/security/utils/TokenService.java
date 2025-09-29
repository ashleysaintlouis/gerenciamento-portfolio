//package io.github.ashleysaintlouis.gerenciamentoportfolio.security.utils;
//
//import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Usuario;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import jakarta.annotation.PostConstruct;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Date;
//
//
//@Component
//public class TokenService {
//
//    @Value("${jwt.secret}")
//    private String jwtSecret;
//
//    @Value("${jwt.expiration}")
//    private long jwtExpiration;
//
//    private Key chaveAssinatura;
//
//    @PostConstruct
//    public void init() {
//        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
//
//        if (keyBytes.length < 64) {
//            throw new IllegalArgumentException("A chave secreta deve ter pelo menos 64 bytes para HS512.");
//        }
//
//        this.chaveAssinatura = Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public String gerarToken(Usuario usuario) {
//
//        return Jwts.builder()
//                .setSubject(usuario.getEmail())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
//                .signWith(chaveAssinatura, SignatureAlgorithm.HS512)
//                .compact();
//    }
//
//    public String validarToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(chaveAssinatura)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    public boolean isTokenValido(String token, UserDetails userDetails) {
//        final String username = validarToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    public boolean isTokenExpired(String token) {
//        Date expiration = Jwts.parserBuilder()
//                .setSigningKey(chaveAssinatura)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration();
//
//        return expiration.before(new Date());
//    }
//}
