package TravelPackage.services;

import TravelPackage.dtos.FlightDTO;
import TravelPackage.dtos.HotelDTO;
import TravelPackage.exceptions.InvalidParamException;

import java.util.List;
import java.util.Map;

public interface FlightService {

    List<FlightDTO> getFlights (Map<String, String> params) throws InvalidParamException;

}
