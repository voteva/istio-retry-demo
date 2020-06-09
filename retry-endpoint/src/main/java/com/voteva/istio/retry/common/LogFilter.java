package com.voteva.istio.retry.common;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class LogFilter implements WebFilter {
    private static final String SPAN_ID = "spanId";
    private static final String HEADER_X_B3_SPANID = "x-b3-spanid";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .doOnSubscribe(s -> {
                    String spanId = exchange.getRequest().getHeaders().getFirst(HEADER_X_B3_SPANID);
                    MDC.put(SPAN_ID, spanId);
                });
    }
}
