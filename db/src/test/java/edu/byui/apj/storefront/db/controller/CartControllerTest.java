package edu.byui.apj.storefront.db.controller;

import edu.byui.apj.storefront.db.model.Cart;
import edu.byui.apj.storefront.db.model.Item;
import edu.byui.apj.storefront.db.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    void testGetCartNoOrder() throws Exception {
        when(cartService.getCartsWithoutOrders()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/cart/noorder"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetCart() throws Exception {
        Cart cart = new Cart();
        cart.setId("123");
        when(cartService.getCart("123")).thenReturn(cart);

        mockMvc.perform(MockMvcRequestBuilders.get("/cart/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void testAddItemToCart() throws Exception {
        Cart cart = new Cart();
        cart.setId("123");
        Item item = new Item();
        item.setId(1L);
        when(cartService.addItemToCart("123", item)).thenReturn(cart);

        mockMvc.perform(MockMvcRequestBuilders.post("/cart/123/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void testRemoveItemFromCart() throws Exception {
        Cart cart = new Cart();
        cart.setId("123");
        when(cartService.removeItemFromCart("123", 1L)).thenReturn(cart);

        mockMvc.perform(MockMvcRequestBuilders.delete("/cart/123/item/1"))
                .andExpect(status().isOk());
    }
}