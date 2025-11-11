package com.api.GS.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utilitário para gerenciamento de tokens JWT
 * - Geração de tokens
 * - Validação de tokens
 * - Extração de informações (claims)
 */
@Component
public class JwtUtil {



    private String secretKey  = "qR6tY7cLh5F8k+S4v1sD3v9uG0hJ2mK8lP4nB7oQ6xM=";


    private static final long expirationTime = 1000 * 60 * 60; // 1 hora

    // Chave de assinatura (gerada a partir da secretKey)
    private SecretKey signingKey;

    /**
     * Inicializa a chave de assinatura após injeção das propriedades
     */
    @PostConstruct
    public void init() {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException(
                    "❌ ERRO: jwt.secret não configurada no application.properties!\n" +
                            "Adicione: jwt.secret=SuaChaveSecretaAqui"
            );
        }

        // Gerar chave de assinatura a partir da secretKey
        this.signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        System.out.println("✅ JwtUtil inicializado com sucesso!");
        System.out.println("⏰ Tempo de expiração: " + (expirationTime / 1000 / 60) + " minutos");
    }

    /**
     * Gera um token JWT para o email do usuário
     *
     * @param email Email do usuário
     * @return Token JWT gerado
     */
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("timestamp", System.currentTimeMillis());

        return createToken(claims, email);
    }

    /**
     * Cria o token JWT com claims personalizados
     *
     * @param claims Informações adicionais do token
     * @param subject Assunto do token (geralmente o email)
     * @return Token JWT completo
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida o token JWT e verifica se pertence ao email informado
     *
     * @param token Token JWT
     * @param email Email do usuário
     * @return true se válido, false caso contrário
     */
    public Boolean validateToken(String token, String email) {
        try {
            final String extractedEmail = extractUsername(token);
            return (extractedEmail.equals(email) && !isTokenExpired(token));
        } catch (Exception e) {
            System.err.println("❌ Erro ao validar token: " + e.getMessage());
            return false;
        }
    }

    /**
     * Extrai o username (email) do token
     *
     * @param token Token JWT
     * @return Email do usuário
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrai a data de expiração do token
     *
     * @param token Token JWT
     * @return Data de expiração
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrai um claim específico do token
     *
     * @param token Token JWT
     * @param claimsResolver Função para extrair o claim desejado
     * @return Valor do claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrai todos os claims do token
     *
     * @param token Token JWT
     * @return Objeto Claims com todas as informações
     * @throws JwtException Se o token for inválido
     */
    private Claims extractAllClaims(String token) throws JwtException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token expirado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Token não suportado: " + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new JwtException("Token malformado: " + e.getMessage());
        } catch (SignatureException e) {
            throw new JwtException("Assinatura inválida: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new JwtException("Token vazio ou nulo: " + e.getMessage());
        }
    }

    /**
     * Verifica se o token está expirado
     *
     * @param token Token JWT
     * @return true se expirado, false caso contrário
     */
    private Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            return true; // Se houver erro ao extrair expiração, considerar expirado
        }
    }

    /**
     * Obtém informações detalhadas do token (para debug)
     *
     * @param token Token JWT
     * @return String com informações do token
     */
    public String getTokenInfo(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return String.format(
                    "Token Info:\n" +
                            "  Subject: %s\n" +
                            "  Issued At: %s\n" +
                            "  Expiration: %s\n" +
                            "  Expired: %s",
                    claims.getSubject(),
                    claims.getIssuedAt(),
                    claims.getExpiration(),
                    isTokenExpired(token) ? "SIM" : "NÃO"
            );
        } catch (Exception e) {
            return "Erro ao obter informações do token: " + e.getMessage();
        }
    }

    /**
     * Renova o token (gera um novo com o mesmo email)
     *
     * @param token Token JWT antigo
     * @return Novo token JWT
     */
    public String refreshToken(String token) {
        try {
            String email = extractUsername(token);
            return generateToken(email);
        } catch (Exception e) {
            throw new JwtException("Erro ao renovar token: " + e.getMessage());
        }
    }

    /**
     * Verifica se o token é válido sem comparar com email
     *
     * @param token Token JWT
     * @return true se válido, false caso contrário
     */
    public Boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }


}
