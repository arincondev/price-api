package com.inditex.priceapi.adapters.in;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.priceapi.adapters.out.JpaPriceRepository;
import com.inditex.priceapi.adapters.out.PriceEntity;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaPriceRepository jpaPriceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada prueba
        jpaPriceRepository.deleteAll();

        // Insertar los datos de ejemplo para los casos de prueba
        jpaPriceRepository.save(createPriceEntity(LocalDateTime.of(2020, 6, 14, 0, 0, 0),
            LocalDateTime.of(2020, 12, 31, 23, 59, 59), 1L, BigDecimal.valueOf(35.50), 0));
        jpaPriceRepository.save(createPriceEntity(LocalDateTime.of(2020, 6, 14, 15, 0, 0),
            LocalDateTime.of(2020, 6, 14, 18, 30, 0), 2L, BigDecimal.valueOf(25.45), 1));
        jpaPriceRepository.save(createPriceEntity(LocalDateTime.of(2020, 6, 15, 0, 0, 0),
            LocalDateTime.of(2020, 6, 15, 11, 0, 0), 3L, BigDecimal.valueOf(30.50), 1));
        jpaPriceRepository.save(createPriceEntity(LocalDateTime.of(2020, 6, 15, 16, 0, 0),
            LocalDateTime.of(2020, 12, 31, 23, 59, 59), 4L, BigDecimal.valueOf(38.95), 1));
    }

    private PriceEntity createPriceEntity(LocalDateTime startDate, LocalDateTime endDate,
                                          Long priceList, BigDecimal price, int priority) {

        return PriceEntity.builder()

            .brandId(1L)
            .productId(35455L)
            .startDate(startDate)
            .endDate(endDate)
            .priceList(priceList)
            .price(price)
            .priority(priority)
            .currency("EUR")
            .build();
    }

    @Test
    void testGetPriceAt10AMOn14thJune() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("applicationDate", "2020-06-14T10:00:00")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{'price': 35.50}"));
    }

    @Test
    void testGetPriceAt4PMOn14thJune() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("applicationDate", "2020-06-14T16:00:00")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{'price': 25.45}"));
    }

    @Test
    void testGetPriceAt9PMOn14thJune() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("applicationDate", "2020-06-14T21:00:00")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{'price': 35.50}"));
    }

    @Test
    void testGetPriceAt10AMOn15thJune() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("applicationDate", "2020-06-15T10:00:00")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{'price': 30.50}"));
    }

    @Test
    void testGetPriceAt9PMOn16thJune() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("applicationDate", "2020-06-16T21:00:00")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{'price': 38.95}"));
    }
}
