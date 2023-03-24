package ro.myclass.studentapi.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.myclass.studentapi.dto.StudentDTO;
import ro.myclass.studentapi.exceptions.ListEmptyException;
import ro.myclass.studentapi.exceptions.StudentNotFoundException;
import ro.myclass.studentapi.exceptions.StudentWasFoundException;
import ro.myclass.studentapi.models.Student;
import ro.myclass.studentapi.repo.StudentRepo;
import ro.myclass.studentapi.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StudentResourcesTest {
    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentResources studentResources;

    private ObjectMapper mapper = new ObjectMapper();

    private MockMvc restMockMvc;

    @BeforeEach
    void initialConfig(){
        restMockMvc = MockMvcBuilders.standaloneSetup(studentResources).build();
    }

    @Test
    public void getAllStudents() throws Exception {
        Faker faker = new Faker();
        List<Student> studentList = new ArrayList<>();

        for(int i = 0 ; i < 10;i++){
            studentList.add(new Student(faker.lorem().sentence(),faker.lorem().sentence(),faker.number().numberBetween(19,25),faker.lorem().sentence(),faker.lorem().sentence()));
        }

        doReturn(studentList).when(studentService).getAllStudents();

        restMockMvc.perform(get("/api/v1/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(studentList)));
    }

    @Test
    public void getAllStudentsBadStatus() throws Exception {
        doThrow(ListEmptyException.class).when(studentService).getAllStudents();

        restMockMvc.perform(get("/api/v1/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void addStudent() throws Exception {
        StudentDTO studentDTO = new StudentDTO("andreipopescu@gmail.com","Popescu","Andrei",22,"Bucuresti");
        doNothing().when(studentService).addStudent(studentDTO);
        restMockMvc.perform(post("/api/v1/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(studentDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void addStudentBadStatus() throws Exception {
        doThrow(StudentWasFoundException.class).when(studentService).addStudent(new StudentDTO());
        restMockMvc.perform(post("/api/v1/add")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(new StudentDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteStudent() throws Exception {
      StudentDTO studentDTO = StudentDTO.builder().email("popaadrian@gmail.com").firstName("Popa").lastName("Adrian").age(22).build();

      Student student = Student.builder().id(1).adress(studentDTO.getAdress()).age(studentDTO.getAge()).firstName(studentDTO.getFirstName()).lastName(studentDTO.getLastName()).email(studentDTO.getEmail()).build();

        doNothing().when(studentService).deleteStudent("popaadrian@gmail.com");

        restMockMvc.perform(delete("/api/v1/delete?email=popaadrian@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void deleteStudentBadRequest() throws Exception {
        doThrow(StudentNotFoundException.class).when(studentService).deleteStudent("test@test.com");

        restMockMvc.perform(delete("/api/v1/delete?=email=test@test.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateStudent() throws Exception {
        StudentDTO studentDTO = StudentDTO.builder().age(23).firstName("Andrei").lastName("Vlad").email("andreivlad@gmail.com").adress("Iasi").build();

        doNothing().when(studentService).updateStudent(studentDTO);

        restMockMvc.perform(put("/api/v1/update")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(studentDTO)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void updateStudentBadRequest() throws Exception{

        doThrow(StudentNotFoundException.class).when(studentService).updateStudent(new StudentDTO());

        restMockMvc.perform(put("/api/v1/update")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(new StudentDTO())))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void findStudentByAgeLessThan() throws Exception{
        Faker faker = new Faker();
        List<Student> students = new ArrayList<>();

        for(int i = 0 ; i < 10;i++){
            students.add(new Student(faker.lorem().sentence(),faker.lorem().sentence(),faker.number().numberBetween(18,22),faker.lorem().sentence(),faker.lorem().sentence()));
        }
        doReturn(students).when(studentService).findStudentByAgelessThan(23);

        restMockMvc.perform(get("/api/v1/ageLessThan/?age=23")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(students)))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentByAgeLessThanBadRequest() throws Exception{

        doThrow(ListEmptyException.class).when(studentService).findStudentByAgelessThan(22);

        restMockMvc.perform(get("/api/v1/ageLessThan/?age=22")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(new ArrayList<Student>())))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void getStudentByAgeHigherThan() throws Exception{
        Faker faker = new Faker();
        List<Student> studentList = new ArrayList<>();
        for(int i = 0 ; i < 10;i++){
            studentList.add(new Student(faker.lorem().sentence(),faker.lorem().sentence(),faker.number().numberBetween(20,24),faker.lorem().sentence(),faker.lorem().sentence()));
        }
        doReturn(studentList).when(studentService).findStudentByAgeHigherthan(19);

        restMockMvc.perform(get("/api/v1/ageHigherThan/?age=19")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(studentList)));
    }

    @Test
    public void getStudentByAgeHigherThanBadRequest() throws Exception{
        doThrow(ListEmptyException.class).when(studentService).findStudentByAgeHigherthan(20);

        restMockMvc.perform(get("/api/v1/ageHigherThan/?age=20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getStudentByAgeBetweenThan() throws Exception{
        Faker faker = new Faker();
        List<Student> studentList = new ArrayList<>();
        for(int i = 0 ; i < 10;i++){
            studentList.add(new Student(faker.lorem().sentence(),faker.lorem().sentence(),faker.number().numberBetween(21,24),faker.lorem().sentence(),faker.lorem().sentence()));
        }
        doReturn(studentList).when(studentService).findStudentByAgeBetween(21,24);

        restMockMvc.perform(get("/api/v1/ageBetweenThan/?age1=21&age2=24")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(studentList)));
    }

    @Test
    public void getStudentByAgeBetweenThanBadRequest() throws Exception{
        doThrow(ListEmptyException.class).when(studentService).findStudentByAgeBetween(24,30);
        restMockMvc.perform(get("/api/v1/ageBetweenThan/?age1=24&age2=30")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void getAllStudentAdress() throws Exception{
        Faker faker = new Faker();
        List<String> studentAdress = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            studentAdress.add(faker.lorem().sentence());
        }
        doReturn(studentAdress).when(studentService).getAllStudentsAdress();

        restMockMvc.perform(get("/api/v1/allStudentAdress").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(studentAdress)));
    }

}

