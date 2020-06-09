package com.voteva.istio.retry.controller;

import com.voteva.istio.retry.service.HelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static java.lang.String.format;
import static java.time.Duration.ofMillis;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EndpointController {

    private final HelperService helperService;

    @RequestMapping("random")
    public ResponseEntity<Mono<String>> withRandomResponse(@RequestParam(value = "durationMs", defaultValue = "0") long durationMs) {
        int statusCode = helperService.getRandomStatusCode();
        log.info("Path: '{}', Duration: '{}', Status code: '{}'", "random", durationMs, statusCode);
        return ResponseEntity
                .status(statusCode)
                .body(Mono.just(format("DurationMs: %s, statusCode: %s", durationMs, statusCode))
                        .delayElement(ofMillis(durationMs)));
    }

    @RequestMapping(value = {"/", "/{path}"})
    public ResponseEntity<Mono<String>> withStatus(@PathVariable(value = "path", required = false) String path,
                                                   @RequestParam(value = "statusCode", defaultValue = "200") int statusCode,
                                                   @RequestParam(value = "durationMs", defaultValue = "0") long durationMs) {
        log.info("Path: '{}', DurationMs: '{}', StatusCode '{}'", path, durationMs, statusCode);
        return ResponseEntity
                .status(statusCode)
                .body(Mono.just(format("DurationMs: %s, statusCode: %s", durationMs, statusCode))
                        .delayElement(ofMillis(durationMs)));
    }
}
