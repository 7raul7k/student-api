package ro.myclass.studentapi.exceptions;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException() {
        super("Student not found !");
    }
}
