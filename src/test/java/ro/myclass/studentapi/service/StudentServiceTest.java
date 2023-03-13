package ro.myclass.studentapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.myclass.studentapi.dto.StudentDTO;
import ro.myclass.studentapi.exceptions.ListEmptyException;
import ro.myclass.studentapi.exceptions.StudentNotFoundException;
import ro.myclass.studentapi.exceptions.StudentWasFoundException;
import ro.myclass.studentapi.models.Student;
import ro.myclass.studentapi.repo.StudentRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {


    @Mock
    StudentRepo studentRepo;


    @InjectMocks
    StudentService studentService;


    @Test
    public void getAllStudentsOk(){
        Student student = Student.builder().firstName("Popescu").lastName("Ion").age(22).adress("Bucuresti").email("popescuion@gmail.com").id(1L).build();
        Student student1 = Student.builder().firstName("Ionescu").lastName("Razvan").age(21).adress("Iasi").email("ionescurazvan@gmail.com").id(2L).build();
        Student student2 = Student.builder().firstName("Antonescu").lastName("Robert").age(23).adress("Arad").email("antonescurobert@gmail.com").id(3L).build();
        studentRepo.save(student);
        studentRepo.save(student1);
        studentRepo.save(student2);
        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student1);
        list.add(student2);

        doReturn(list).when(studentRepo).findAll();


        assertEquals(list,studentService.getAllStudents());

    }

    @Test
    public void getAllStudentsException(){
        doReturn(new ArrayList<>()).when(studentRepo).findAll();
        assertThrows(ListEmptyException.class,()->{
            studentService.getAllStudents();
        });
    }

    @Test
    public void addStudentOk(){
        StudentDTO student1 = StudentDTO.builder().firstName("Dinu").lastName("Ciprian").age(23).adress("Timisoara").email("dinuciprian@gmail.com").build();



        doReturn(Optional.empty()).when(studentRepo).findStudentByFirstAndLastName(student1.getFirstName(),student1.getLastName());

        assertEquals(student1,this.studentService.addStudent(student1));


    }

    @Test
    public void addStudentException(){


        doReturn(Optional.of(new Student())).when(studentRepo).findStudentByFirstAndLastName(new StudentDTO().getFirstName(),new StudentDTO().getLastName());

        assertThrows(StudentNotFoundException.class,()->{
         this.studentService.addStudent(new StudentDTO());
        });
    }

    @Test
    public void removeStudentOk(){
        Student student= Student.builder().firstName("Marcu").lastName("Ianis").adress("Sibiu").age(23).email("marcuianis@gmail.com").id(1L).build();
       studentRepo.save(student);


       doReturn(Optional.of(student)).when(studentRepo).findStudentByEmail("marcuianis@gmail.com");

       assertEquals(true,this.studentService.deleteStudent("marcuianis@gmail.com"));

    }

    @Test
    public void removeStudentException(){

        doReturn(Optional.empty()).when(studentRepo).findStudentByEmail("alin");

        assertThrows(StudentNotFoundException.class,()->{

            this.studentService.deleteStudent("alin");
        });
    }

    @Test
    public void findStudentbyEmailOk(){
        Student student = Student.builder().id(1L).firstName("Tudor").lastName("Marian").age(22).email("tudormarian@gmail.com").adress("Craiova").build();
        studentRepo.save(student);

        doReturn(Optional.of(student)).when(studentRepo).findStudentByEmail("tudormarian@gmail.com");

        assertEquals(true,this.studentService.findStudentByEmail("tudormarian@gmail.com"));
    }

    @Test
    public void findStudentByEmailException(){

        doReturn(Optional.empty()).when(studentRepo).findStudentByEmail("");

        assertThrows(StudentNotFoundException.class,()->{
            this.studentService.findStudentByEmail("");
        });
    }

    @Test
    public void updateStudentOk(){
        Student student = Student.builder().id(1L).firstName("Pop").lastName("Darius").email("popdarius@gmail.com").age(21).adress("Satu Mare").build();

        studentRepo.save(student);
        StudentDTO studentDTO = StudentDTO.builder().email("popdarius@gmail.com").age(22).lastName("Darius Sebastian").build();



        doReturn(Optional.of(student)).when(studentRepo).findStudentByEmail("popdarius@gmail.com");

        assertEquals(true,studentService.updateStudent(studentDTO));
    }

    @Test
    public void updateStudentException(){

        doReturn(Optional.empty()).when(studentRepo).findStudentByEmail("adrian@gmail.com");
        assertThrows(StudentNotFoundException.class,()->{
            this.studentService.updateStudent(StudentDTO.builder().email("adrian@gmail.com").build());
        });
    }

    @Test
    public void findStudentByAgelessThanOk(){
        Student student = Student.builder().firstName("Rusu").lastName("Constantin").age(23).build();
        Student student1 = Student.builder().firstName("Popa").lastName("Marcel").age(21).build();
        Student student2 = Student.builder().firstName("Lazar").lastName("Matei").age(24).build();
        Student student3 = Student.builder().firstName("Moldovan").lastName("Vasile").age(20).build();
        studentRepo.save(student);
        studentRepo.save(student1);
        studentRepo.save(student2);
        studentRepo.save(student3);

        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student1);
        list.add(student2);

        doReturn(list).when(studentRepo).findStudentbyAgeLessThan(24);

        assertEquals(list,this.studentService.findStudentByAgelessThan(24));
    }

    @Test
    public void findStudentByAgeLessThanException(){

        doReturn(new ArrayList<>()).when(studentRepo).findStudentbyAgeLessThan(23);

        assertThrows(ListEmptyException.class,()->{
            this.studentService.findStudentByAgelessThan(23);
        });
    }

    @Test
    public void findStudentByAgeHigherThanOk(){
        Student student = Student.builder().firstName("Mihai").lastName("Iosif").age(21).build();
        Student student1 = Student.builder().firstName("Florea").lastName("Andrei").age(20).build();
        Student student2 = Student.builder().firstName("Ilie").lastName("Alexandru").age(22).build();

        studentRepo.save(student);
        studentRepo.save(student1);
        studentRepo.save(student2);

        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student1);
        list.add(student2);

        doReturn(list).when(studentRepo).findStudentbyAgeHigherThan(19);
        assertEquals(list,this.studentService.findStudentByAgeHigherthan(19));
    }

    @Test
    public void findStudentbyAgeHigherThanException(){

        doReturn(new ArrayList<>()).when(studentRepo).findStudentbyAgeHigherThan(20);

        assertThrows(ListEmptyException.class,()->{
            this.studentService.findStudentByAgeHigherthan(20);
        });
    }

    @Test
    public void findStudentByAgeBetweenOk(){
        Student student = Student.builder().firstName("Stanciu").lastName("Gabriel").age(20).build();
        Student student1 = Student.builder().firstName("Marin").lastName("Cristian").age(21).build();
        Student student2 = Student.builder().firstName("Stefan").lastName("Andrei").age(22).build();

        studentRepo.save(student);
        studentRepo.save(student1);
        studentRepo.save(student2);

        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student1);
        list.add(student2);

        doReturn(list).when(studentRepo).findStudentByAgeBetween(19,23);

        assertEquals(list,this.studentService.findStudentByAgeBetween(19,23));


    }

    @Test
    public void findStudentByAgeBetweenException(){

        doReturn(new ArrayList<>()).when(studentRepo).findStudentByAgeBetween(18,22);

        assertThrows(ListEmptyException.class,()->{
            this.studentService.findStudentByAgeBetween(18,22);
        });
    }

    @Test
    public void getAllStudentsAdressOk(){
        Student student = Student.builder().adress("Botosani").build();
        Student student1 = Student.builder().adress("Ramnicul Valcea").build();
        Student student2 = Student.builder().adress("Sibiu").build();
        Student student3 = Student.builder().adress("Brasov").build();

        List<String> strings = new ArrayList<>();
        strings.add(student.getAdress());
        strings.add(student1.getAdress());
        strings.add(student2.getAdress());
        strings.add(student3.getAdress());

        doReturn(strings).when(studentRepo).getAllStudentAdress();

        assertEquals(strings,this.studentService.getAllStudentsAdress());

    }

    @Test
    public void getAllStudentsAdressException(){
        doReturn(new ArrayList<String>()).when(studentRepo).getAllStudentAdress();

        assertThrows(ListEmptyException.class,()->{
            this.studentService.getAllStudentsAdress();
        });
    }









}