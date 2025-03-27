package edu.byui.apj.storefront.jms.consumer;

import edu.byui.apj.storefront.model.CardOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OrderConfirmationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderConfirmationConsumer.class);
    private final WebClient.Builder webClientBuilder;

    public OrderConfirmationConsumer(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @JmsListener(destination = "orderQueue")
    public void receiveOrderConfirmation(String orderId) {
        try {
            WebClient webClient = webClientBuilder.baseUrl("http://localhost:8083").build();
            CardOrder cardOrder = webClient.get()
                    .uri("/order/{orderId}", orderId)
                    .retrieve()
                    .bodyToMono(CardOrder.class)
                    .block();

            if (cardOrder != null) {
                logger.info("Order confirmation sent for order: {}", cardOrder);
            } else {
                logger.error("No order found for order ID: {}", orderId);
            }
        } catch (Exception e) {
            logger.error("Error processing order confirmation for order ID: {}", orderId, e);
        }
    }
}