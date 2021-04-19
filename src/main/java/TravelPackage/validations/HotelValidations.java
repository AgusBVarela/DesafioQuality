package TravelPackage.validations;

import TravelPackage.dtos.HotelDTO;
import TravelPackage.dtos.PaymentMethodDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidBookingException;
import TravelPackage.exceptions.InvalidInstanceDBException;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.utils.CompareDate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

public class HotelValidations {

    public static void ValidateSearchParams(Map<String, String> params) throws InvalidParamException {
        if(params != null && params.size() == 0) return;
        if(params.size() == 3 && params.containsKey("dateFrom")
                && params.containsKey("dateTo") & params.containsKey("destination")) {
            try {
                LocalDate dateFrom = LocalDate.parse(params.get("dateFrom"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate dateTo = LocalDate.parse(params.get("dateTo"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                if(CompareDate.AfterTo(dateFrom, dateTo)) throw new InvalidParamException("Fecha de entrada posterior a fecha de salida");
            }catch (Exception e){
                throw new InvalidParamException("Formato de fecha " + params.get("dateFrom") +" debe ser 'dd/mm/aaaa'.");
            }
            if(params.get("destination").isEmpty()) throw new InvalidParamException("El destino debe estar especificado.");
        } else{
            throw new InvalidParamException("Los parámetros recibidos no son los esperados: 'dateFrom', 'dateTo', 'destination'.");
        }
    }

    public static void ValidateTicket(TicketDTO ticket) throws InvalidParamException {
        String message = "";
        HotelDTO hotel = (HotelDTO) ticket.getBooking();
        LocalDate dateTo = hotel.getDateTo();
        LocalDate dateFrom = hotel.getDateFrom();
        if(CompareDate.AfterTo(dateFrom, dateTo)) message += "Fecha de entrada posterior a fecha de salida. ";
        if(hotel.getDestination().isEmpty()) message += "El destino debe estar especificado.";
        if(hotel.getPeopleAmount() < 1 || hotel.getPeopleAmount() > 10) message += "La cantidad solicitada debe estar entre 1 y 10.";
        message += validateRoomType(hotel.getRoomType(), hotel.getPeopleAmount());
        message += validatePaymentMethod(ticket.getBooking().getPaymentMethod());
        if(!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", ticket.getUserName())) message += "Por favor ingrese un email válido.";

        if(!message.isEmpty()) throw new InvalidParamException(message);
    }

    public static void ValidateBooking(HotelDTO hotel, TicketDTO ticket) throws InvalidBookingException {
        HotelDTO hotelToBooking = (HotelDTO) ticket.getBooking();
        String message = "";
        if(hotel.getReserved()) message += "El hotel seleccionado no está disponible en la fecha establecida.";
        if(CompareDate.AfterTo(hotelToBooking.getDateFrom(), hotel.getDateFrom())) message += "Fecha de entrada es previa a la disponibilidad del hotel. ";
        if(CompareDate.AfterTo(hotelToBooking.getDateTo(), hotel.getDateTo())) message += "Fecha de salida es posterior a la disponibilidad del hotel.";
        if(!hotelToBooking.getDestination().equals(hotel.getDestination())) message += "El destino solicitado no pertenece al hotel elegido.";
        message += validateRoomType(hotel.getRoomType(), hotelToBooking.getPeopleAmount());

        if(!message.isEmpty()) throw new InvalidBookingException(message);
    }

    private static String validatePaymentMethod(PaymentMethodDTO paymentMethodDTO){
        switch (paymentMethodDTO.getType().toUpperCase()){
            case "CREDIT":
                if(paymentMethodDTO.getDues() < 1 || paymentMethodDTO.getDues() > 12) return "El número de cuotas debe ser entre 1 y 12.";
                break;
            case "DEBIT":
                if(paymentMethodDTO.getDues() != 1 ) return "El número de cuotas por pagar con débito debe ser 1.";
                break;
            default:
                return "EL tipo de pago debe ser 'CREDIT' o 'DEBIT'.";
        }
        return "";
    }

    private static String validateRoomType(String roomType, int peopleAmount){
        switch(roomType.toUpperCase()) {
            case "SIMPLE":
                if (peopleAmount == 1) return "";
                break;
            case "DOBLE":
                if (peopleAmount == 2) return "";
                break;
            case "MÚLTIPLE":
                if (peopleAmount >= 3 && peopleAmount <= 10) return "";
            default:
                break;
        }
        return "El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella";

    }

    public static void ValidateHotelFromDB(String[] datos) throws InvalidInstanceDBException {
       /*Valida la instancias recibida por parámetro del archivo de DB. En caso de sus valores ser erróneos
        o poseer valores en esas condiciones, lanza una excepción.*/
            int maxParams = 8;
            String message = "";
            for(int i = 0; i < maxParams; i++){
                if(datos == null) message += "La DB de hoteles está incompleta";
                if(datos[i] == null)  message += "El hotel " + datos[0] + " posee datos vacios en la DB.";
            }

            try {
                double price = Double.valueOf(datos[4].replace("$", "").replace(".", ""));
                Boolean reservated = datos[7].toUpperCase() == "SI" ? true : false;
                Date dateFrom =new SimpleDateFormat("dd/MM/yyyy").parse(datos[5]);
                Date dateTo =new SimpleDateFormat("dd/MM/yyyy").parse(datos[6]);

            } catch (Exception e) {
                message += "El hotel " + datos[0] + " posee datos en un formato inesperado: " + e.getMessage();
                System.out.println(message);
                throw new InvalidInstanceDBException("Ocurrió un error inesperado, por favor contactese con soporte.");
            }
        }

}
