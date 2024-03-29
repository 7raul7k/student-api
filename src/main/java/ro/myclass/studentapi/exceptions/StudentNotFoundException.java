package ro.myclass.studentapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException() {
        super("Student not found !");
    }
}
