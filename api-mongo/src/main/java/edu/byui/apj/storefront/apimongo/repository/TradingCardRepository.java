package edu.byui.apj.storefront.apimongo.repository;

import edu.byui.apj.storefront.apimongo.model.TradingCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface TradingCardRepository extends MongoRepository<TradingCard, String> {
    // Filters by specialty
    List<TradingCard> findBySpecialty(String specialty);
    // Searches for text within name or contribution
    List<TradingCard> findByNameContainsIgnoreCaseOrContributionContainsIgnoreCase(String name, String contribution);

    Page<TradingCard> findAll(Pageable pageable);

    List<TradingCard> findByPriceBetween(double min, double max);

}

