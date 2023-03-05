package ro.myclass.studentapi.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.myclass.studentapi.models.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {

    @Query("Select student from Student student where student.firstName = ?1 AND student.lastName = ?2")
    Optional<Student> findStudentByFirstAndLastName(String firstName,String lastName);

   @Query("Select student from Student student where student.email = ?1")
    Optional<Student> findStudentByEmail(String email);

   @Query("Select student from Student student where student.age < ?1")
    List<Student> findStudentbyAgeLessThan(int age);

   @Query("SELECT student FROM Student student where student.age > ?1")
    List<Student> findStudentbyAgeHigherThan(int age);

   @Query("SELECT student FROM Student student where student.age > ?1 AND student.age < ?2")
    List<Student> findStudentByAgeBetween(int age1,int ag2);

   @Query("SELECT student.adress from Student student")
    List<String> getAllStudentAdress();
}

