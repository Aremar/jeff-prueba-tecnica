package jeff.technical.test.recommendationservice.customerservice.model.dto;

import java.util.List;

public record CustomerApiResponse(Integer id, String name, String email, String persona, List<Integer> subscriptions) {
}
