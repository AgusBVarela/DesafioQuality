package TravelPackage.repositories;

import TravelPackage.dtos.HotelDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidBookingException;
import TravelPackage.exceptions.InvalidParamException;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface HotelRepository {

    List<HotelDTO> getHotels(Map<String, String> params) throws ParseException, InvalidParamException;
    Double booking (TicketDTO ticket) throws InvalidBookingException;

}
