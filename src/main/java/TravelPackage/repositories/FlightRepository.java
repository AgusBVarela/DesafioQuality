package TravelPackage.repositories;

import TravelPackage.dtos.FlightDTO;
import TravelPackage.dtos.HotelDTO;
import TravelPackage.exceptions.InvalidParamException;

import java.util.List;
import java.util.Map;

public interface FlightRepository {

    List<FlightDTO> getFlights(Map<String, String> filters) throws InvalidParamException;
}
