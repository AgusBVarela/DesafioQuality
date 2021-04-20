package TravelPackage.services;

import TravelPackage.dtos.FlightDTO;
import TravelPackage.dtos.PaymentMethodDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidBookingException;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.exceptions.InvalidReservationException;
import TravelPackage.repositories.FlightRepository;
import TravelPackage.utils.Price;
import TravelPackage.validations.FlightValidation;
import org.springframework.stereotype.Service;

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

    @Override
    public TicketDTO reservation (TicketDTO ticket) throws InvalidParamException, InvalidBookingException, InvalidReservationException {
        /*Encargado de devolver una ticket con la información de la reserva realizada.
        Delega la búsqueda de vuelos al repositorio.
          Valida que los filtros sean válidos y de no ser así lanza una excepción. D*/

        FlightValidation.ValidateTicket(ticket);

        Double personPrice = flightRepository.reservation(ticket);
        this.updateTicketPrice(ticket, personPrice);
        ticket.getFlightReservation().setPaymentMethod(null);
        return ticket;
    }

    private void updateTicketPrice(TicketDTO ticket, Double price){
        PaymentMethodDTO paymentMethod = ticket.getFlightReservation().getPaymentMethod();
        Double totalPrice = price * ticket.getFlightReservation().getSeats();
        ticket.setAmount(totalPrice);
        ticket.setInterest(totalPrice * Price.getInterest(paymentMethod));
        ticket.setTotal(totalPrice + ticket.getInterest());
    }


}
