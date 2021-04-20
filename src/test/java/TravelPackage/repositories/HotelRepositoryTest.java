package TravelPackage.repositories;

import TravelPackage.dtos.HotelDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidBookingException;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.exceptions.InvalidReservationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelRepositoryTest {
    private HotelRepository hotelRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        hotelRepository = new HotelRepositoryImple("src/test/resources/repositoryTest/dbTravelTest.csv");
    }

    @Test
    @DisplayName("Devuelve todos los hoteles.")
    void getAllHotelsWithoutFilter_OK() throws IOException, ParseException, InvalidParamException {
        /*Test01: Obtiene todos los hoteles de la db sin filtro.*/
        List<HotelDTO> hotels = objectMapper.readValue(
                new File("src/test/resources/dbHotelsTest.json"),
                new TypeReference<>() {
                });

        Assertions.assertIterableEquals(hotels, hotelRepository.getHotels(new HashMap<>()));
    }

    @Test
    @DisplayName("Devuelve todos los hoteles que cumplen con el filtro.")
    void getAllHotelsWithFilter_OK() throws IOException, ParseException, InvalidParamException {
    /*Test02: obtiene los hoteles de la db con el filtro establecido.*/
        List<HotelDTO> hotels = objectMapper.readValue(
                new File("src/test/resources/dbHotelsWithFilterTest2.json"),
                new TypeReference<>() {
                });

        Map<String, String> filter = new HashMap<>();
        filter.put("dateFrom", "10/02/2021");
        filter.put("dateTo", "18/03/2021");
        filter.put("destination", "Puerto Iguazú");

        Assertions.assertIterableEquals(hotels, hotelRepository.getHotels(filter));
    }

    @Test
    @DisplayName("Busca filtrar los hoteles con un destino no encontrado. Recibe excepcion.")
    void getAllHotelsWithFilter_ERROR() throws IOException, ParseException, InvalidParamException {
        /*Test03: Valida que lance una excepcion en caso de que el destino no exista.*/

        List<HotelDTO> hotels = objectMapper.readValue(
                new File("src/test/resources/dbHotelsWithFilterTest2.json"),
                new TypeReference<>() {
                });

        Map<String, String> filter = new HashMap<>();
        filter.put("dateFrom", "10/02/2021");
        filter.put("dateTo", "18/03/2021");
        filter.put("destination", "ErrorDestino");

        InvalidParamException thrown = Assertions.assertThrows(
                InvalidParamException.class,
                () -> hotelRepository.getHotels(filter),
                "Expected getHotels() to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().contains("El destino elegido no existe"));
    }

    @Test
    @DisplayName("Generacion de una compra con codigo invalido.")
    void bookingError() throws IOException{
        /* Test04: intenta generar una compra con un código de vuelo inválido. */

        TicketDTO ticket = objectMapper.readValue(
                new File("src/test/resources/repositoryTest/ticketHotel01.json"),
                new TypeReference<>() {
                });

        InvalidBookingException thrown = Assertions.assertThrows(
                InvalidBookingException.class,
                () -> hotelRepository.booking(ticket),
                "Expected booking to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().contains("El hotel con hashCode 'B2-0002' no existe"));
    }
    @Test
    @DisplayName("Generacion de una compra con tamaño de habitación diferente a la cantidad de personas.")
    void bookingSizeError() throws IOException{
        /* Test05: intenta generar una compra con número de personas erróneo. */

        TicketDTO ticket = objectMapper.readValue(
                new File("src/test/resources/repositoryTest/ticketHotel02.json"),
                new TypeReference<>() {
                });

        InvalidBookingException thrown = Assertions.assertThrows(
                InvalidBookingException.class,
                () -> hotelRepository.booking(ticket),
                "Expected booking to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().contains("El hotel con hashCode 'CC-0002' no existe."));
    }

    /* @Test
    @DisplayName("Genera una reserva de vuelo.")
    void reservationOK() throws IOException, InvalidBookingException, InvalidReservationException {
        /* Test05: genera una reserva exitosa.
        TicketDTO ticket = objectMapper.readValue(
                new File("src/test/resources/repositoryTest/ticketFlight02.json"),
                new TypeReference<>() {
                });

        Assertions.assertEquals(6500d, repository.reservation(ticket));
    } */
}
