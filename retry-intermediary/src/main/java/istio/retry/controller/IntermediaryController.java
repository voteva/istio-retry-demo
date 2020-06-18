package istio.retry.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static java.time.Duration.ofMillis;

@Slf4j
@RestController
@RequiredArgsConstructor
public class IntermediaryController {

    @Value("${endpoint.host}")
    private String endpointHost;

    private final WebClient webClient;

    @RequestMapping
    public Mono<String> withDuration(@RequestParam(value = "durationMsIntermediary", defaultValue = "0") long durationMsIntermediary) {
        log.info("Receive request. DurationMs: '{}'", durationMsIntermediary);
        return Mono.just("Duration ms: " + durationMsIntermediary)
                .delayElement(ofMillis(durationMsIntermediary));
    }

    @RequestMapping("chain")
    public Mono<String> withDurationToIntermediary(@RequestParam(value = "path", defaultValue = "/") String path,
                                                   @RequestParam(value = "statusCode", defaultValue = "200") int statusCode,
                                                   @RequestParam(value = "method", defaultValue = "GET") HttpMethod method,
                                                   @RequestParam(value = "durationMs", defaultValue = "0") long durationMs) {
        log.info("Receive request. DurationMs: '{}'. StatusCode: '{}'. Method: '{}', Path: '{}'", durationMs, statusCode, method, path);
        return webClient.method(method)
                .uri(uriBuilder ->
                        uriBuilder
                                .host(endpointHost)
                                .path(path)
                                .queryParam("durationMs", String.valueOf(durationMs))
                                .queryParam("statusCode", statusCode)
                                .build())
                .exchange()
                .flatMap(clientResponse -> {
                    if (!clientResponse.statusCode().is2xxSuccessful()) {
                        clientResponse.body((clientHttpResponse, context) -> clientHttpResponse.getBody());
                    }
                    return clientResponse.bodyToMono(String.class);
                });
    }
}
