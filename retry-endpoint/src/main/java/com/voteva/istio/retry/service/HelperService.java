package com.voteva.istio.retry.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HelperService {
    private volatile AtomicInteger requestCount = new AtomicInteger();

    public int getRandomStatusCode() {
        if (requestCount.incrementAndGet() % 2 == 0) {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        } else {
            return HttpStatus.OK.value();
        }
    }
}
