package com.inditex.priceapi.port;

import com.inditex.prices.domain.Price;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepositoryPort {
    List<Price> findByProductIdAndBrandIdAndDate(Long productId, Long brandId, LocalDateTime applicationDate);
}
