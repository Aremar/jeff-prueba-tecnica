package jeff.technical.test.recommendationservice.customerservice.service;

import jeff.technical.test.recommendationservice.customerservice.model.dto.CustomerApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "CustomerServiceRest", url = "${customer.service.url}")
public interface CustomerServiceRestClient {

    @RequestMapping(method = RequestMethod.GET, value = "/customers/{customerId}", produces = "application/json")
    CustomerApiResponse getCustomerInformationById(@PathVariable("customerId") Integer customerId);


}
