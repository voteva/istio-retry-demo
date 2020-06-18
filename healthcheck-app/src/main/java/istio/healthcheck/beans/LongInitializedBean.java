package istio.healthcheck.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class LongInitializedBean {
    private static final long DELAY_MILLIS = 15_000;

    @PostConstruct
    public void init() {
        log.info("Creating LongInitializedBean ...");

        try {
            Thread.sleep(DELAY_MILLIS);
            log.info("LongInitializedBean successfully created");

        } catch (InterruptedException e) {
            log.info("LongInitializedBean creation failed");
            throw new RuntimeException("Failed to create LongInitializedBean");
        }
    }
}
