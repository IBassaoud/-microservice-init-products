package org.aelion.products.product;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<?> findProductByEan13(String ean13);
}
