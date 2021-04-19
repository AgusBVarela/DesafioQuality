package TravelPackage.utils;

import TravelPackage.dtos.FlightDTO;
import TravelPackage.dtos.HotelDTO;
import TravelPackage.validations.FlightValidation;
import TravelPackage.validations.HotelValidations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
    public static List<HotelDTO> loadHotelDB(String csvFile) {
         /*Almacena los datos de la DB en memoria. Crea un HashMap donde la clave es el
        id del articulo, y el value es el artículo.*/

       List<HotelDTO> hotels = new ArrayList<HotelDTO>();
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
        return hotels;
    }
    public static List<FlightDTO> loadFlightsDB(String csvFile) {
         /*Almacena los datos de la DB en memoria. Crea un HashMap donde la clave es el
        id del articulo, y el value es el artículo.*/

       List<FlightDTO> flights = new ArrayList<FlightDTO>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            br.readLine(); // elimino la cabecera
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                FlightValidation.ValidateFlightFromDB(data);
                flights.add(new FlightDTO(data));
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
        return flights;
    }


}
