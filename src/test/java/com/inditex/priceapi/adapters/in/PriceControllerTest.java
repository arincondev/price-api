package com.inditex.priceapi.adapters.in;

import com.inditex.priceapi.port.PriceRepositoryPort;
import com.inditex.priceapi.domain.Price;
import com.inditex.priceapi.service.PriceService; // Asegúrate de importar correctamente el servicio
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PriceControllerTest {

    @Mock
    private PriceService priceService;  // Ahora mockeamos el servicio en lugar del puerto directamente

    @InjectMocks
    private PriceController priceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPrice() {
        // Datos de prueba
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        // Crear un precio de prueba
        Price price = new Price();
        price.setBrandId(brandId);
        price.setProductId(productId);
        price.setPrice(BigDecimal.valueOf(35.50));
        price.setPriority(0);

        // Simular el comportamiento del servicio
        when(priceService.getApplicablePrice(productId, brandId, applicationDate)).thenReturn(price);

        // Llamar al controlador
        ResponseEntity<Price> response = priceController.getPrice(productId, brandId, applicationDate);

        // Verificar que la respuesta es correcta
        assertEquals(200, response.getStatusCode().value());
        assertEquals(price, response.getBody());
    }
}
