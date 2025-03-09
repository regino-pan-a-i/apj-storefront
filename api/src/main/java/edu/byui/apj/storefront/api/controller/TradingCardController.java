package edu.byui.apj.storefront.api.controller;

import ch.qos.logback.core.model.Model;
import edu.byui.apj.storefront.api.model.TradingCard;
import edu.byui.apj.storefront.api.service.TradingCardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;

@Controller
public class TradingCardController {


    @GetMapping("/api/cards")
    public ArrayList<TradingCard> tradingCards(Model model, @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "20") int size ) {
        return TradingCardService.getAllCards(page,size);
    }

    @GetMapping("/api/cards/filter")
    public ArrayList<TradingCard> tradingCardsFilter(Model model, @RequestParam(required = false) BigDecimal minPrice,
                                     @RequestParam(required = false) BigDecimal maxPrice, @RequestParam(required = false) String speciality,
                                     @RequestParam(required = false) String sort) {
        return TradingCardService.getFilteredCars(minPrice, maxPrice, speciality, sort);

    }

    @GetMapping("/api/cards/search")
    public ArrayList<TradingCard> tradingCardsSearch(Model model, @RequestParam String query ){

        return TradingCardService.searchCard(query);

    }
}
