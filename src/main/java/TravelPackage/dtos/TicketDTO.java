package TravelPackage.dtos;

import TravelPackage.repositories.HotelRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDTO {

    private String userName;
    private Double amount =null;
    private Double interest = null;
    private Double total=null;
    private HotelDTO booking = null;

    public TicketDTO(String userName, HotelDTO booking){
        this.userName = userName;
        this.booking = booking;
    }

    public TicketDTO(String userName){
        this.userName = userName;
    }

}
