package TravelPackage.controllers;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidBookingException;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.services.HotelServiceImple;
import TravelPackage.validations.HotelValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HotelController {

    private HotelServiceImple hotelService;

    public HotelController(HotelServiceImple hotel){
        this.hotelService = hotel;
    }

    @GetMapping("/hotels")
    public ResponseEntity getHotels(@RequestParam Map<String, String> params) throws ParseException, InvalidParamException {
        //HotelValidations.ValidateParams(params);
        return new ResponseEntity(hotelService.getHotels(params), HttpStatus.OK);
    }

    @PostMapping("/booking")
    public ResponseEntity booking(@RequestBody TicketDTO ticket) throws InvalidParamException, InvalidBookingException {
        //ResponseDTO response = new ResponseDTO(catalogueService.buyArticles(buy), new StatusDTO("200", "La solicitud de compra se completó con éxito"));
        return new ResponseEntity(hotelService.booking(ticket), HttpStatus.OK);
    }

    /*@ExceptionHandler(NullPointerException.class) //Este endpoint es por si se envia un atributo en el JSON con distinto tipado
    public ResponseEntity exceptionHandler1(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String message = "Error, Format Json not valid";
        ErrorDTO errorDTO = new ErrorDTO("Grave error", message, HttpStatus.BAD_REQUEST);
        return new ResponseEntity(errorDTO, errorDTO.getStatus());
    }*/


}
