package edu.byui.apj.storefront.db.controller;

import edu.byui.apj.storefront.db.model.CardOrder;
import edu.byui.apj.storefront.db.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void testSaveOrder() throws Exception {
        CardOrder order = new CardOrder();
        order.setId(1L);
        when(orderService.saveOrder(Mockito.any(CardOrder.class))).thenReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetOrder() throws Exception {
        CardOrder order = new CardOrder();
        order.setId(1L);
        when(orderService.getOrder(1L)).thenReturn(Optional.of(order));

        mockMvc.perform(MockMvcRequestBuilders.get("/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetOrderNotFound() throws Exception {
        when(orderService.getOrder(2L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/order/2"))
                .andExpect(status().isNotFound());
    }
}
