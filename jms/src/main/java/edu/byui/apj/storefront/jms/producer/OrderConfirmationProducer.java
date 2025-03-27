package edu.byui.apj.storefront.jms.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
public class OrderConfirmationProducer {

    private static final Logger logger = LoggerFactory.getLogger(OrderConfirmationProducer.class);

    private final JmsTemplate jmsTemplate;

    public OrderConfirmationProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void createMessage(String orderId) {
        try {
            jmsTemplate.convertAndSend("orderQueue", orderId); // Sends orderId to JMS queue
            logger.info("Order confirmation message sent for order ID: {}", orderId);
        } catch (Exception e) {
            logger.error("Error sending order confirmation message for order ID: {}", orderId, e);
            throw new RuntimeException("Failed to send JMS message");
        }
    }
}