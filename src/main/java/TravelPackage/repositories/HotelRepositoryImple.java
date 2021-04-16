package TravelPackage.repositories;

import TravelPackage.dtos.HotelDTO;
import TravelPackage.exceptions.InvalidInstanceDBException;
import TravelPackage.exceptions.InvalidParamException;
import TravelPackage.validations.HotelValidations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                hotel-> (hotel.getCity().toUpperCase().equals(filters.get("destination").toUpperCase()))).collect(Collectors.toList());
        if(hotelsByDestination.size() == 0) throw new InvalidParamException("El destino solicitado no existe.");

        Date dateFrom =new SimpleDateFormat("dd/MM/yyyy").parse(filters.get("dateFrom"));
        Date dateTo =new SimpleDateFormat("dd/MM/yyyy").parse(filters.get("dateTo"));

        List<HotelDTO> result = hotelsByDestination.stream().filter(
                hotel-> ((hotel.getDateFrom().before(dateFrom) || hotel.getDateFrom().equals(dateFrom)) &&
                        (hotel.getDateTo().after(dateTo) || hotel.getDateTo().equals(dateTo)))).collect(Collectors.toList());

        return result;
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
                HotelDTO hotelDTO = new HotelDTO(hotel);
                hotels.add(hotelDTO);
            }
        } catch (FileNotFoundException | InvalidInstanceDBException e ) {
            e.printStackTrace();
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
