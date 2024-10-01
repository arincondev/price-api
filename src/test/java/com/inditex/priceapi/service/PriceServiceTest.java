package com.inditex.priceapi.service;

import com.inditex.priceapi.port.PriceRepositoryPort;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class PriceServiceTest {

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetApplicablePrice() {
        // Datos de prueba
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        // Crear precios simulados
        Price price1 = new Price();
        price1.setBrandId(brandId);
        price1.setProductId(productId);
        price1.setPrice(BigDecimal.valueOf(35.50));
        price1.setPriority(0);

        Price price2 = new Price();
        price2.setBrandId(brandId);
        price2.setProductId(productId);
        price2.setPrice(BigDecimal.valueOf(25.45));
        price2.setPriority(1);

        // Simular el comportamiento del puerto (repositorio)
        when(priceRepositoryPort.findByProductIdAndBrandIdAndDate(productId, brandId, applicationDate))
            .thenReturn(List.of(price1, price2));

        // Llamar al servicio
        Price applicablePrice = priceService.getApplicablePrice(productId, brandId, applicationDate);

        // Verificar el resultado
        assertNotNull(applicablePrice);
        assertEquals(BigDecimal.valueOf(25.45), applicablePrice.getPrice());  // Precio con mayor prioridad
    }
}
