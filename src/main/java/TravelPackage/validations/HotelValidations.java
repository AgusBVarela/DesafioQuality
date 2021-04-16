package TravelPackage.validations;

import TravelPackage.exceptions.InvalidInstanceDBException;
import TravelPackage.exceptions.InvalidParamException;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class HotelValidations {

    public static void ValidateParams(Map<String, String> params) throws InvalidParamException {
        if(params != null && params.size() == 0) return;
        String message = "";
        if(params.size() == 3 && params.containsKey("dateFrom")
                && params.containsKey("dateTo") & params.containsKey("destination")) {
            try {
                Date dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(params.get("dateFrom"));
                Date dateTo = new SimpleDateFormat("dd/MM/yyyy").parse(params.get("dateTo"));
            }catch (Exception e){
                throw new InvalidParamException("Las fechas ingresadas no están en el formato esperado 'dd/MM/yyyy'.");
            }
            if(params.get("destination").isEmpty()) throw new InvalidParamException("El destino debe estar especificado.");
        }
    }

    public static void ValidateHotelFromDB(String[] datos) throws InvalidInstanceDBException {
       /*Valida la instancias recibida por parámetro del archivo de DB. En caso de sus valores ser erróneos
        o poseer valores en esas condiciones, lanza una excepción.*/

            int maxParams = 8;
            for(int i = 0; i < maxParams; i++){
                if(datos == null) throw new InvalidInstanceDBException("La DB de hoteles está incompleta");
                if(datos[i] == null) throw new InvalidInstanceDBException("El hotel " + datos[0] + " posee datos vacios en la DB.");
            }

            try {
                double price = Double.valueOf(datos[4].replace("$", "").replace(".", ""));
                Boolean reservated = datos[7].toUpperCase() == "SI" ? true : false;
                Date dateFrom =new SimpleDateFormat("dd/MM/yyyy").parse(datos[5]);
                Date dateTo =new SimpleDateFormat("dd/MM/yyyy").parse(datos[6]);

            } catch (Exception e) {
                throw new InvalidInstanceDBException("El hotel " + datos[0] + " posee datos en un formato inesperado: " + e.getMessage());
            }
        }

}
