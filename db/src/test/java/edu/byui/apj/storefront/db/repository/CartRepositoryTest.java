package edu.byui.apj.storefront.db.repository;

import edu.byui.apj.storefront.db.model.Cart;
import edu.byui.apj.storefront.db.model.CardOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testFindCartsWithoutOrders() {
        Cart cart1 = new Cart();
        cart1.setId("cart1");
        entityManager.persist(cart1);

        Cart cart2 = new Cart();
        cart2.setId("cart2");
        entityManager.persist(cart2);

        CardOrder order = new CardOrder();
        order.setCart(cart1);
        entityManager.persist(order);

        entityManager.flush();

        List<Cart> cartsWithoutOrders = cartRepository.findCartsWithoutOrders();

        assertThat(cartsWithoutOrders).containsExactly(cart2);
    }
}
