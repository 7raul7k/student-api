package ro.myclass.studentapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {


    @Mock
    StudentRepo studentRepo;


    @InjectMocks
    StudentService studentService;


    @Captor
    ArgumentCaptor<Student> argumentCaptor;


    @Test
    public void getAllStudentsOk() {
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


        assertEquals(list, studentService.getAllStudents());

    }

    @Test
    public void getAllStudentsException() {
        doReturn(new ArrayList<>()).when(studentRepo).findAll();
        assertThrows(ListEmptyException.class, () -> {
            studentService.getAllStudents();
        });
    }

    @Test
    public void addStudent() {
        StudentDTO m = StudentDTO.builder().lastName("Ionescu").firstName("Cristian").age(22).adress("Bucuresti").email("ionescucristian@gmail.com").build();
        doReturn(Optional.empty()).when(studentRepo).findStudentByFirstAndLastName("Cristian", "Ionescu");
        Student student = Student.builder().firstName(m.getFirstName())
                .lastName(m.getLastName())
                .adress(m.getAdress())
                .age(m.getAge())
                .email(m.getEmail())
                .build();
        studentService.addStudent(m);

        verify(studentRepo, times(1)).save(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(), student);

    }

    @Test
    public void addStudentException() {
        doReturn(Optional.of(new Student())).when(studentRepo).findStudentByFirstAndLastName("", "");

        assertThrows(StudentWasFoundException.class, () -> {
            this.studentService.addStudent(StudentDTO.builder().firstName("").lastName("").build());
        });
    }

    @Test
    public void removeStudent() {
        Student student = Student.builder().id(1L).firstName("Popa")
                .lastName("Adrian").age(20)
                .email("popaadrian@gmail.com")
                .adress("Iasi")
                .build();
        studentRepo.save(student);

        doReturn(Optional.of(student)).when(studentRepo).findStudentByEmail("popaadrian@gmail.com");

        studentService.deleteStudent("popaadrian@gmail.com");

        verify(studentRepo, times(1)).delete(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(), student);

    }

    @Test
    public void removeStudentException() {
        doReturn(Optional.empty()).when(studentRepo).findStudentByEmail("test@gmail.com");

        assertThrows(StudentNotFoundException.class, () -> {
            this.studentService.deleteStudent("test@gmail.com");
        });
    }

    @Test
    public void findByEmail() {
        Student student = Student.builder().id(1L).firstName("Andrei").lastName("Mihai").age(23).adress("Sibiu").email("andreimihai@gmail.com").build();
        studentRepo.save(student);

        doReturn(Optional.of(student)).when(studentRepo).findStudentByEmail("andreimihai@gmail.com");

        assertEquals(student, this.studentService.findStudentByEmail("andreimihai@gmail.com"));

    }

    @Test
    public void findByEmailException() {
        doReturn(Optional.empty()).when(studentRepo).findStudentByEmail("test@gmail.com");

        assertThrows(StudentNotFoundException.class, () -> {
            this.studentService.findStudentByEmail("test@gmail.com");
        });
    }

    @Test
    public void updateStudent() {
        Student student = Student.builder().firstName("Voinea").lastName("Daniel").age(24).adress("Satu Mare").email("voineadaniel@gmail.com").id(1L).build();
        studentRepo.save(student);

        StudentDTO studentDTO = StudentDTO.builder().email("voineadaniel@gmail.com").age(23).build();

        doReturn(Optional.of(student)).when(studentRepo).findStudentByEmail("voineadaniel@gmail.com");

        studentService.updateStudent(studentDTO);

        verify(studentRepo, times(1)).saveAndFlush(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(), student);
    }

    @Test
    public void updateStudentException() {
        doReturn(Optional.empty()).when(studentRepo).findStudentByEmail("test2@gmail.com");

        assertThrows(StudentNotFoundException.class, () -> {
            this.studentService.updateStudent(StudentDTO.builder().email("test2@gmail.com").build());
        });


    }

    @Test
    public void findStudentByAgelessThan() {
        Student student = Student.builder().id(1L).firstName("Ciuca").lastName("Marian").age(20).adress("Brasov").email("ciucamarian@gmail.com").build();
        Student student1 = Student.builder().id(2L).firstName("Muresan").lastName("Flavius").age(21).adress("Arad").email("muresanflavius@gmail.com").build();
        Student student2 = Student.builder().id(3L).firstName("Radu").lastName("Dragos").age(22).adress("Bucuresti").email("radudragos@gmail.com").build();
        studentRepo.save(student);
        studentRepo.save(student1);
        studentRepo.save(student2);

        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student1);
        list.add(student2);

        doReturn(list).when(studentRepo).findStudentbyAgeLessThan(23);

        assertEquals(list, this.studentService.findStudentByAgelessThan(23));
    }

    @Test
    public void findStudentByAgeLessThanException() {
        doReturn(new ArrayList<>()).when(studentRepo).findStudentbyAgeLessThan(22);

        assertThrows(ListEmptyException.class, () -> {
            this.studentService.findStudentByAgelessThan(22);
        });
    }

    @Test
    public void findStudentByAgeHigherThan() {
        Student student = Student.builder().id(1L).firstName("Pop").lastName("Darius").age(20).adress("Timisoara").email("popdarius@gmail.com").build();
        Student student1 = Student.builder().id(2L).firstName("Antal").lastName("Ionut").age(21).adress("Oradea").email("antalionut@gmail.com").build();
        Student student2 = Student.builder().id(3L).firstName("Matei").lastName("Gabriel").age(22).adress("Botosani").email("mateigabriel@gmail.com").build();
        studentRepo.save(student);
        studentRepo.save(student1);
        studentRepo.save(student2);
        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student1);
        list.add(student2);

        doReturn(list).when(studentRepo).findStudentbyAgeHigherThan(19);

        assertEquals(list, this.studentService.findStudentByAgeHigherthan(19));
    }

    @Test
    public void findStudentByAgeHigherThanException() {
        doReturn(new ArrayList<>()).when(studentRepo).findStudentbyAgeHigherThan(20);

        assertThrows(ListEmptyException.class, () -> {
            this.studentService.findStudentByAgeHigherthan(20);
        });

    }

    @Test
    public void findStudentByAgeBetweenOk() {
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

        doReturn(list).when(studentRepo).findStudentByAgeBetween(19, 23);

        assertEquals(list, this.studentService.findStudentByAgeBetween(19, 23));


    }

    @Test
    public void findStudentByAgeBetweenException() {

        doReturn(new ArrayList<>()).when(studentRepo).findStudentByAgeBetween(18, 22);

        assertThrows(ListEmptyException.class, () -> {
            this.studentService.findStudentByAgeBetween(18, 22);
        });
    }

    @Test
    public void getAllStudentsAdressOk() {
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

        assertEquals(strings, this.studentService.getAllStudentsAdress());

    }

    @Test
    public void getAllStudentsAdressException() {
        doReturn(new ArrayList<String>()).when(studentRepo).getAllStudentAdress();

        assertThrows(ListEmptyException.class, () -> {
            this.studentService.getAllStudentsAdress();
        });

    }
}