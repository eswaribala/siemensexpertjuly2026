package com.boa.userservice.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
/*
@Component @Order(HIGHEST_PRECEDENCE)
public class DbFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws java.io.IOException, ServletException {
        String db = req.getHeader("X-DB"); // "paymentdb" or "loandb"
        if (!"paymentdb".equals(db) && !"loandb".equals(db)) db = "paymentdb"; // default
        DbContext.set(db);
        try { chain.doFilter(req, res); } finally { DbContext.clear(); }
    }
}
*/