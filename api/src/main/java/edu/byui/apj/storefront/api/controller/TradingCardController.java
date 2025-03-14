package edu.byui.apj.storefront.api.controller;

import ch.qos.logback.core.model.Model;
import edu.byui.apj.storefront.api.model.TradingCard;
import edu.byui.apj.storefront.api.service.TradingCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class TradingCardController {

    private TradingCardService tradingCardService;

    @Autowired
    public TradingCardController(TradingCardService tradingCardService) {
        this.tradingCardService = tradingCardService;
    }

    @GetMapping
    public List<TradingCard> getTradingCards(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return tradingCardService.getAllCards(page, size);
    }

    @GetMapping("/filter")
    public List<TradingCard> tradingCardsFilter( @RequestParam(required = false) BigDecimal minPrice,
                                     @RequestParam(required = false) BigDecimal maxPrice, @RequestParam(required = false) String speciality,
                                     @RequestParam(required = false) String sort) {
        return tradingCardService.getFilteredCars(minPrice, maxPrice, speciality, sort);

    }

    @GetMapping("/search")
    public List<TradingCard> tradingCardsSearch( @RequestParam String query ){

        return tradingCardService.searchCard(query);

    }
}
