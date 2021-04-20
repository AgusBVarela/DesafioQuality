package TravelPackage.controllers;

import TravelPackage.dtos.StatusDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidBookingException;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.exceptions.InvalidReservationException;
import TravelPackage.services.FlightServiceImple;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/api/v1/flight-reservation")
    public ResponseEntity reservation(@RequestBody TicketDTO ticket) throws InvalidBookingException, InvalidParamException, InvalidReservationException {
        TicketDTO response = flightService.reservation(ticket);
        response.setStatusCode(new StatusDTO("200", "El proceso terminó satisfactoriamente"));
        return new ResponseEntity(response,HttpStatus.OK);
    }

}
