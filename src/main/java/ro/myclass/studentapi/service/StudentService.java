package ro.myclass.studentapi.service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import ro.myclass.studentapi.dto.StudentDTO;
import ro.myclass.studentapi.exceptions.ListEmptyException;
import ro.myclass.studentapi.exceptions.StudentWasFoundException;
import ro.myclass.studentapi.exceptions.StudentNotFoundException;
import ro.myclass.studentapi.models.Student;
import ro.myclass.studentapi.repo.StudentRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public List<Student> getAllStudents() {

        List<Student> students = this.studentRepo.findAll();

        if (students.isEmpty()) {
            throw new ListEmptyException();
        } else {
            return students;
        }
    }

    @Transactional
    @Modifying
    public StudentDTO addStudent(StudentDTO m) {
        Optional<Student> student = this.studentRepo.findStudentByFirstAndLastName(m.getFirstName(), m.getLastName());
        if (student.isEmpty() == false) {
            throw new StudentNotFoundException();
        }else{
            Student student1 = Student.builder().firstName(m.getFirstName())
                    .lastName(m.getLastName())
                    .adress(m.getAdress())
                    .age(m.getAge())
                    .build();

            studentRepo.save(student1);

            return m;


        }

    }

    public boolean deleteStudent(String email) {
        Optional<Student> student = this.studentRepo.findStudentByEmail(email);

        if (student.isEmpty() ) {
            throw new StudentNotFoundException();
        }else{
            this.studentRepo.delete(student.get());

            return true;
        }

    }

    @Transactional
    public boolean findStudentByEmail(String email) {
        Optional<Student> student = this.studentRepo.findStudentByEmail(email);

        if (student.isEmpty()) {
            throw new StudentNotFoundException();
        } else {
            this.studentRepo.delete(student.get());

            return true;
        }
    }


    @Transactional
    public boolean updateStudent(StudentDTO studentDTO) {


        Optional<Student> student = this.studentRepo.findStudentByEmail(studentDTO.getEmail());
        if (student.isEmpty()) {
            throw new StudentNotFoundException();
        } else {

           if(studentDTO.getLastName()!=null) {

               student.get().setLastName(studentDTO.getLastName());
           } else if (studentDTO.getFirstName()!= null) {
               student.get().setFirstName(studentDTO.getFirstName());
           } else if (studentDTO.getAge() < 0 ){
               student.get().setAge(studentDTO.getAge());
            }else if (studentDTO.getAdress()!= null){
               student.get().setAdress(studentDTO.getAdress());
           }
            this.studentRepo.saveAndFlush(student.get());
           return true;
        }
    }

    @Transactional
    public List<Student> findStudentByAgelessThan(int age) {

        if (age > 0) {
            List<Student> students = this.studentRepo.findStudentbyAgeLessThan(age);
            if (students.isEmpty()== false) {
                return students;
            }
        }
          throw new ListEmptyException();

    }

    @Transactional
    public List<Student> findStudentByAgeHigherthan(int age){
        if(age > 0){
            List<Student> students = this.studentRepo.findStudentbyAgeHigherThan(age);
            if(students.isEmpty()==false){
                return students;
            }
        }
        throw new ListEmptyException();

    }

    @Transactional
    public List<Student> findStudentByAgeBetween(int age1,int age2){
        if(age1 < age2 || age2 > 0 || age1 > 0){
            List<Student> students = this.studentRepo.findStudentByAgeBetween(age1,age2);
            if(students.isEmpty()==false){
                return students;
            }
        }
        throw new ListEmptyException();
    }

    public List<String> getAllStudentsAdress(){
        List<String> strings = this.studentRepo.getAllStudentAdress();

        if(strings.isEmpty()== false){
            return strings;
        }
        throw new ListEmptyException();
    }

}
