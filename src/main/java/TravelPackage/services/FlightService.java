package TravelPackage.services;

import TravelPackage.dtos.FlightDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidBookingException;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.exceptions.InvalidReservationException;

import java.util.List;
import java.util.Map;

public interface FlightService {

    List<FlightDTO> getFlights (Map<String, String> params) throws InvalidParamException;
    public TicketDTO reservation (TicketDTO ticket) throws InvalidParamException, InvalidBookingException, InvalidReservationException;
}
