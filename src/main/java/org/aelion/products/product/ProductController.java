package org.aelion.products.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/{ean13}")
    public ResponseEntity<?> getProductByEan13(@PathVariable String ean13){
        return service.findProductByEan13(ean13);
    };
}
