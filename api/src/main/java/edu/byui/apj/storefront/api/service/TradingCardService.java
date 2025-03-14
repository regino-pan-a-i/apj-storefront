package edu.byui.apj.storefront.api.service;

import edu.byui.apj.storefront.api.model.TradingCard;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

    public List<TradingCard> getFilteredCars(BigDecimal minPrice, BigDecimal maxPrice, String speciality, String sort) {
        List<TradingCard> cards = new ArrayList<>(tradingCards);  // Create a new list to work with

        if (minPrice != null) {
            cards = cards.stream()
                    .filter(card -> card.getPrice().compareTo(minPrice) >= 0)
                    .collect(Collectors.toList());  // Reassign to cards
        }

        if (maxPrice != null) {
            cards = cards.stream()
                    .filter(card -> card.getPrice().compareTo(maxPrice) <= 0)
                    .collect(Collectors.toList());  // Reassign to cards
        }

        if (speciality != null) {
            cards = cards.stream()
                    .filter(card -> card.getSpecialty().equalsIgnoreCase(speciality))
                    .collect(Collectors.toList());  // Reassign to cards
        }

        if (sort != null) {
            if (sort.equalsIgnoreCase("name")) {
                cards.sort(Comparator.comparing(TradingCard::getName));
            } else if (sort.equalsIgnoreCase("price")) {
                cards.sort(Comparator.comparing(TradingCard::getPrice));
            }
        }

        return cards;
    }

    public List<TradingCard> searchCard(String query) {

        return tradingCards.stream()
                .filter(card -> card.getName().toLowerCase().contains(query.toLowerCase()) ||
                        card.getContribution().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
