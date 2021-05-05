package jeff.technical.test.recommendationservice.common.exceptions;

import feign.FeignException;
import lombok.Getter;

import java.util.UUID;

@Getter
public class NotFoundException extends FeignException {
    private final UUID uuid;

    public NotFoundException(String message){
        super(404, message);
        this.uuid = UUID.randomUUID();
    }
}
