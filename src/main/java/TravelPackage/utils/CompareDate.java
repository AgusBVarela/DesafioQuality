package TravelPackage.utils;

import java.time.LocalDate;

public class CompareDate {

    public static boolean AfterTo(LocalDate date1, LocalDate date2){
        /*Encargada de comparar si la primer fecha es posterior a la segunda.
        Devuelve un booleano representando el resultado.*/
        return date2.compareTo(date1) < 0 ? true : false;
    }
    public static boolean BeforeTo(LocalDate date1, LocalDate date2){
        /*Encargada de comparar si la primer fecha es posterior a la segunda.
        Devuelve un booleano representando el resultado.*/
        return !AfterTo(date1, date2);
    }
}
