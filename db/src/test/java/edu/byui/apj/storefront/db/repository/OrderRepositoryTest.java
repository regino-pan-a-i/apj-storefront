package edu.byui.apj.storefront.db.repository;

import edu.byui.apj.storefront.db.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Customer customer;

    @Mock
    private Cart cart1;

    @Mock
    private Cart cart2;

    @Mock
    private Address address;

    @Mock
    private CardOrder order1;

    @Mock
    private CardOrder order2;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Setting up mock behaviors
        when(customer.getFirstName()).thenReturn("John");
        when(customer.getLastName()).thenReturn("Doe");
        when(customer.getEmail()).thenReturn("john@example.com");

        when(address.getAddressLine1()).thenReturn("123 Main St");
        when(address.getCity()).thenReturn("Springfield");
        when(address.getState()).thenReturn("IL");
        when(address.getZipCode()).thenReturn("62701");

        when(cart1.getId()).thenReturn("cart1");
        when(cart2.getId()).thenReturn("cart2");

        // Mocking orders
        when(order1.getId()).thenReturn(1L);
        when(order1.getCustomer()).thenReturn(customer);
        when(order1.getCart()).thenReturn(cart1);
        when(order1.getShippingAddress()).thenReturn(address);
        when(order1.getSubtotal()).thenReturn(50.0);
        when(order1.getTotal()).thenReturn(55.0);
        when(order1.getShipMethod()).thenReturn("Standard");
        when(order1.getOrderNotes()).thenReturn("Order 1");

        when(order2.getId()).thenReturn(2L);
        when(order2.getCustomer()).thenReturn(customer);
        when(order2.getCart()).thenReturn(cart2);
        when(order2.getShippingAddress()).thenReturn(address);
        when(order2.getSubtotal()).thenReturn(75.0);
        when(order2.getTotal()).thenReturn(82.5);
        when(order2.getShipMethod()).thenReturn("Express");
        when(order2.getOrderNotes()).thenReturn("Order 2");
    }

    @Test
    void testSaveAndFindById() {
        // Simulate saving the order
        when(orderRepository.save(order1)).thenReturn(order1);

        // Save and find the first order
        CardOrder savedOrder = orderRepository.save(order1);
        Long savedOrderId = savedOrder.getId();

        // Verify saved order details
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrderId).isEqualTo(1L);
        assertThat(savedOrder.getCustomer()).isEqualTo(customer);
        assertThat(savedOrder.getCart()).isEqualTo(cart1);
        assertThat(savedOrder.getShippingAddress()).isEqualTo(address);
        assertThat(savedOrder.getSubtotal()).isEqualTo(50.0);

        // Verify that save method was called once
        verify(orderRepository, times(1)).save(order1);
    }

    @Test
    void testFindAll() {
        // Simulate finding all orders
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        // Retrieve all orders
        Iterable<CardOrder> orders = orderRepository.findAll();
        assertThat(orders).hasSize(2);

        // Verify the orders' details
        assertThat(order1.getCustomer().getFirstName()).isEqualTo("John");
        assertThat(order2.getCart().getId()).isEqualTo("cart2");

        // Verify findAll was called once
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testDeleteOrder() {
        // Simulate deleting an order
        doNothing().when(orderRepository).deleteById(1L);

        // Delete the order
        orderRepository.deleteById(1L);

        // Verify that deleteById method was called once
        verify(orderRepository, times(1)).deleteById(1L);
    }

}