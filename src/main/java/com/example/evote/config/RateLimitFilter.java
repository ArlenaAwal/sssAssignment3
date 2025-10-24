package com.example.evote.config;
import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.stereotype.Component; import java.io.IOException; import java.time.Instant; import java.util.Map; import java.util.concurrent.ConcurrentHashMap;
@Component
public class RateLimitFilter implements Filter {
  private final Map<String, Window> buckets = new ConcurrentHashMap<>();
  private static final int MAX_REQUESTS = 60; // per minute per IP/path
  @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest)request; HttpServletResponse res = (HttpServletResponse)response;
    String key = req.getRemoteAddr()+":"+req.getRequestURI(); Window w = buckets.computeIfAbsent(key, k -> new Window());
    long now = Instant.now().getEpochSecond(); if(now - w.start >= 60){ w.start = now; w.count = 0; }
    if(++w.count > MAX_REQUESTS){ res.setStatus(429); res.getWriter().write("Too Many Requests"); return; }
    chain.doFilter(request, response);
  }
  static class Window { volatile long start = Instant.now().getEpochSecond(); volatile int count = 0; }
}
