package TravelPackage.controllers;

import TravelPackage.dtos.HotelDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.services.HotelServiceImple;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@EnableWebMvc
public class HotelControllerTest2 {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @InjectMocks
    HotelController hotelController;

    @Mock
    HotelServiceImple service;

    private MockMvc mvc;

    private ObjectMapper mapper;

    @Before
    public void setup()
    {
        mvc = MockMvcBuilders.standaloneSetup(hotelController).build();

        mapper = new ObjectMapper().findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    @DisplayName("Test de integracion 01: Encargado de mockear la respuesta del service y devolver lo mismo.")
    public void getAll_OK() throws Exception
    {
        List<HotelDTO> hotels = mapper.readValue(
                new File("src/test/resources/dbTestIntegracionHotel_01.json"),
                new TypeReference<>() {
                });

        when(service.getHotels(any())).thenReturn(hotels);


        MvcResult result = this.mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/hotels").accept(MediaType.ALL)
        ).andReturn();

        String response = result.getResponse().getContentAsString();
        assertNotNull(response);

        List<HotelDTO> listResponse = Arrays.asList(mapper.readValue(response, HotelDTO[].class));

        Assertions.assertEquals(hotels, listResponse);
      }

    @Test
    @DisplayName("Test de integracion 02: Encargado de mockear la compra en el service.")
    public void booking() throws Exception
    {
        /*//Todo: pasar de json a strin para apsar al psot
        TicketDTO ticketInput = mapper.readValue(
                new File("src/test/resources/dbTestIntegracionHotelInput_02.json"),
                new TypeReference<>() {
                });

        TicketDTO ticket = mapper.toString(
                new File("src/test/resources/dbTestIntegracionHotel_02.json"),
                new TypeReference<>() {
                });

        when(service.booking(any())).thenReturn(ticket);


        *//*MvcResult result = this.mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/booking").accept(MediaType.ALL)
        ).andReturn();*//*

        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.post("/api/v1/booking").contentType(APPLICATION_JSON_UTF8)
                .content("src/test/resources/dbTestIntegracionHotel_02.json"))
                .andExpect(status().isOk());

        String r = result.toString();
       // String response = result.getResponse().getContentAsString();
       *//* assertNotNull(response);

        TicketDTO ticketResponse = mapper.readValue(response, TicketDTO.class);
        assertNotNull(response);*//*
        assertNotNull(r);

        TicketDTO ticketResponse = mapper.readValue(r, TicketDTO.class);
        assertNotNull(ticketResponse);

        //TicketDTO ticketResponse = mapper.readValue(response, TicketDTO.class);

        Assertions.assertEquals(ticket, ticketResponse);*/
    }
}
