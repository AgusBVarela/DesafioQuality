package TravelPackage.services;

import TravelPackage.dtos.FlightDTO;
import TravelPackage.dtos.HotelDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidBookingException;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.exceptions.InvalidReservationException;
import TravelPackage.repositories.FlightRepositoryImple;
import TravelPackage.repositories.HotelRepositoryImple;
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

public class FlightServiceTest {
    private FlightService service;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        service = new FlightServiceImple(new FlightRepositoryImple("src/test/resources/repositoryTest/dbVueloTest.csv"));
    }

    @Test
    @DisplayName("Devuelve todos los vuelos.")
    void getAllWithoutFilter_OK() throws IOException, ParseException, InvalidParamException {
    /* Test01: encargado de obtener todos los vuelos sin filtros y compararlos con el json de los elementos
         que se deberían obtener. */
        List<FlightDTO> flights = objectMapper.readValue(
                new File("src/test/resources/dbFlightsTest.json"),
                new TypeReference<>() {
                });

        Assertions.assertIterableEquals(flights, service.getFlights(new HashMap<>()));
    }

    @Test
    @DisplayName("Devuelve todos los vuelos que cumplen con el filtro.")
    void getAllWithFilter_OK() throws IOException, InvalidParamException {
    /* Test02: encargado de obtener todos los vuelos con el filtro generado y compararlos con el
        json de los elementos que se deberían obtener. */
        List<FlightDTO> flights = objectMapper.readValue(
                new File("src/test/resources/repositoryTest/dbFlightsTest02.json"),
                new TypeReference<>() {
                });

        Map<String, String> filter = this.getFilters();

        Assertions.assertIterableEquals(flights, service.getFlights(filter));
    }

    @Test
    @DisplayName("Busca filtrar los vuelos con un destino no encontrado. Recibe excepcion.")
    void getAllWithFilter_ERROR() {
        /*Test03: Valida que lance una excepcion en caso de que el destino no exista.*/

        Map<String, String> filter = this.getFilters();
        filter.put("destination", "error");

        InvalidParamException thrown = Assertions.assertThrows(
                InvalidParamException.class,
                () -> service.getFlights(filter),
                "Expected getFlights to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().contains("El vuelo con destino y origen elegido no existe."));

    }

    @Test
    @DisplayName("Intenta reservar con un hotelCode erroneo.")
    void booking_ERROR() throws IOException {
        /* Test04: intenta generar una reserva con un código de vuelo inválido. */

        TicketDTO ticket = objectMapper.readValue(
                new File("src/test/resources/repositoryTest/ticketFlight01.json"),
                new TypeReference<>() {
                });

        InvalidReservationException thrown = Assertions.assertThrows(
                InvalidReservationException.class,
                () -> service.reservation(ticket),
                "Expected getFlights to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().contains("El vuelo con numero 'FFFF-0002' no existe."));
    }

    @Test
    @DisplayName("Genera una reserva de vuelo.")
    void reservationOK() throws IOException, InvalidBookingException, InvalidParamException, InvalidReservationException {
        /* Test05: genera una reserva exitosa. */
        TicketDTO ticket = objectMapper.readValue(
                new File("src/test/resources/repositoryTest/ticketFlight02.json"),
                new TypeReference<>() {
                });

        TicketDTO ticketResult = objectMapper.readValue(
                new File("src/test/resources/repositoryTest/ticketResultFlight02.json"),
                new TypeReference<>() {
                });

        Assertions.assertEquals(ticketResult, service.reservation(ticket));
    }


    private Map<String, String> getFilters(){
        HashMap filter = new HashMap<>();
        filter.put("dateFrom", "10/02/2021");
        filter.put("dateTo", "18/03/2021");
        filter.put("origin", "Puerto Iguazú");
        filter.put("destination", "Bogotá");
        return filter;
    }
}

