package jeff.technical.test.recommendationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jeff.technical.test.recommendationservice.common.errors.ErrorDetails;
import jeff.technical.test.recommendationservice.common.exceptions.NotFoundException;
import jeff.technical.test.recommendationservice.model.dto.Recommendation;
import jeff.technical.test.recommendationservice.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/recommend")
public class RecommendationController {

    private static final String format = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    @Autowired
    RecommendationService service;

    @Operation(summary = "Get tailored recommendations for specific customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recommendations delivered",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Recommendation.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)) }) })
    @GetMapping(value = "/{customerId}", produces = "application/json")
    public ResponseEntity<Object> getRecommendations(@Parameter(description = "Id of the customer")@PathVariable Integer customerId,
                                                     @Parameter(description = "Minimum score to overwrite the default")@RequestParam(required = false) Optional<Integer> minScore,
                                                     @Parameter(description = "Number of extra high-margin products to include")@RequestParam(required = false) Optional<Integer> extraHighMarginProducts) {

        try {
            Recommendation recommendation = service.getRecommendedProductsForUser(customerId, minScore, extraHighMarginProducts);
            return ResponseEntity.ok(recommendation);
        }  catch (Exception e) {
            if (e instanceof NotFoundException) {
                log.error("Something was not found: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorDetails(ZonedDateTime.now().format(DateTimeFormatter.ofPattern(format)), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage(), "/recommended/".concat(String.valueOf(customerId))));
            } else {
                log.error("Something happened: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorDetails(ZonedDateTime.now().format(DateTimeFormatter.ofPattern(format)), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), "/recommended/".concat(String.valueOf(customerId))));
            }

        }


    }

}
