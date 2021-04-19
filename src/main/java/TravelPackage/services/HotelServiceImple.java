package TravelPackage.services;

import TravelPackage.dtos.HotelDTO;
import TravelPackage.dtos.PaymentMethodDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidBookingException;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.repositories.HotelRepository;
import TravelPackage.validations.HotelValidations;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

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

        HotelValidations.ValidateSearchParams(params);
        return hotelRepository.getHotels(params);
    }

    @Override
    public TicketDTO booking(TicketDTO ticket) throws InvalidParamException, InvalidBookingException {
        HotelValidations.ValidateTicket(ticket);

        Double priceNight = hotelRepository.booking(ticket);
        this.updateTicketPrice(ticket, priceNight);
        ticket.getBooking().setPaymentMethod(null);
        return ticket;
    }

    private void updateTicketPrice(TicketDTO ticket, Double priceNight){
        PaymentMethodDTO paymentMethod = ticket.getBooking().getPaymentMethod();
        HotelDTO hotel = (HotelDTO) ticket.getBooking();
        //long daysBetween = DAYS.between(hotel.getDateFrom(), ticket.getBooking().getDateTo());
        long daysBetween = DAYS.between(hotel.getDateFrom(), hotel.getDateTo());
        Double totalPrice = priceNight * daysBetween;
        ticket.setAmount(totalPrice);
        ticket.setInterest(totalPrice * this.getInterest(paymentMethod));
        ticket.setTotal(totalPrice + ticket.getInterest());
    }

    private Double getInterest(PaymentMethodDTO paymentMethodDTO){
        if(paymentMethodDTO.getType().equalsIgnoreCase("CREDIT")){
            if(paymentMethodDTO.getDues() <= 3) return 0.05;
            else if(paymentMethodDTO.getDues() <= 6) return 0.10;
            else if(paymentMethodDTO.getDues() <= 9) return 0.15;
            else if(paymentMethodDTO.getDues() <= 12) return 0.20;
        }
        return 0d;
    }
}
