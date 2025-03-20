package edu.byui.apj.storefront.apimongo.controller;

import edu.byui.apj.storefront.apimongo.model.TradingCard;
import edu.byui.apj.storefront.apimongo.repository.TradingCardRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trading-cards")
public class TradingCardController {

    private final TradingCardRepository tradingCardRepository;

    public TradingCardController(TradingCardRepository tradingCardRepository) {
        this.tradingCardRepository = tradingCardRepository;
    }
    @GetMapping
    public List<TradingCard> getCards(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "20") int size,
                                      @RequestParam(required = false) String sortParam) {

        Pageable pageable;

        if ( sortParam == null || !sortParam.isEmpty()) {
            pageable = PageRequest.of(page, size);

        } else {
            Sort sort = Sort.by(Sort.Order.asc(sortParam));
            pageable = PageRequest.of(page, size, sort);  // Create Pageable object//
        }

        return tradingCardRepository.findAll(pageable).getContent();
    }

    // Get a trading card by ID
    @GetMapping("/{id}")
    public ResponseEntity<TradingCard> getTradingCardById(@PathVariable String id) {
        return tradingCardRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // Update a trading card
    @PutMapping("/{id}")
    public ResponseEntity<TradingCard> updateTradingCard(@PathVariable String id, @RequestBody
    TradingCard updatedCard) {
        return tradingCardRepository.findById(id)
                .map(existingCard -> {
                    updatedCard.set_id(id);
                    return
                            ResponseEntity.ok(tradingCardRepository.save(updatedCard));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    // Filter by specialty
    @GetMapping("/filter/specialty")
    public ResponseEntity<List<TradingCard>> filterBySpecialty(@RequestParam String specialty) {
        return
                ResponseEntity.ok(tradingCardRepository.findBySpecialty(specialty)
                );
    }
    // Search trading cards by name or contribution
    @GetMapping("/search")
    public ResponseEntity<List<TradingCard>> searchTradingCards(@RequestParam String query) {
        return
                ResponseEntity.ok(tradingCardRepository.findByNameContainsIgnoreCaseOrContributionContainsIgnoreCase(query, query));
    }
}