package jeff.technical.test.recommendationservice.common.feignutils;

import feign.RetryableException;
import feign.Retryer;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class creates a way of retry a call to an api with Feign Client a provided number of times
 * for an specific type of Exception
 */
@Slf4j
@Component
@NoArgsConstructor
public class FeignRetryer implements Retryer {

    @Value("${feign.retry.attempts}")
    private int maxAttempts;

    @Value("${feign.retry.interval}")
    private long interval;

    private int attempt;


    public FeignRetryer(int maxAttempts, Long interval) {
        this.maxAttempts = maxAttempts;
        this.interval = interval;
        this.attempt = 1;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        log.info("Feign retry attempt {} due to {} ", attempt, e.getMessage());

        if(attempt++ == maxAttempts){
            throw e;
        }
        try {
            Thread.sleep(interval);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }

    }

    @Override
    public Retryer clone() {
        return new FeignRetryer(maxAttempts, interval);
    }
}