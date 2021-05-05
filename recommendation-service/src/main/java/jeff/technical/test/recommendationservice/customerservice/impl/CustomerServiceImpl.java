package jeff.technical.test.recommendationservice.customerservice.impl;

import jeff.technical.test.recommendationservice.common.exceptions.NotFoundException;
import jeff.technical.test.recommendationservice.customerservice.api.CustomerService;
import jeff.technical.test.recommendationservice.customerservice.model.dto.CustomerApiResponse;
import jeff.technical.test.recommendationservice.customerservice.service.CustomerServiceRestClient;
import jeff.technical.test.recommendationservice.model.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerServiceRestClient client;

    @Override
    public Customer getCustomerInfoForRecommendation(Integer customerId) throws NotFoundException {
        try {
            CustomerApiResponse response = client.getCustomerInformationById(customerId);
            return new Customer(response.id(), response.persona(), response.subscriptions());
        } catch (NotFoundException e) {
            throw e;
        }
    }
}
