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





}
