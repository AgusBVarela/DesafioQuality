package TravelPackage.repositories;

import TravelPackage.dtos.FlightDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidBookingException;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.exceptions.InvalidReservationException;

import java.util.List;
import java.util.Map;

public interface FlightRepository {

    List<FlightDTO> getFlights(Map<String, String> filters) throws InvalidParamException;

     Double reservation(TicketDTO ticket) throws InvalidBookingException, InvalidReservationException;

}
