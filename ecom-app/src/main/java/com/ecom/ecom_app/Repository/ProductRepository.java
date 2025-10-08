package com.ecom.ecom_app.Repository;

import com.ecom.ecom_app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
