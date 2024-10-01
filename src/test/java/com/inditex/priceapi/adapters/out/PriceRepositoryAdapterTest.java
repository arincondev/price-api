package com.inditex.priceapi.adapters.out;

import com.inditex.priceapi.domain.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PriceRepositoryAdapterTest {

    @Mock
    private JpaPriceRepository jpaPriceRepository;

    @InjectMocks
    private PriceRepositoryAdapter priceRepositoryAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByProductIdAndBrandIdAndDate() {
        // Datos de prueba
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        // Crear entidades de prueba simuladas
        PriceEntity priceEntity1 = new PriceEntity();
        priceEntity1.setBrandId(brandId);
        priceEntity1.setProductId(productId);
        priceEntity1.setPrice(BigDecimal.valueOf(35.50));
        priceEntity1.setPriority(0);

        PriceEntity priceEntity2 = new PriceEntity();
        priceEntity2.setBrandId(brandId);
        priceEntity2.setProductId(productId);
        priceEntity2.setPrice(BigDecimal.valueOf(25.45));
        priceEntity2.setPriority(1);

        // Simular comportamiento del repositorio JPA
        when(jpaPriceRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            productId, brandId, applicationDate, applicationDate))
            .thenReturn(List.of(priceEntity1, priceEntity2));

        // Llamar al adaptador
        List<Price> prices = priceRepositoryAdapter.findByProductIdAndBrandIdAndDate(productId, brandId, applicationDate);

        // Verificar la conversión
        assertEquals(2, prices.size());
        assertEquals(BigDecimal.valueOf(35.50), prices.get(0).getPrice());
        assertEquals(BigDecimal.valueOf(25.45), prices.get(1).getPrice());
    }
}
