package com.palmeiras.aluguel.common;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
@Order(1)
public class LoginFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Add CORS headers
        res.setHeader("Access-Control-Allow-Origin", "*");  // allows requests from any domain, use specific domain to restrict
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "token, Content-Type");
        res.setHeader("Access-Control-Allow-Credentials", "true");

        // If it's a pre-flight request (OPTIONS method), just return OK status
        if (req.getMethod().equals("OPTIONS")) {
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String token = req.getHeader("token");
        String uri = req.getRequestURI();
        String method = req.getMethod();

        RestTemplate restTemplate = new RestTemplate();

        if (uri.equals("/v3/api-docs") || uri.contains("swagger")) {
            chain.doFilter(request, response);
            return;
        }

        if (req.getMethod().equals("OPTIONS")) {
            chain.doFilter(request, response);
        }

        try {
            ResponseEntity<TokenDTO> responseEntity = restTemplate.getForEntity("http://54.71.150.144:8082/token/" + token, TokenDTO.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "O token informado é inválido");
            }
        } catch (Exception e) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "O token informado é inválido");
        }
    }
}
