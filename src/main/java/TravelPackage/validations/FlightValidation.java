package TravelPackage.validations;

import TravelPackage.dtos.FlightDTO;
import TravelPackage.dtos.PaymentMethodDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidInstanceDBException;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.exceptions.InvalidReservationException;
import TravelPackage.utils.CompareDate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

public class FlightValidation {

    public static void ValidateFlightFromDB(String[] datos) throws InvalidInstanceDBException {
       /*Valida la instancias recibida por parámetro del archivo de DB. En caso de sus valores ser erróneos
        o poseer valores en esas condiciones, lanza una excepción.*/
        int maxParams = 7;
        String message = "";
        for(int i = 0; i < maxParams; i++){
            if(datos == null) message += "La DB de vuelos está incompleta";
            if(datos[i] == null)  message += "El vuelo " + datos[0] + " posee datos vacios en la DB.";
        }

        try {
            double price = Double.valueOf(datos[4].replace("$", "").replace(".", ""));
            Date dateFrom =new SimpleDateFormat("dd/MM/yyyy").parse(datos[5]);
            Date dateTo =new SimpleDateFormat("dd/MM/yyyy").parse(datos[6]);

        } catch (Exception e) {
            message += "El vuelo " + datos[0] + " posee datos en un formato inesperado: " + e.getMessage();
            System.out.println(message);
            throw new InvalidInstanceDBException("Ocurrió un error inesperado, por favor contactese con soporte.");
        }
    }

    public static void ValidateTicket(TicketDTO ticket) throws InvalidParamException {
       /*Valida el ticket recibida por parámetro. En caso de sus valores ser erróneos
        o poseer valores en esas condiciones, lanza una excepción.*/
        String message = "";
        FlightDTO flight = (FlightDTO) ticket.getFlightReservation();
        LocalDate dateTo = flight.getDateTo();
        LocalDate dateFrom = flight.getDateFrom();
        if(CompareDate.AfterTo(dateFrom, dateTo)) message += "Fecha de egreso posterior a la de regreso. ";
        if(flight.getDestination().isEmpty()) message += "El destino debe estar especificado.";
        if(flight.getOrigin().isEmpty()) message += "El origen debe estar especificado.";
        if(flight.getSeats() < 0) message += "La cantidad solicitada debe ser positiva.";
        PaymentMethodDTO p = flight.getPaymentMethod();
        message += GenericValidations.validatePaymentMethod(p);
        if(!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", ticket.getUserName())) message += "Por favor ingrese un email válido.";

        if(!message.isEmpty()) throw new InvalidParamException(message);
    }


    public static void ValidateSearchParams(Map<String, String> params) throws InvalidParamException {
        if(params != null && params.size() == 0) return;
        if(params.size() == 4 && params.containsKey("dateFrom")
                && params.containsKey("dateTo") && params.containsKey("origin")
                && params.containsKey("destination")) {
            try {
                LocalDate dateFrom = LocalDate.parse(params.get("dateFrom"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate dateTo = LocalDate.parse(params.get("dateTo"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                if(CompareDate.AfterTo(dateFrom, dateTo)) throw new InvalidParamException("Fecha de entrada posterior a fecha de salida");
            }catch (Exception e){
                throw new InvalidParamException("Formato de fecha " + params.get("dateFrom") +" debe ser 'dd/mm/aaaa'.");
            }
            if(params.get("destination").isEmpty()) throw new InvalidParamException("El destino debe estar especificado.");
            if(params.get("origin").isEmpty()) throw new InvalidParamException("El origen debe estar especificado.");
        } else{
            throw new InvalidParamException("Los parámetros recibidos no son los esperados: 'dateFrom', 'dateTo', 'destination'.");
        }
    }

    public static void validateReservation(FlightDTO flight, TicketDTO ticket) throws InvalidReservationException {
        FlightDTO flightDTO = (FlightDTO) ticket.getFlightReservation();
        String message = "";
        if(!CompareDate.Equals(flightDTO.getDateFrom(), flight.getDateFrom())) message += "Fecha de reserva de egreso no coincide con la disponible.";
        if(!CompareDate.Equals(flightDTO.getDateTo(), flight.getDateTo())) message += "Fecha de reserva de regreso no coincide con la disponible.";
         if(!flightDTO.getDestination().equalsIgnoreCase(flight.getDestination())) message += "El destino solicitado no coincide con el correspondiente al vuelo.";
         if(!flightDTO.getOrigin().equalsIgnoreCase(flight.getOrigin())) message += "El origen solicitado no coincide con el correspondiente al vuelo.";
         if(!flightDTO.getSeatType().equalsIgnoreCase(flight.getSeatType())) message += "El tipo solicitado no coincide con el correspondiente al vuelo.";

        if(!message.isEmpty()) throw new InvalidReservationException(message);

    }
}
