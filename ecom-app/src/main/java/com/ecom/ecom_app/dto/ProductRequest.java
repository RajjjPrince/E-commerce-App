package com.ecom.ecom_app.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stackQuantity;
    private String category;
    private String imageUrl;
}
