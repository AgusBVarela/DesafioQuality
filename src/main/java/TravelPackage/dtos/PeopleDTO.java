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

import java.util.Date;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PeopleDTO {
    private long dni;
    private String name;
    private String lastName;
    @JsonFormat(pattern="dd/MM/yyyy")
    private String birthDate;
    private String mail;
}
