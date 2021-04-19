package TravelPackage.controllers;

import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.services.FlightServiceImple;
import TravelPackage.services.HotelServiceImple;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Map;

@RestController
public class FlightController {

    private FlightServiceImple flightService;

    public FlightController(FlightServiceImple flight){
        this.flightService = flight;
    }

    @GetMapping("/api/v1/flights")
    public ResponseEntity getHotels(@RequestParam Map<String, String> params) throws ParseException, InvalidParamException {
        return new ResponseEntity(flightService.getFlights(params), HttpStatus.OK);
    }


}
