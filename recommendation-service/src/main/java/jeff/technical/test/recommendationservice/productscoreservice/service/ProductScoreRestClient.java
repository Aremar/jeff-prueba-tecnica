package jeff.technical.test.recommendationservice.productscoreservice.service;

import jeff.technical.test.recommendationservice.productscoreservice.model.dto.ScoresApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "ProductScoreRest", url = "${product.scores.service.url}")
public interface ProductScoreRestClient {

    @RequestMapping(method = RequestMethod.GET, value = "/products/{productId}/score", produces = "application/json")
    ScoresApiResponse getProductScoreByIdAndPersona(@PathVariable("productId") Integer productId, @RequestParam(value="persona") String persona);

    @RequestMapping(method = RequestMethod.GET, value = "/scores", produces = "application/json")
    List<ScoresApiResponse> getAllProductScores();

}
