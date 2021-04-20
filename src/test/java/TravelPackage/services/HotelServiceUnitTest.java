package TravelPackage.services;

import TravelPackage.dtos.HotelDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.repositories.HotelRepository;
import TravelPackage.repositories.HotelRepositoryImple;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelServiceUnitTest {
    private HotelService hotelService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        hotelService = new HotelServiceImple(new HotelRepositoryImple("src/test/resources/repositoryTest/dbTravelTest.csv"));
    }

    @Test
    @DisplayName("Devuelve todos los hoteles.")
    void getAllHotelsWithoutFilter_OK() throws IOException, ParseException, InvalidParamException {

        List<HotelDTO> hotels = objectMapper.readValue(
                new File("src/test/resources/dbHotelsTest.json"),
                new TypeReference<>() {
                });

        Assertions.assertIterableEquals(hotels, hotelService.getHotels(new HashMap<>()));
    }

    @Test
    @DisplayName("Devuelve todos los hoteles que cumplen con el filtro.")
    void getAllHotelsWithFilter_OK() throws IOException, ParseException, InvalidParamException {

        List<HotelDTO> hotels = objectMapper.readValue(
                new File("src/test/resources/dbHotelsWithFilterTest2.json"),
                new TypeReference<>() {
                });

        Map<String, String> filter = new HashMap<>();
        filter.put("dateFrom", "10/02/2021");
        filter.put("dateTo", "18/03/2021");
        filter.put("destination", "Puerto Iguazú");

        Assertions.assertIterableEquals(hotels, hotelService.getHotels(filter));
    }

    @Test
    @DisplayName("Busca filtrar los hoteles con un destino no encontrado. Recibe excepcion.")
    void getAllHotelsWithFilter_ERROR() throws IOException, ParseException, InvalidParamException {
        /*Valida que lance una excepcion en caso de que el destino no exista.*/

        Map<String, String> filter = new HashMap<>();
        filter.put("dateFrom", "10/02/2021");
        filter.put("dateTo", "18/03/2021");
        filter.put("destination", "ErrorDestino");

        InvalidParamException thrown = Assertions.assertThrows(
                InvalidParamException.class,
                () -> hotelService.getHotels(filter),
                "Expected getHotels() to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().contains("El destino elegido no existe"));
    }

    @Test
    @DisplayName("Intenta comprar con un hotelCode erroneo.")
    void booking_ERROR() throws IOException {
        /*Valida que lance una excepcion en caso de que el codeHerror sea erroneo a la hora de comprar.*/
        //TicketDTO ticket = this.getBookingTicket();
/*
        TicketDTO ticket = objectMapper.readValue(
                new File("src/test/resources/bookingHotelCodeError.json"),
                new TypeReference<>() {
                });

        InvalidParamException thrown = Assertions.assertThrows(
                InvalidParamException.class,
                () -> hotelService.booking(ticket),
                "Expected booking to throw, but it didn't"
        );

        //TODO cambiar mensaje de eror
        Assertions.assertTrue(thrown.getMessage().contains("El destino elegido no existe"));*/
    }

    private TicketDTO getBookingTicket() {
        String userName = "seba_gonzalez@unmail.com";


        HotelDTO hotel = new HotelDTO();
        //  hotel.setDateFrom(new LocalDate(2021, 02,12) );
        TicketDTO ticket = new TicketDTO(userName,hotel);
        return ticket;
    }
}
