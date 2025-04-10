package org.example.infrastructure.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPrueba1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/prices/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fechaConsulta\": \"2020-06-14T10:00:00.000Z\", \"productId\": 35455, \"brandId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(35.50));
    }

    @Test
    void testPrueba2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/prices/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fechaConsulta\": \"2020-06-14T16:00:00.000Z\", \"productId\": 35455, \"brandId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(25.45));
    }

    @Test
    void testPrueba3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/prices/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fechaConsulta\": \"2020-06-14T21:00:00.000Z\", \"productId\": 35455, \"brandId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(35.50));
    }

    @Test
    void testPrueba4() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/prices/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fechaConsulta\": \"2020-06-15T10:00:00.000Z\", \"productId\": 35455, \"brandId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(30.50));
    }

    @Test
    void testPrueba5() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/prices/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fechaConsulta\": \"2020-06-16T21:00:00.000Z\", \"productId\": 35455, \"brandId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(38.95));
    }

    @Test
    void test6SinFecha() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/prices/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 35455, \"brandId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void test7UrlIncorrecta() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/priceseeee/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 35455, \"brandId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void test8argumentosIncorrectors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/prices/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 35455}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}