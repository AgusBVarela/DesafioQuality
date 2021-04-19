package TravelPackage.services;


import TravelPackage.controllers.HotelController;
import TravelPackage.dtos.HotelDTO;
import TravelPackage.repositories.HotelRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(HotelService.class)
public class HotelServiceTest {
    @MockBean
    private HotelRepository hotelRepository;

    @Autowired
    private HotelServiceImple hotelService;

    @Autowired
    private MockMvc mvc;


    @BeforeAll
    static void setUp() throws IOException {
    }

    @Test
    public void getAll() throws Exception {

      /*  ObjectMapper objectMapper = new ObjectMapper();
        List<HotelDTO> hotels = objectMapper.readValue(
                new File("src/test/resources/dbHotelsTest.json"),
                new TypeReference<>() {
                });
        Mockito.when(hotelRepository.getHotels(new HashMap<>())).thenReturn(hotels);

        *//*MvcResult mvcResult = this.mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/hotels").accept(MediaType.ALL)
        ).andReturn();*//*
        //List<ArticleDTO> actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
        //        new TypeReference<>(){});

        MvcResult mvcResult = mvc.perform(get("/api/v1/hotels"))
                .andExpect(status().isOk()).andReturn();

        List<HotelDTO> responseHotels =
                objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<>() {
                });

        Assertions.assertIterableEquals(hotels, responseHotels);*/
    }
}
