package com.ecom.ecom_app.Controller;

import com.ecom.ecom_app.Services.ProductService;
import com.ecom.ecom_app.dto.ProductRequest;
import com.ecom.ecom_app.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> fetchAllProducts() {
        return new ResponseEntity<>(productService.fetchAllProducts()
                , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest)
                ,HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest productRequest){
        return productService.updateProduct(id,productRequest)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }
    @GetMapping("/active")
    public ResponseEntity<List<ProductResponse>> fetchAllActiveProducts() {
        return ResponseEntity.ok(productService.fetchAllActiveProducts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String keyword){
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }


}


