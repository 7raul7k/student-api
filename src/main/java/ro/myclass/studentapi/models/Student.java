package ro.myclass.studentapi.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@Table(name = "student_db")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Student implements Comparable<Student>{

    @GeneratedValue(strategy = GenerationType.AUTO)

    @Id

    private long id;
    private String firstName;
    private String lastName;
    private int age;
    private String adress;
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
