package org.aelion.products.product.Impl;

import org.aelion.products.product.Product;
import org.aelion.products.product.ProductRepository;
import org.aelion.products.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private final static String ANALYTICS_API = "http://ANALYTICS-SERVICE/api/v1/analytics/";
    private final static String OPEN_FOOD_FACT_API =  "https://world.openfoodfacts.org/api/v2/product/";

    /**
     * Finds a product by its EAN13 barcode.
     * @param ean13 The EAN13 barcode of the product to find.
     * @return A ResponseEntity containing the found product or an error message and the corresponding HTTP status.
     */
    @Override
    public ResponseEntity<?> findProductByEan13(String ean13) {
        RestTemplate restTemplateForExternalApi = new RestTemplate();

        String openFoodFactsApiUrl = OPEN_FOOD_FACT_API + ean13;

        try {
            ResponseEntity<?> response = restTemplateForExternalApi.getForEntity(openFoodFactsApiUrl, String.class);
            if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
                URI analyticsUri = UriComponentsBuilder.fromHttpUrl(ANALYTICS_API + "incrementScan/" + ean13)
                        .build().toUri();
                restTemplate.postForEntity(analyticsUri, null, String.class);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the product.");
        }
    }
}
