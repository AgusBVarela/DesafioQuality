package TravelPackage.services;

import TravelPackage.dtos.HotelDTO;
import TravelPackage.exceptions.InvalidParamException;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface HotelService {

    List<HotelDTO> getHotels(Map<String, String> params) throws ParseException, InvalidParamException;

}
