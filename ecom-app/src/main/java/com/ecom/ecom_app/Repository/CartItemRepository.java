package com.ecom.ecom_app.Repository;

import com.ecom.ecom_app.model.CartItem;
import com.ecom.ecom_app.model.Product;
import com.ecom.ecom_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    CartItem findByUserAndProduct(User user, Product product);
}
