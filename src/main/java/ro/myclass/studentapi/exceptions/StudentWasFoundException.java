package ro.myclass.studentapi.exceptions;

public class StudentWasFoundException extends RuntimeException{
    public StudentWasFoundException() {
        super("Student was found!");
    }
}
