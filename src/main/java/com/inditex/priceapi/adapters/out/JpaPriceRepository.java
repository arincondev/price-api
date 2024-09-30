package com.inditex.priceapi.adapters.out;

import com.inditex.prices.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {
    List<PriceEntity> findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        Long productId, Long brandId, LocalDateTime startDate, LocalDateTime endDate);
}
