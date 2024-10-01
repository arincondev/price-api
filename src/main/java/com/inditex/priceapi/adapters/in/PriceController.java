package com.inditex.priceapi.adapters.in;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.priceapi.domain.Price;
import com.inditex.priceapi.service.PriceService;
import lombok.RequiredArgsConstructor;

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
