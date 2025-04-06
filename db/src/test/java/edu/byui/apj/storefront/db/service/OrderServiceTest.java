package edu.byui.apj.storefront.db.service;

import edu.byui.apj.storefront.db.model.CardOrder;
import edu.byui.apj.storefront.db.model.Cart;
import edu.byui.apj.storefront.db.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartService cartService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testSaveOrder() {
        Cart cart = new Cart();
        cart.setId("cart1");
        CardOrder order = new CardOrder();
        order.setCart(cart);

        when(cartService.getCart("cart1")).thenReturn(cart);
        when(orderRepository.save(any(CardOrder.class))).thenReturn(order);

        CardOrder savedOrder = orderService.saveOrder(order);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getCart()).isEqualTo(cart);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetOrder() {
        CardOrder order = new CardOrder();
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<CardOrder> foundOrder = orderService.getOrder(1L);

        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getId()).isEqualTo(1L);
    }
}
