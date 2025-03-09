package edu.byui.apj.storefront.web.service;

import edu.byui.apj.storefront.web.model.TradingCard;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TradingCardClientService {

    // Returns the list of cards starting at position page * size and returning size elements.
    public static ArrayList<TradingCard> getAllCardsPaginated(int page, int size){
        return null;
    }

    // Returns the list resulting in filtering by minPrice, maxPrice or specialty, then sorting by sort
    //  sort can be "name" or "price"
    public static ArrayList<TradingCard> filterAndSort(BigDecimal minPrice, BigDecimal maxPrice, String specialty, String sort){
        return null;
    }

    // Returns the list of cards resulting in the query string (case-insensitive) found in the name or contribution.
    public static ArrayList<TradingCard> searchByNameOrContribution(String query){
        return null;
    }
}
