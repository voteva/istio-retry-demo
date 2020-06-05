package com.voteva.istio.retry.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class HelperService {
    private volatile AtomicInteger requestCount = new AtomicInteger();

    public int getRandomStatusCode() {
        if (requestCount.incrementAndGet() % 2 == 0) {
            log.info("Returning error response");
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        } else {
            log.info("Returning success response");
            return HttpStatus.OK.value();
        }
    }
}
