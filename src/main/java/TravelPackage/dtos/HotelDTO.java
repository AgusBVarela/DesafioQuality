package TravelPackage.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {
    private String codeHotel;
    private String name;
    private String city;
    private String roomType;
    private Double nightPrice = null;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dateFrom;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dateTo;
    private boolean reserved;

    public HotelDTO(String[] data) throws Exception {
        /*Genera una instancia de DTO completándola con los datos recibidos por parámetro según correspodna.*/

        this.codeHotel = data[0];
        this.name = data[1];
        this.city = data[2];
        this.roomType = data[3];
        this.nightPrice = Double.valueOf(data[4].replace("$", "").replace(".", ""));;
        this.dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(data[5]);;
        this.dateTo = new SimpleDateFormat("dd/MM/yyyy").parse(data[6]);;
        this.reserved = data[7].toUpperCase().equals("SI")  ? true : false;
        int i = 0;
    }


}
