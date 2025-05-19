package org.example.infrastructure.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("query")
class PriceControllerTest {

    private static final String BEARER = "Bearer ";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String authToken;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String TOKEN = "token";


    @BeforeAll
    static void setUp(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper,
                      @Value("${test.username}") String testUsername,
                      @Value("${test.password}") String testPassword,
                      @Value("${auth.url}") String urlLogin) throws Exception {

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put(USERNAME, testUsername);
        loginRequest.put(PASSWORD, testPassword);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlLogin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        Map<String, String> responseMap = objectMapper.readValue(responseJson, Map.class);
        authToken = BEARER + responseMap.get(TOKEN);
    }

    @Test
    void testPrueba1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/prices/")
                        .header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"queryDate\": \"2020-06-14T10:00:00.000Z\", \"productId\": 35455, \"brandId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(35.50));
    }

    @Test
    void testPrueba2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/prices/")
                        .header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"queryDate\": \"2020-06-14T16:00:00.000Z\", \"productId\": 35455, \"brandId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(25.45));
    }

    @Test
    void testPrueba3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/prices/")
                        .header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"queryDate\": \"2020-06-14T21:00:00.000Z\", \"productId\": 35455, \"brandId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(35.50));
    }

    @Test
    void testPrueba4() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/prices/")
                        .header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"queryDate\": \"2020-06-15T10:00:00.000Z\", \"productId\": 35455, \"brandId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(30.50));
    }

    @Test
    void testPrueba5() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/prices/")
                        .header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"queryDate\": \"2020-06-16T21:00:00.000Z\", \"productId\": 35455, \"brandId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(35455))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(38.95));
    }

    @Test
    void test6SinFecha() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/prices/")
                        .header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 35455, \"brandId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void test7UrlIncorrecta() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/priceseeee/")
                        .header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 35455, \"brandId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void test8argumentosIncorrectors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/prices/")
                        .header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 35455}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

