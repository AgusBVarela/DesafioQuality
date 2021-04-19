package TravelPackage.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PeopleDTO {
    private long dni;
    private String name;
    private String lastName;
    @JsonFormat(pattern="dd/MM/yyyy")
    private String birthDate;
    private String mail;
}
