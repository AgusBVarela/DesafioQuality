package TravelPackage.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelDTO {
    private String hotelCode;
    private String name;
    private String destination;
    private String roomType;
    private Integer peopleAmount = null;
    private Double nightPrice = null;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate dateFrom;
    @JsonFormat(pattern="dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateTo;
    private Boolean reserved = null;
    private List<PeopleDTO> people = null;
    private PaymentMethodDTO paymentMethod = null;

    public HotelDTO(LocalDate dateFrom, LocalDate dateTo, String destination, String hotelCode, int peopleAmount, String roomType, List<PeopleDTO> people, PaymentMethodDTO paymentMethod){
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.destination = destination;
        this.hotelCode = hotelCode;
        this.peopleAmount = peopleAmount;
        this.roomType = roomType;
        this.people = people;
        this.paymentMethod = paymentMethod;
    }

    public HotelDTO(String[] data) throws Exception {
        /*Genera una instancia de DTO completándola con los datos recibidos por parámetro según correspodna.*/

        this.hotelCode = data[0];
        this.name = data[1];
        this.destination = data[2];
        this.roomType = data[3];
        this.nightPrice = Double.valueOf(data[4].replace("$", "").replace(".", ""));;
        this.dateTo = LocalDate.parse(data[6], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.dateFrom = LocalDate.parse(data[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        //this.dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(data[5]);;
        //this.dateTo = new SimpleDateFormat("dd/MM/yyyy").parse(data[6]);;
        this.reserved = data[7].toUpperCase().equals("SI")  ? true : false;
    }


}
