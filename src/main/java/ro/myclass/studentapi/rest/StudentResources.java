package ro.myclass.studentapi.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.myclass.studentapi.dto.StudentDTO;
import ro.myclass.studentapi.models.Student;
import ro.myclass.studentapi.repo.StudentRepo;
import ro.myclass.studentapi.service.StudentService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StudentResources {

    private StudentService studentService;

    public StudentResources(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> students = this.studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping("/add")

    public ResponseEntity<Student> getAllStudents(@RequestBody Student student){

         studentService.addStudent(student);
        return new ResponseEntity<>(student, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam String email){
        this.studentService.findStudentByEmail(email);

        return new ResponseEntity<>("Student was deleted",HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDTO studentDTO){
        this.studentService.updateStudent(studentDTO);
        return new ResponseEntity<>("Student was updated",HttpStatus.ACCEPTED);
    }

    @GetMapping("/ageLessThan/{age}")
    public ResponseEntity<List<Student>> findStudentByAgeLessThan(@PathVariable int age){
       List<Student> students = this.studentService.findStudentByAgelessThan(age);

       return new ResponseEntity<>(students,HttpStatus.OK);
    }

    @GetMapping("/ageHigherThan/{age}")
    public ResponseEntity<List<Student>> findStudentbyAgeHigherThan(@PathVariable int age){
        List<Student> students = this.studentService.findStudentByAgeHigherthan(age);

        return new ResponseEntity<>(students,HttpStatus.OK);
    }

    @GetMapping("/ageBetweenThan/{age1}&{age2}")
    public ResponseEntity<List<Student>> findStudentByAgeBetweenThan(@PathVariable int age1,@PathVariable int age2){
        List<Student> students = this.studentService.findStudentByAgeBetween(age1,age2);

        return new ResponseEntity<>(students,HttpStatus.OK);
    }
    @GetMapping("/allStudentAdress")
    public ResponseEntity<List<String>> getAllStudentAdress(){
        List<String> strings = this.studentService.getAllStudentsAdress();

        return new ResponseEntity<>(strings,HttpStatus.OK) ;
    }







}
