package edu.byui.apj.storefront.api.service;

import edu.byui.apj.storefront.api.model.TradingCard;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

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
                String image = record.get("ImageURL");

                TradingCard card = new TradingCard(id, name, speciality, contribution, price, image);

                tradingCards.add(card);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<TradingCard> getAllCards(int page, int size) {

        ArrayList<TradingCard> cards = new ArrayList<TradingCard>();
        int start = (page - 1) * size;
        int end = start + size;
        for (int i = start; i < end; i++) {
            cards.add(tradingCards.get(i));
        }
        return cards;

    }

    public static ArrayList<TradingCard> getFilteredCars(BigDecimal minPrice, BigDecimal maxPrice, String speciality, String sort) {
        ArrayList<TradingCard> cards = tradingCards;

        if (minPrice != null) {
            cards.stream().filter(card -> card.getPrice().compareTo(minPrice) >= 0 )
                    .collect(Collectors.toList());
        }

        if (maxPrice != null) {
            cards.stream().filter(card -> card.getPrice().compareTo(maxPrice) <= 0 )
                    .collect(Collectors.toList());
        }

        if (speciality != null) {
            cards.stream().filter(card -> card.getSpecialty().equalsIgnoreCase(speciality))
                    .collect(Collectors.toList());
        }

        if (sort != null){
            if (sort.equalsIgnoreCase("name")){
                cards.sort(Comparator.comparing(TradingCard::getName));
            }
            else if (sort.equalsIgnoreCase("price")){
                cards.sort(Comparator.comparing(TradingCard::getPrice));
            }
        }

        return cards;
    }

    public static ArrayList<TradingCard> searchCard(String query) {
        ArrayList<TradingCard> cards = tradingCards;

        cards.stream().filter( card -> card.getName().toLowerCase().contains(query.toLowerCase()) ||
                card.getContribution().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());

        return cards;
    }
}
