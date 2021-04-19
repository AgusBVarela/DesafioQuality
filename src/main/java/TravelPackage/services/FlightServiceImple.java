package TravelPackage.services;

import TravelPackage.dtos.FlightDTO;
import TravelPackage.dtos.HotelDTO;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.repositories.FlightRepository;
import TravelPackage.repositories.HotelRepository;
import TravelPackage.validations.FlightValidation;
import TravelPackage.validations.HotelValidations;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class FlightServiceImple implements FlightService{

    private FlightRepository flightRepository;

    public FlightServiceImple(FlightRepository repository){
        this.flightRepository = repository;
    }

    @Override
    public List<FlightDTO> getFlights (Map<String, String> params) throws InvalidParamException {
        /*Encargado de devolver una lista con los vuelos con las condiciones recibidas por parámetros:
        filtrados según corresponda. Delega la búsqueda de vuelos al repositorio.
          Valida que los filtros sean válidos y de no ser así lanza una excepción. D*/

        FlightValidation.ValidateSearchParams(params);
        return flightRepository.getFlights(params);
    }
}
