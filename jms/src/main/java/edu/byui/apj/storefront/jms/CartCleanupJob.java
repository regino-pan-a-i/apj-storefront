package edu.byui.apj.storefront.jms;

import edu.byui.apj.storefront.model.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CartCleanupJob {

    private static final Logger logger = LoggerFactory.getLogger(CartCleanupJob.class);
    private final WebClient.Builder webClientBuilder;

    public CartCleanupJob(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<Cart> getCartsWithoutOrders() {
        try {
            WebClient webClient = webClientBuilder.baseUrl("http://localhost:8083").build();
            List<Cart> carts = webClient.get()
                    .uri("/cart/noorderLinks")
                    .retrieve()
                    .bodyToFlux(Cart.class)
                    .collectList()
                    .block();

            return carts;
        } catch (Exception e) {
            logger.error("Error fetching carts without orders", e);
            throw new RuntimeException("Error fetching carts without orders", e);
        }
    }

    public void cleanupCart(String cartId) {
        try {
            WebClient webClient = webClientBuilder.baseUrl("http://localhost:8083").build();
            webClient.delete()
                    .uri("/cart/{cartId}", cartId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            logger.info("Successfully cleaned up cart: {}", cartId);
        } catch (Exception e) {
            logger.error("Error cleaning up cart: {}", cartId, e);
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupCarts() {
        List<Cart> carts = getCartsWithoutOrders();
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        carts.forEach(cart -> {
            executorService.submit(() -> cleanupCart(cart.getId()));
        });

        executorService.shutdown();
        logger.info("Cart cleanup complete");
    }
}