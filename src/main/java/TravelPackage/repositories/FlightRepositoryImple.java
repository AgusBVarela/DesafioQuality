package TravelPackage.repositories;

import TravelPackage.dtos.FlightDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidBookingException;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.exceptions.InvalidReservationException;
import TravelPackage.utils.CompareDate;
import TravelPackage.utils.DBUtil;
import TravelPackage.validations.FlightValidation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class FlightRepositoryImple implements FlightRepository {

    List<FlightDTO> flights;

    public FlightRepositoryImple(@Value("${vuelo_path:src/main/resources/dbVuelo.csv}") String path)
    {
        //Todo en luagr de sacarlo de aca sacarlo de properties
        flights = DBUtil.loadFlightsDB(path);
    }

    @Override
    public List<FlightDTO> getFlights(Map<String, String> filters) throws InvalidParamException {

        if(filters.size() == 0) return this.flights;

        List<FlightDTO> flightsByDestinitation = this.flights.stream().filter(
                flight-> (flight.getDestination().equalsIgnoreCase(filters.get("destination")))
        && flight.getOrigin().equalsIgnoreCase(filters.get("origin"))).collect(Collectors.toList());
        if(flightsByDestinitation.size() == 0) throw new InvalidParamException("El vuelo con destino y origen elegido no existe.");

        LocalDate dateFrom = LocalDate.parse(filters.get("dateFrom"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate dateTo = LocalDate.parse(filters.get("dateTo"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        List<FlightDTO> result = flightsByDestinitation.stream().filter(
                flight-> ((CompareDate.Equals(flight.getDateFrom(), dateFrom) || CompareDate.AfterTo(flight.getDateFrom(), dateFrom)) &&
                         (CompareDate.Equals(flight.getDateTo(), dateTo) || CompareDate.AfterTo(dateTo, flight.getDateTo())))).collect(Collectors.toList());

        return result;
    }

    @Override
    public Double reservation(TicketDTO ticket) throws InvalidReservationException {
        /*Encargado de buscar el vuelo que cumpla con las características del ticket.
        En caso de corresponder, el mismo se califica como comprado y se devuelve el precio por persona correspondiente. */
        FlightDTO flightDTO = this.flights.stream().filter(flight -> flight.getFlightNumber().equals(ticket.getFlightReservation().getFlightNumber())).findFirst().orElse(null);
        if(flightDTO == null ) throw  new InvalidReservationException("El vuelo con numero '" + ticket.getFlightReservation().getFlightNumber() + "' no existe." );
        FlightValidation.validateReservation(flightDTO, ticket);

        return flightDTO.getPrice();
    }

}
