package edu.byui.apj.storefront.api.service;

import edu.byui.apj.storefront.api.model.TradingCard;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TradingCardService {

    private static final ArrayList<TradingCard> tradingCards = new ArrayList<>();

    public TradingCardService() {
        loadTradingCards();
    }

    private void loadTradingCards() {
        try{
            ClassPathResource resource = new ClassPathResource("pioneers.csv");
            Reader reader = new InputStreamReader(resource.getInputStream());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

            for (CSVRecord record : records) {
                //ID,Name,Specialty,Contribution,Price,ImageUrl
                Long id = Long.parseLong(record.get("ID"));
                String name = record.get("Name");
                String speciality = record.get("Specialty");
                String contribution = record.get("Contribution");
                BigDecimal price = new BigDecimal(record.get("Price"));
                String image = record.get("ImageUrl");

                TradingCard card = new TradingCard(id, name, speciality, contribution, price, image);

                tradingCards.add(card);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<TradingCard> getAllCards(int page, int size) {

        List<TradingCard> cards = new ArrayList<TradingCard>();
        int start = (page - 1) * size;
        int end = start + size;
        for (int i = start; i < end; i++) {
            cards.add(tradingCards.get(i));
        }
        return cards;

    }

    public List<TradingCard> getCards(int page, int size) {
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, tradingCards.size());
        return tradingCards.subList(fromIndex, toIndex);
    }

    public List<TradingCard> filterAndSortCards(BigDecimal minPrice, BigDecimal maxPrice, String specialty, String sort) {
        return tradingCards.stream()
                .filter(card -> (minPrice == null || card.getPrice().compareTo(minPrice) >= 0))
                .filter(card -> (maxPrice == null || card.getPrice().compareTo(maxPrice) <= 0))
                .filter(card -> (specialty == null || card.getSpecialty().equalsIgnoreCase(specialty)))
                .sorted((card1, card2) -> {
                    if ("price".equalsIgnoreCase(sort)) {
                        return card1.getPrice().compareTo(card2.getPrice());
                    } else { // default to sorting by name
                        return card1.getName().compareTo(card2.getName());
                    }
                })
                .collect(Collectors.toList());
    }

    public List<TradingCard> searchCards(String query) {
        return tradingCards.stream()
                .filter(card -> card.getName().toLowerCase().contains(query.toLowerCase()) ||
                        card.getContribution().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
