package ro.myclass.studentapi.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ro.myclass.studentapi.StudentApiApplication;
import ro.myclass.studentapi.models.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StudentApiApplication.class)
@Transactional
class StudentRepoTest {

    @Autowired
    StudentRepo studentRepository;

    @BeforeEach
    public void clean() {
        studentRepository.deleteAll();
    }

    @Test
    public void findStudentByEmail() {
        //Preconditie
        Student student = new Student("Andrei", "Popescu", 17, "Iasi", "andreipopescu@gmail.com");
        Student student1 = new Student("Adrian", "Iosif", 24, "Suceava", "adrianiosif@gmail.com");
        Student student2 = new Student("Cristian", "Florin", 22, "Bucuresti", "cristianflorin@gmail.com");
        Student student3 = new Student("George", "Razvan", 23, "Constanta", "georgerazvan@gmail.com");
        Student student4 = new Student("Denis", "Anton", 20, "Galati", "antondenis@gmail.com");
        studentRepository.save(student);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);

        //actiune si rezultat


        Student student5 = studentRepository.findStudentByEmail("antondenis@gmail.com").get();
        assertEquals(student4, student5);
    }

    @Test
    public void findByFirstNameAndName(){
        Student student = new Student("Andrei", "Popescu", 17, "Iasi", "andreipopescu@gmail.com");
        Student student1 = new Student("Adrian", "Iosif", 24, "Suceava", "adrianiosif@gmail.com");
        Student student2 = new Student("Cristian", "Florin", 22, "Bucuresti", "cristianflorin@gmail.com");
        Student student3 = new Student("George", "Razvan", 23, "Constanta", "georgerazvan@gmail.com");
        Student student4 = new Student("Denis", "Anton", 20, "Galati", "antondenis@gmail.com");
        studentRepository.save(student);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);

        Student student5 = this.studentRepository.findStudentByFirstAndLastName("George","Razvan").get();

        assertEquals(student3,student5);
    }

    @Test
    public void findStudentbyAgeHigherThan(){
        Student student = new Student("Andrei", "Popescu", 17, "Iasi", "andreipopescu@gmail.com");
        Student student1 = new Student("Adrian", "Iosif", 24, "Suceava", "adrianiosif@gmail.com");
        Student student2 = new Student("Cristian", "Florin", 22, "Bucuresti", "cristianflorin@gmail.com");
        Student student3 = new Student("George", "Razvan", 23, "Constanta", "georgerazvan@gmail.com");
        Student student4 = new Student("Denis", "Anton", 20, "Galati", "antondenis@gmail.com");
        studentRepository.save(student);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);


        assertEquals(students,this.studentRepository.findStudentbyAgeHigherThan(20));
    }

    @Test
    public void findStudentByAgeBetween(){
        Student student = new Student("Andrei", "Popescu", 17, "Iasi", "andreipopescu@gmail.com");
        Student student1 = new Student("Adrian", "Iosif", 24, "Suceava", "adrianiosif@gmail.com");
        Student student2 = new Student("Cristian", "Florin", 22, "Bucuresti", "cristianflorin@gmail.com");
        Student student3 = new Student("George", "Razvan", 23, "Constanta", "georgerazvan@gmail.com");
        Student student4 = new Student("Denis", "Anton", 20, "Galati", "antondenis@gmail.com");
        studentRepository.save(student);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);

        List<Student> students = this.studentRepository.findStudentByAgeBetween(18,25);

        assertEquals(students,this.studentRepository.findStudentByAgeBetween(18,25));

    }

    @Test
    public void getAllStudentAdress(){
        Student student = new Student("Andrei", "Popescu", 17, "Iasi", "andreipopescu@gmail.com");
        Student student1 = new Student("Adrian", "Iosif", 24, "Suceava", "adrianiosif@gmail.com");
        Student student2 = new Student("Cristian", "Florin", 22, "Bucuresti", "cristianflorin@gmail.com");
        Student student3 = new Student("George", "Razvan", 23, "Constanta", "georgerazvan@gmail.com");
        Student student4 = new Student("Denis", "Anton", 20, "Galati", "antondenis@gmail.com");
        studentRepository.save(student);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);

        List<String> strings = this.studentRepository.getAllStudentAdress();

        assertEquals(strings,this.studentRepository.getAllStudentAdress());
    }
}

