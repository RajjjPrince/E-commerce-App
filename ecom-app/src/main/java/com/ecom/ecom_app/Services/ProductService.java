package com.ecom.ecom_app.Services;

import com.ecom.ecom_app.Repository.ProductRepository;
import com.ecom.ecom_app.dto.ProductRequest;
import com.ecom.ecom_app.dto.ProductResponse;
import com.ecom.ecom_app.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product,productRequest);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    public List<ProductResponse> fetchAllProducts(){
        return productRepository.findAll().stream()
                .map(this :: mapToProductResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
         return productRepository.findById(id)
                    .map(existingProduct ->{
                        updateProductFromRequest(existingProduct,productRequest);
                        Product savedProduct = productRepository.save(existingProduct);
                        return mapToProductResponse(savedProduct);
                    });
    }

    public Optional<ProductResponse> fetchProduct(Long id) {
        return productRepository.findById(id)
                .map(this::mapToProductResponse);
    }



    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(String.valueOf(savedProduct.getId()));
        response.setName(savedProduct.getName());
        response.setCategory(savedProduct.getCategory());
        response.setActive(savedProduct.isActive());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setStackQuantity(savedProduct.getStackQuantity());

        return response;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setPrice(productRequest.getPrice());
        product.setStackQuantity(productRequest.getStackQuantity());
    }



}
