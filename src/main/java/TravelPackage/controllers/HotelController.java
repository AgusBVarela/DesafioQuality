package TravelPackage.controllers;
import TravelPackage.dtos.StatusDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidBookingException;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.services.HotelServiceImple;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
public class HotelController {

    private HotelServiceImple hotelService;

    public HotelController(HotelServiceImple hotel){
        this.hotelService = hotel;
    }

    @GetMapping("/api/v1/hotels")
    public ResponseEntity getHotels(@RequestParam Map<String, String> params) throws ParseException, InvalidParamException {
        return new ResponseEntity(hotelService.getHotels(params), HttpStatus.OK);
    }

    @PostMapping("/api/v1/booking")
    public ResponseEntity booking(@RequestBody TicketDTO ticket) throws InvalidParamException, InvalidBookingException {
        TicketDTO response = hotelService.booking(ticket);
        response.setStatusCode(new StatusDTO("200", "El proceso terminó satisfactoriamente"));
        return new ResponseEntity(response,HttpStatus.OK);
    }

    /*@ExceptionHandler(NullPointerException.class) //Este endpoint es por si se envia un atributo en el JSON con distinto tipado
    public ResponseEntity exceptionHandler1(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = "Error, Format Json not valid";
        ErrorDTO errorDTO = new ErrorDTO("Grave error", message, HttpStatus.BAD_REQUEST);
        return new ResponseEntity(errorDTO, errorDTO.getStatus());
    }*/


}
