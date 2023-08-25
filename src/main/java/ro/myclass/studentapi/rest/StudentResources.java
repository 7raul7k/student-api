package ro.myclass.studentapi.rest;


import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@CrossOrigin
public class StudentResources {

    private StudentService studentService;

    public StudentResources(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents(){


        List<Student> students = this.studentService.getAllStudents();
        log.info("REST requests to get all students {}",students);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<StudentDTO> getAllStudents(@RequestBody StudentDTO student){
        log.info("REST requests to add student {}",student);
         studentService.addStudent(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam String email){
        log.info("REST requests to get students by email {}",email);
        this.studentService.deleteStudent(email);

        return new ResponseEntity<>("Student was deleted",HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDTO studentDTO){
        log.info("REST requests to update student {}",studentDTO);
        this.studentService.updateStudent(studentDTO);
        return new ResponseEntity<>("Student was updated",HttpStatus.ACCEPTED);
    }

    @GetMapping("/ageLessThan/")
    public ResponseEntity<List<Student>> findStudentByAgeLessThan(@RequestParam int age){

        log.info("REST requests to get student by age less than {}",age);
       List<Student> students = this.studentService.findStudentByAgelessThan(age);

       return new ResponseEntity<>(students,HttpStatus.OK);
    }

    @GetMapping("/ageHigherThan/")
    public ResponseEntity<List<Student>> findStudentbyAgeHigherThan(@RequestParam int age){
        log.info("REST requests to get student by age higher than {}",age);
        List<Student> students = this.studentService.findStudentByAgeHigherthan(age);

        return new ResponseEntity<>(students,HttpStatus.OK);
    }

    @GetMapping("/ageBetweenThan/")
    public ResponseEntity<List<Student>> findStudentByAgeBetweenThan(@RequestParam int age1,@RequestParam int age2){
        log.info("REST requests to student between ages {}",age1,age2);
        List<Student> students = this.studentService.findStudentByAgeBetween(age1,age2);

        return new ResponseEntity<>(students,HttpStatus.OK);
    }
    @GetMapping("/allStudentAdress")
    public ResponseEntity<List<String>> getAllStudentAdress(){
        List<String> strings = this.studentService.getAllStudentsAdress();

        log.info("REST requests to get allStudent adress {}",strings);

        return new ResponseEntity<>(strings,HttpStatus.OK) ;
    }

    @GetMapping("/findStudentByEmail")
    public ResponseEntity<Student> findStudentByEmail(@RequestParam String email){

        Student student = this.studentService.findStudentByEmail(email);

        log.info("REST request to get student by email {}",email);

        return new ResponseEntity<>(student,HttpStatus.OK);
    }

    @GetMapping("/getStudentById")
    public ResponseEntity<Student> getStudentById(@RequestParam int id){

        Student student = this.studentService.getStudentById(id);

        log.info("REST request to get student by id {}",id);

        return new ResponseEntity<>(student,HttpStatus.OK);

    }



}
