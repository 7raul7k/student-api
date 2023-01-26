package ro.myclass.studentapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private String adress;

}
