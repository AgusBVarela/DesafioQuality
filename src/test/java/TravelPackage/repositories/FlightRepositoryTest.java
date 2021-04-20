package TravelPackage.repositories;
import TravelPackage.dtos.FlightDTO;
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

public class FlightRepositoryTest {
       private FlightRepository repository;

        private ObjectMapper objectMapper = new ObjectMapper();

        @BeforeEach
        void setUp() {
            repository = new FlightRepositoryImple("src/test/resources/repositoryTest/dbVueloTest.csv");
        }

        @Test
        @DisplayName("Devuelve todos los vuelos de la db.")
        void getAllHotelsWithoutFilter_OK() throws IOException, InvalidParamException {
        /* Test01: encargado de obtener todos los vuelos sin filtros y compararlos con el json de los elementos
         que se deberían obtener. */
            List<FlightDTO> flights = objectMapper.readValue(
                    new File("src/test/resources/dbFlightsTest.json"),
                    new TypeReference<>() {
                    });

            Assertions.assertIterableEquals(flights, repository.getFlights(new HashMap<>()));
        }

        @Test
        @DisplayName("Devuelve todos los vuelos que cumplen con el filtro.")
        void getAllHotelsWithFilter_OK() throws IOException, ParseException, InvalidParamException {
        /* Test02: encargado de obtener todos los vuelos con el filtro generado y compararlos con el
        json de los elementos que se deberían obtener. */
            List<FlightDTO> flights = objectMapper.readValue(
                    new File("src/test/resources/repositoryTest/dbFlightsTest02.json"),
                    new TypeReference<>() {
                    });

            Map<String, String> filter = this.getFilters();

            Assertions.assertIterableEquals(flights, repository.getFlights(filter));
        }

        @Test
        @DisplayName("Busca filtrar los vuelos con un destino no encontrado. Se espera una excepcion.")
        void getAllHotelsWithFilter_ERROR() throws IOException, ParseException, InvalidParamException {
            /* Test03: Se busca obtener los vuelos con un filtro de destino no existente.
            Valida que lance una excepcion.*/

            Map<String, String> filter = this.getFilters();
            filter.put("destination", "error");

            InvalidParamException thrown = Assertions.assertThrows(
                    InvalidParamException.class,
                    () -> repository.getFlights(filter),
                    "Expected getFlights to throw, but it didn't"
            );

            Assertions.assertTrue(thrown.getMessage().contains("El vuelo con destino y origen elegido no existe."));
        }

    @Test
    @DisplayName("Generacion de una reserva con codigo invalido.")
    void reservationError() throws IOException, InvalidParamException {
        /* Test04: intenta generar una reserva con un código de vuelo inválido. */

        TicketDTO ticket = objectMapper.readValue(
                new File("src/test/resources/repositoryTest/ticketFlight01.json"),
                new TypeReference<>() {
                });

        InvalidReservationException thrown = Assertions.assertThrows(
                InvalidReservationException.class,
                () -> repository.reservation(ticket),
                "Expected getFlights to throw, but it didn't"
        );

        Assertions.assertTrue(thrown.getMessage().contains("El vuelo con numero 'FFFF-0002' no existe."));
    }

    @Test
    @DisplayName("Genera una reserva de vuelo.")
    void reservationOK() throws IOException, InvalidBookingException, InvalidReservationException {
        /* Test05: genera una reserva exitosa. */
        TicketDTO ticket = objectMapper.readValue(
                new File("src/test/resources/repositoryTest/ticketFlight02.json"),
                new TypeReference<>() {
                });

        Assertions.assertEquals(6500d, repository.reservation(ticket));
    }


    private Map<String, String>getFilters(){
            HashMap filter = new HashMap<>();
            filter.put("dateFrom", "10/02/2021");
            filter.put("dateTo", "18/03/2021");
            filter.put("origin", "Puerto Iguazú");
            filter.put("destination", "Bogotá");
            return filter;
        }
    }
