package istio.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class SampleController {

    @RequestMapping("test")
    public Mono<Void> test() {
        log.info("Test");
        return Mono.empty();
    }
}
