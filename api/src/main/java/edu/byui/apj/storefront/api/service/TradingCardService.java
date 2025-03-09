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

public class TradingCardService {

    private final ArrayList<TradingCard> tradingCards = new ArrayList<>();

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
    public  ArrayList<TradingCard> getAllCards(int page, int size) {

        ArrayList<TradingCard> cards = new ArrayList<>();
        int start = (page - 1) * size;
        int end = start + size;
        for (int i = start; i < end; i++) {
            cards.add(tradingCards.get(i));
        }
        return cards;

    }

    public  ArrayList<TradingCard> getFilteredCars(BigDecimal minPrice, BigDecimal maxPrice, String speciality, Boolean sort) {
    }

    public  ArrayList<TradingCard> searchCard(String query) {
    }
}
