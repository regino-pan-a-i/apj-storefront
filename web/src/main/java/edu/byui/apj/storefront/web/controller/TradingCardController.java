package edu.byui.apj.storefront.web.controller;

import ch.qos.logback.core.model.Model;
import edu.byui.apj.storefront.web.model.TradingCard;
import edu.byui.apj.storefront.web.service.TradingCardClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;

@RestController
public class TradingCardController {



    @GetMapping("/cards")
    public ArrayList<TradingCard> tradingCards(Model model, @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "20") int size ) {
        return TradingCardClientService.getAllCardsPaginated(page,size);
    }

    @GetMapping("/filter")
    public ArrayList<TradingCard> tradingCardsFilter(Model model, @RequestParam(required = false) BigDecimal minPrice,
                                                     @RequestParam(required = false) BigDecimal maxPrice, @RequestParam(required = false) String speciality,
                                                     @RequestParam(required = false) String sort) {
        return TradingCardClientService.filterAndSort(minPrice, maxPrice, speciality, sort);

    }

    @GetMapping("/search")
    public ArrayList<TradingCard> tradingCardsSearch(Model model, @RequestParam String query ){

        return TradingCardClientService.searchByNameOrContribution(query);

    }
}