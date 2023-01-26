package ro.myclass.studentapi.exceptions;

public class StudentExistException extends RuntimeException{
    public StudentExistException() {
        super("Student was found!");
    }
}
