package TravelPackage.controllers;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.services.HotelServiceImple;
import TravelPackage.validations.HotelValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
