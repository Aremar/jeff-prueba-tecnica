package jeff.technical.test.recommendationservice.model.dto;

import java.util.List;

public record Customer(Integer id, String persona, List<Integer> subscriptions) {

}
