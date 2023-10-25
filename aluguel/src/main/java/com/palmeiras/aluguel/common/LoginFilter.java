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

        String token = req.getHeader("token");

        String uri = req.getRequestURI();
        String method = req.getMethod();

        RestTemplate restTemplate = new RestTemplate();

        

        try {
            ResponseEntity<TokenDTO> responseEntity = restTemplate.getForEntity("http://54.71.150.144:8082/token/" + token, TokenDTO.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                chain.doFilter(request, response);
            }
            else {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "O token informado é inválido");
            }
        } catch (Exception e) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "O token informado é inválido");
        }

    }

}
