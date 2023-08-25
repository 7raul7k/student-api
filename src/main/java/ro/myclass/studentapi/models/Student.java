package ro.myclass.studentapi.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Table(name = "student_db")
@Entity(name = "Student")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Student implements Comparable<Student>{


    @Id
    @SequenceGenerator(name = "student_sequence",
    sequenceName = "student_sequence",
    allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_sequence"
    )
    @Column(
            name = "id"
    )
    private long id;

    @Column(name = "student_firstName",
    nullable = false,
    columnDefinition = "TEXT")
    @NotEmpty
    private String firstName;
    @Column(name = "student_lastName",
    nullable = false,
    columnDefinition = "TEXT")
    @NotEmpty
    private String lastName;

    @Column(name = "student_age",
    nullable = false,
    columnDefinition = "INT")
    @NotEmpty
    private int age;
    @Column(name = "student_adress",
    nullable = false,
    columnDefinition = "TEXT")
    @NotEmpty
    private String adress;
    @Column(name = "student_email",
    nullable = false,
    columnDefinition = "TEXT")
    @NotEmpty
    private String email;

    public Student(String firstName, String lastName, int age, String adress, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.adress = adress;
        this.email = email;
    }

    @Override
    public String toString(){
        return id+","+firstName+","+lastName+","+","+age+","+adress+","+email;
    }

    @Override
    public int compareTo(Student o) {
        if(o.age > this.age){
            return 1;
        } else if (o.age < this.age) {
            return -1;
        }else{
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj){
        Student m = (Student) obj;
        if(m.firstName.equals(this.firstName)&&m.lastName.equals(this.lastName)&&m.age==this.age&&m.adress.equals(this.adress)&&m.email.equals(this.email)){
            return true;
        }
        return false;
    }
}
