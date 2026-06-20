package org.yearup.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.yearup.models.*;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService {
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;
    private final UserService userService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService, UserService userService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public ShoppingCart getByUserId(int userId) {
        // load the user's cart rows, look up each product, and build the ShoppingCart
        //add new shopping cart
        ShoppingCart cart = new ShoppingCart();
        //make the list to store items added to cart
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);
        //make the loop needed to grab the rows
        for (CartItem item : cartItems) {

            //get product id and quantity from cart rows
            int productID = item.getProductId();
            int quantity = item.getQuantity();

            //use productService to get the product
            Product product = productService.getById(productID);

            //make a shopping cart item to put the product into
            ShoppingCartItem cartItem = new ShoppingCartItem();

            //now put in the product
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.add(cartItem);
        }

        return cart;
    }

    // add additional methods here

//method to add products to the cart
    public ShoppingCart addProduct(int userId, int productId) {
       //create the item by finding it in user & product id
        CartItem item = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
     //use an if statement to increase the quantity and save the item to the cart
        if (item == null) {
            item = new CartItem();
            item.setProductId(productId);
            item.setUserId(userId);
            item.setQuantity(1);
          //use an else to add multiple of the same product
        } else {
            item.setQuantity(item.getQuantity()+1);

        }
        shoppingCartRepository.save(item);
        return getByUserId(userId);
    }


    public ShoppingCart updateQuantity(int userId, int productId, int quantity)
    {
        CartItem item = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        item.setQuantity(quantity);

        shoppingCartRepository.save(item);

        return getByUserId(userId);
    }

@DeleteMapping
        public ShoppingCart clearCart(int userId)
{ shoppingCartRepository.deleteByUserId(userId);

    return getByUserId(userId) ;
}
}// end of shopping cart service
