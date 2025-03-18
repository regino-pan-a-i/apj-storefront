package edu.byui.apj.storefront.web.service;

import edu.byui.apj.storefront.web.model.TradingCard;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigDecimal;
import java.util.List;

@Service
public class TradingCardClientService {

    private final WebClient webClient;

    public TradingCardClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081/api/cards").build();  // Assuming API runs on localhost
    }

    // Get paginated cards
    public List<TradingCard> getAllCardsPaginated(int page, int size) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToFlux(TradingCard.class)
                .collectList()
                .block();
    }

    // Filter and sort cards
    public List<TradingCard> filterAndSort(BigDecimal minPrice, BigDecimal maxPrice, String specialty, String sort) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/filter")
                        .queryParam("minPrice", minPrice)
                        .queryParam("maxPrice", maxPrice)
                        .queryParam("specialty", specialty)
                        .queryParam("sort", sort)
                        .build())
                .retrieve()
                .bodyToFlux(TradingCard.class)
                .collectList()
                .block();
    }

    // Search cards by name or contribution
    public List<TradingCard> searchByNameOrContribution(String query) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToFlux(TradingCard.class)
                .collectList()
                .block();
    }
}
