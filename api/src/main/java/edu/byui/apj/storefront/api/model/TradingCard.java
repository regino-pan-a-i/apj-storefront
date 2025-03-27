package edu.byui.apj.storefront.api.model;


import java.math.BigDecimal;


public class TradingCard {
    private Long id;
    private String name;
    private String specialty;
    private String contribution;
    private BigDecimal price;
    private String imageUrl;

    public TradingCard(Long id, String name, String specialty, String contribution, BigDecimal price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.contribution = contribution;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getContribution() {
        return contribution;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
