package com.gym.gym.filters;

import org.springframework.util.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

public class TransactionIdFilter implements Filter {

    private static final String TRANSACTION_ID_HEADER = "Transaction-ID";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String transactionId = httpRequest.getHeader(TRANSACTION_ID_HEADER);
        if (StringUtils.hasText(transactionId)) {
            MDC.put("transactionId", transactionId);
        } else {
            MDC.put("transactionId", UUID.randomUUID().toString());
        }
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove("transactionId");
        }
    }
}

