package istio.mirroring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class TestEndpoint {

    @RequestMapping("test")
    public Mono<Void> health() {
        log.info("Request received");
        return Mono.empty();
    }
}
