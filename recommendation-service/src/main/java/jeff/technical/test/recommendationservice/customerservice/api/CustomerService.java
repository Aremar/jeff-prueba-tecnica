package jeff.technical.test.recommendationservice.customerservice.api;

import jeff.technical.test.recommendationservice.common.exceptions.NotFoundException;
import jeff.technical.test.recommendationservice.model.dto.Customer;

public interface CustomerService {

    /**
     * THis method returns the minimal and necessary customer information to get the recommendations
     * @param customerId
     * @return Customer object containing id, persona and list of current subscriptions
     */
    Customer getCustomerInfoForRecommendation(Integer customerId) throws NotFoundException;
}
