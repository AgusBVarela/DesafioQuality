package TravelPackage.repositories;

import TravelPackage.dtos.HotelDTO;
import TravelPackage.dtos.TicketDTO;
import TravelPackage.exceptions.InvalidBookingException;
import TravelPackage.exceptions.InvalidInstanceDBException;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.utils.CompareDate;
import TravelPackage.validations.HotelValidations;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class HotelRepositoryImple implements HotelRepository{

    List<HotelDTO> hotels;

    public HotelRepositoryImple(@Value("${articles_path:src/main/resources/dbTravel.csv}") String path)
    {
        //Todo en luagr de sacarlo de aca sacarlo de properties
        this.loadDB(path);
    }

    @Override
    public List<HotelDTO> getHotels(Map<String, String> filters) throws ParseException, InvalidParamException {

        if(filters.size() == 0) return this.hotels;

        List<HotelDTO> hotelsByDestination = this.hotels.stream().filter(
                hotel-> (hotel.getDestination().equalsIgnoreCase(filters.get("destination")))).collect(Collectors.toList());
        if(hotelsByDestination.size() == 0) throw new InvalidParamException("El destino elegido no existe.");

        LocalDate dateFrom = LocalDate.parse(filters.get("dateFrom"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate dateTo = LocalDate.parse(filters.get("dateTo"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        List<HotelDTO> result = hotelsByDestination.stream().filter(
                hotel-> (!hotel.isReserved() &&
                        (CompareDate.BeforeTo(hotel.getDateFrom(), dateFrom) || hotel.getDateFrom().equals(dateFrom)) &&
                        (CompareDate.AfterTo(hotel.getDateTo(), dateTo) || hotel.getDateTo().equals(dateTo)))).collect(Collectors.toList());

        return result;
    }

    @Override
    public Double booking(TicketDTO ticket) throws InvalidBookingException {
        /*Encargado de buscar el hotel que cumpla con las características del ticket.
        En caso de corresponder, el mismo se califica como reservado y se devuelve el precio por noche del mismo. */
        HotelDTO hotel = this.hotels.stream().filter(hotelDTO -> hotelDTO.getHotelCode().equals(ticket.getBooking().getHotelCode())).findFirst().orElse(null);
        if(hotel == null ) throw  new InvalidBookingException("El hotel con hashCode '" + ticket.getBooking().getHotelCode() + "' no existe." );
        HotelValidations.ValidateBooking(hotel, ticket);

        hotel.setReserved(true);
        return hotel.getNightPrice();
    }


    private void loadDB(String csvFile) {
         /*Almacena los datos de la DB en memoria. Crea un HashMap donde la clave es el
        id del articulo, y el value es el artículo.*/

        hotels = new ArrayList<>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            br.readLine(); // elimino la cabecera
            while ((line = br.readLine()) != null) {
                String[] hotel = line.split(cvsSplitBy);
                HotelValidations.ValidateHotelFromDB(hotel);
                hotels.add(new HotelDTO(hotel));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
