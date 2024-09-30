package com.inditex.priceapi.adapters.out;

import com.inditex.priceapi.port.PriceRepositoryPort;
import com.inditex.prices.domain.Price;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final JpaPriceRepository jpaPriceRepository;

    @Override
    public List<Price> findByProductIdAndBrandIdAndDate(Long productId, Long brandId, LocalDateTime applicationDate) {
        List<PriceEntity> priceEntities = jpaPriceRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            productId, brandId, applicationDate, applicationDate);

        return priceEntities.stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }

    // Conversión de PriceEntity a Price
    private Price toDomain(PriceEntity entity) {
        return Price.builder()

            .brandId(entity.getBrandId())
            .productId(entity.getProductId())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .price(entity.getPrice())
            .priceList(entity.getPriceList())
            .priority(entity.getPriority())
            .currency(entity.getCurrency())
            .build();
    }
}
