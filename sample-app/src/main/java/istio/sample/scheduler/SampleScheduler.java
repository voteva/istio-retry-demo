package istio.sample.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.lang.System.getenv;

@Slf4j
@Component
public class SampleScheduler {

    @Scheduled(cron = "*/10 * * * * *")
    public void printEnv() {
        log.info(getenv("SETBODY"));
    }
}
