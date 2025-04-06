package edu.byui.apj.storefront.db.service;

import edu.byui.apj.storefront.db.model.Cart;
import edu.byui.apj.storefront.db.model.Item;
import edu.byui.apj.storefront.db.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    void testAddItemToCart() {
        Cart cart = new Cart();
        cart.setId("cart1");
        cart.setItems(new ArrayList<>());
        Item item = new Item();
        item.setId(1L);

        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.addItemToCart("cart1", item);

        assertThat(updatedCart.getItems()).contains(item);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testRemoveItemFromCart() {
        Cart cart = new Cart();
        cart.setId("cart1");
        Item item = new Item();
        item.setId(1L);
        List<Item> items = new ArrayList<>();
        items.add(item);
        cart.setItems(items);

        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.removeItemFromCart("cart1", 1L);

        assertThat(updatedCart.getItems()).doesNotContain(item);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testGetCartNotFound() {
        when(cartRepository.findById("cart1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> cartService.getCart("cart1"));
        assertThat(exception.getMessage()).isEqualTo("Cart not found");
    }
}
