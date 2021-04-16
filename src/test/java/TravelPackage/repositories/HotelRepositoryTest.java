package TravelPackage.repositories;

import TravelPackage.dtos.HotelDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public class HotelRepositoryTest {
    private HotelRepository hotelRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        hotelRepository = new HotelRepositoryImple("src/test/resources/dbTravelTest.csv");
    }

    @Test
    void getAllHotels_OK() throws IOException, ParseException {

        List<HotelDTO> hotels = objectMapper.readValue(
                new File("src/test/resources/dbHotelsTest.json"),
                new TypeReference<>() {
                });

        Assertions.assertEquals(hotelRepository.getHotels(new HashMap<>()), hotels);
    }
}
