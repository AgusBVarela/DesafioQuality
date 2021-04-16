package TravelPackage.services;

import TravelPackage.dtos.HotelDTO;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.repositories.HotelRepository;
import TravelPackage.validations.HotelValidations;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class HotelServiceImple implements HotelService {

    private HotelRepository hotelRepository;

    public HotelServiceImple(HotelRepository repository){
        this.hotelRepository = repository;
    }

    @Override
    public List<HotelDTO> getHotels(Map<String, String> params) throws InvalidParamException, ParseException {
        /*Encargado de devolver una lista con los hoteles con las condiciones recibidas por parámetros:
        filtrados según corresponda. Delega la búsqueda de hoteles al repositorio.
          Valida que los filtros sean válidos y de no ser así lanza una excepción. D*/

        HotelValidations.ValidateParams(params);
        return hotelRepository.getHotels(params);
    }
}
