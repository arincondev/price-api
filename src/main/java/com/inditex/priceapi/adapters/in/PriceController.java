package com.inditex.priceapi.adapters.in;

import com.inditex.priceapi.port.PriceRepositoryPort;
import com.inditex.priceapi.service.PriceService;
import com.inditex.prices.domain.Price;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/prices")
public class PriceController {

    private final PriceService priceService;


    @GetMapping
    public ResponseEntity<Price> getPrice(
        @RequestParam Long productId,
        @RequestParam Long brandId,
        @RequestParam LocalDateTime applicationDate
    ) {
        return ResponseEntity.ok(priceService.getApplicablePrice(productId, brandId, applicationDate));
    }
}
