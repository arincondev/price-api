package com.inditex.priceapi.port;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.inditex.priceapi.domain.Price;


@Repository
public interface PriceRepositoryPort {
    List<Price> findByProductIdAndBrandIdAndDate(Long productId, Long brandId, LocalDateTime applicationDate);
}
