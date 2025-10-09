package com.ecom.ecom_app.Services;

import com.ecom.ecom_app.Repository.CartItemRepository;
import com.ecom.ecom_app.Repository.ProductRepository;
import com.ecom.ecom_app.Repository.UserRepository;
import com.ecom.ecom_app.dto.CartItemRequest;
import com.ecom.ecom_app.dto.CartResponse;
import com.ecom.ecom_app.model.CartItem;
import com.ecom.ecom_app.model.Product;
import com.ecom.ecom_app.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    public boolean addToCart(String userId, CartItemRequest request) {
        //Look for product
        Optional<Product>productOpt = productRepository.findById(request.getProductId());
        if(productOpt.isEmpty())
            return false;

        Product product = productOpt.get();
        if(product.getStackQuantity()< request.getQuantity())
            return false;

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty())
            return false;

        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user,product);
        if(existingCartItem != null){
            //Update the quantity.
            existingCartItem.setQuantity(existingCartItem.getQuantity()+ request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }else{
            //Create a new cart item.
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);

        }

        return true;

    }


}
