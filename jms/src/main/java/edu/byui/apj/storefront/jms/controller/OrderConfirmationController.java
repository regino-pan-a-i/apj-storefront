package edu.byui.apj.storefront.jms.controller;

import edu.byui.apj.storefront.jms.producer.OrderConfirmationProducer;
import jakarta.jms.ConnectionFactory;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/confirm")
public class OrderConfirmationController {

    private OrderConfirmationProducer producer;

    public OrderConfirmationController(OrderConfirmationProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity produceMessage(@PathVariable String orderId){

        try {
            producer.createMessage(orderId);

            String body = "Order confirm message sent for order ID: " + orderId;
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (Exception e) {

            System.out.println(e);
            // Return a generic error message
            String errorMessage = "An unexpected error occurred while processing the order ID: " + orderId;
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
