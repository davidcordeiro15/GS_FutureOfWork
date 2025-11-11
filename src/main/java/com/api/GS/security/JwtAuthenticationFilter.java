package com.api.GS.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Permitir endpoints públicos
        if (isPublicEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        // Validar presença do header
        if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
            sendUnauthorizedResponse(response, "Token ausente");
            return;
        }

        // Validar formato do header
        if (!authorizationHeader.startsWith("Bearer ")) {
            sendUnauthorizedResponse(response, "Formato de token inválido. Use: Bearer <token>");
            return;
        }

        try {

            String token = authorizationHeader.substring(7).trim(); // Remove "Bearer " e espaços

            // Validar se token não está vazio após limpeza
            if (token.isEmpty()) {
                sendUnauthorizedResponse(response, "Token vazio");
                return;
            }

            // Log do token (apenas primeiros caracteres para segurança)
            System.out.println("🔍 Token recebido (primeiros 20 chars): " +
                    (token.length() > 20 ? token.substring(0, 20) + "..." : token));

            // Extrair email do token
            String email = jwtUtil.extractUsername(token);

            // Validar token
            if (email != null && !email.isEmpty() && jwtUtil.validateToken(token, email)) {
                // Criar autenticação
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println(" Token válido para: " + email);

                // Continuar com a requisição
                filterChain.doFilter(request, response);
                return;

            } else {
                sendUnauthorizedResponse(response, "Token inválido ou expirado");
                return;
            }

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.err.println(" Token expirado: " + e.getMessage());
            sendUnauthorizedResponse(response, "Token expirado. Faça login novamente.");

        } catch (io.jsonwebtoken.MalformedJwtException e) {
            System.err.println(" Token malformado: " + e.getMessage());
            sendUnauthorizedResponse(response, "Token malformado. Formato inválido.");

        } catch (io.jsonwebtoken.SignatureException e) {
            System.err.println(" Assinatura inválida: " + e.getMessage());
            sendUnauthorizedResponse(response, "Assinatura do token inválida.");

        } catch (IllegalArgumentException e) {
            System.err.println(" Token com caracteres inválidos: " + e.getMessage());
            sendUnauthorizedResponse(response, "Token contém caracteres inválidos: " + e.getMessage());

        } catch (Exception e) {
            System.err.println(" Erro ao processar token: " + e.getMessage());
            e.printStackTrace();
            sendUnauthorizedResponse(response, "Erro ao validar token: " + e.getMessage());
        }
    }

    /**
     * Verifica se o endpoint é público (não requer autenticação)
     */
    private boolean isPublicEndpoint(String path) {
        return path.equals("/api/login") ||
                path.equals("/api/register") ||
                path.equals("/api/auth/login") ||
                path.equals("/api/auth/register") ||
                path.startsWith("/public/") ||
                path.equals("/error") ||
                path.equals("/");
    }

    /**
     * Envia resposta de não autorizado
     */
    private void sendUnauthorizedResponse(HttpServletResponse response, String mensagem)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = String.format(
                "{\"erro\": \"%s\", \"status\": 401}",
                mensagem.replace("\"", "\\\"")
        );

        response.getWriter().write(jsonResponse);
    }
}