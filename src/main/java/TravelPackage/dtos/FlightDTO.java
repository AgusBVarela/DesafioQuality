package TravelPackage.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightDTO{
    private String flightNumber = null;
    private String origin = null;
    private String destination = null;
    private String seatType = null;
    private Integer seats = null;
    private Double price = null;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate dateFrom;
    @JsonFormat(pattern="dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateTo;
    private List<PeopleDTO> people = null;
    private PaymentMethodDTO paymentMethod = null;

    public FlightDTO(String[] data) throws Exception {
        /*Genera una instancia de DTO completándola con los datos recibidos por parámetro según correspodna.*/

        this.flightNumber = data[0];
        this.origin = data[1];
        this.destination = data[2];
        this.seatType = data[3];
        this.price = Double.valueOf(data[4].replace("$", "").replace(".", ""));;
        this.dateFrom = LocalDate.parse(data[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.dateTo = LocalDate.parse(data[6], DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    }

}
