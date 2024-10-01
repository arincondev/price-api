package com.inditex.priceapi.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.inditex.priceapi.port.PriceRepositoryPort;
import com.inditex.priceapi.domain.Price;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepositoryPort priceRepositoryPort;

    public Price getApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        List<Price> prices = priceRepositoryPort.findByProductIdAndBrandIdAndDate(productId, brandId, applicationDate);
        return prices.stream()
            .max(Comparator.comparing(Price::getPriority))
            .orElseThrow(() -> new RuntimeException("No applicable price found"));
    }
}
