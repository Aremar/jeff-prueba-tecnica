package jeff.technical.test.recommendationservice.common.feignutils;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import jeff.technical.test.recommendationservice.common.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;

/**
 * This class defines which error responses are retryable
 * and wraps them as RetryableException
 */
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        Exception exception = defaultErrorDecoder.decode(s, response);

        if(exception instanceof RetryableException){
            return exception;
        }

        if(HttpStatus.valueOf(response.status()) == HttpStatus.NOT_FOUND) {
            return new NotFoundException("External resource not found.");
        }

        if (HttpStatus.valueOf(response.status()).is5xxServerError()) {
            return new RetryableException(response.status(),"Server error", response.request().httpMethod(), null, response.request());
        }

        return exception;
    }
}
