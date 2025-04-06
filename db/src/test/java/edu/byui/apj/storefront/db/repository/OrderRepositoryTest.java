package edu.byui.apj.storefront.db.repository;

import edu.byui.apj.storefront.db.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    private Customer customer;
    private Cart cart1;
    private Cart cart2;
    private CardOrder order1;
    private CardOrder order2;
    private Address address;

    @BeforeEach
    void setUp() {
        // Clear previous test data
        orderRepository.deleteAll();

        // Create a customer
        customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john@example.com");
        customer.setPhone("123-456-7890");
        entityManager.persist(customer);

        // Create an address
        address = new Address();
        address.setAddressLine1("123 Main St");
        address.setCity("Springfield");
        address.setState("IL");
        address.setZipCode("62701");
        address.setCountry("USA");
        entityManager.persist(address);

        // Create carts
        cart1 = new Cart();
        cart1.setId("cart1");
        cart1.setPersonId("person1");
        cart1.setItems(new ArrayList<>());
        entityManager.persist(cart1);

        cart2 = new Cart();
        cart2.setId("cart2");
        cart2.setPersonId("person2");
        cart2.setItems(new ArrayList<>());
        entityManager.persist(cart2);

        // Create orders
        order1 = new CardOrder();
        order1.setCustomer(customer);
        order1.setCart(cart1);
        order1.setShippingAddress(address);
        order1.setOrderDate(new Date());
        order1.setShipMethod("Standard");
        order1.setOrderNotes("Order 1");
        order1.setSubtotal(50.0);
        order1.setTax(5.0);
        order1.setTotal(55.0);
        entityManager.persist(order1);

        order2 = new CardOrder();
        order2.setCustomer(customer);
        order2.setCart(cart2);
        order2.setShippingAddress(address);
        order2.setOrderDate(new Date());
        order2.setShipMethod("Express");
        order2.setOrderNotes("Order 2");
        order2.setSubtotal(75.0);
        order2.setTax(7.5);
        order2.setTotal(82.5);
        entityManager.persist(order2);

        entityManager.flush();
    }

    @Test
    void testSaveAndFindById() {
        // Save and find the first order
        CardOrder savedOrder = orderRepository.save(order1);
        Long savedOrderId = savedOrder.getId();

        // Verify saved order details
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrderId).isNotNull();
        assertThat(savedOrder.getCustomer()).isEqualTo(customer);
        assertThat(savedOrder.getCart()).isEqualTo(cart1);
        assertThat(savedOrder.getShippingAddress()).isEqualTo(address);
        assertThat(savedOrder.getSubtotal()).isEqualTo(50.0);
    }

    @Test
    void testFindAll() {
        // Retrieve all orders
        List<CardOrder> orders = orderRepository.findAll();
        assertThat(orders).hasSize(2);

        // Verify orders
        assertThat(orders.get(0).getCustomer().getFirstName()).isEqualTo("John");
        assertThat(orders.get(1).getCart().getId()).isEqualTo("cart2");
    }

    @Test
    void testDeleteOrder() {
        // Get the ID of the first order
        Long orderIdToDelete = order1.getId();

        // Delete the order
        orderRepository.deleteById(orderIdToDelete);
        entityManager.flush();
        entityManager.clear(); // Ensure the data is fresh for verification

        // Verify the order is deleted
        Optional<CardOrder> deletedOrder = orderRepository.findById(orderIdToDelete);
        assertThat(deletedOrder).isEmpty();

        // Verify only one order remains
        List<CardOrder> remainingOrders = orderRepository.findAll();
        assertThat(remainingOrders).hasSize(1);
        assertThat(remainingOrders.get(0).getId()).isEqualTo(order2.getId());
    }

    @Test
    void testUpdateOrder() {
        // Get ID for later retrieval
        Long orderIdToUpdate = order2.getId();

        // Update order properties
        order2.setShipMethod("Overnight");
        order2.setOrderNotes("Updated order notes");

        // Save the updated order
        orderRepository.save(order2);
        entityManager.flush();

        // Retrieve the updated order and verify changes
        CardOrder retrieved = orderRepository.findById(orderIdToUpdate).orElseThrow();
        assertThat(retrieved.getShipMethod()).isEqualTo("Overnight");
        assertThat(retrieved.getOrderNotes()).isEqualTo("Updated order notes");
    }
}