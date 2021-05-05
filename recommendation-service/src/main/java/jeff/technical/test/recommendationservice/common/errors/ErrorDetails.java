package jeff.technical.test.recommendationservice.common.errors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record ErrorDetails(String timestamp, int status, String error, String message, String path) {
}
