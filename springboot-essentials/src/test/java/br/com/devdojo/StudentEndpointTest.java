package br.com.devdojo;

import br.com.devdojo.endpoint.StudentEndpoint;
import br.com.devdojo.model.Student;
import br.com.devdojo.model.User;
import br.com.devdojo.repository.StudentRepository;
import br.com.devdojo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentEndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private UserRepository userRepository;

    @TestConfiguration
    static class Config {
        @Bean
        public RestTemplateBuilder restTemplateBuilder () {
            return new RestTemplateBuilder().basicAuthentication("ze", "devdojo");
        }
    }

    @Test
    public void whenListStudentUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized () {
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/v1/protected/students/", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void whenGetStudentByIdUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized () {
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/v1/protected/students/1", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void whenFindStudentByNameUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized () {
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/v1/protected/students/findByName/studentName", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void whenSaveStudentUsingIncorrectUsernameAndPassword_thenReturnResourceAccessException () {
        restTemplate = restTemplate.withBasicAuth("1", "1");

        Student student = new Student(1L, "Legolas", "legolas@lotr.com");

        assertThrows(
                ResourceAccessException.class,
                () -> restTemplate.postForEntity("http://localhost:8080/v1/admin/students/", student, String.class));
    }

    @Test
    public void whenDeleteStudentUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized () {
        restTemplate = restTemplate.withBasicAuth("1", "1");

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/{id}", DELETE, null, String.class, 1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void whenUpdateStudentUsingIncorrectUsernameAndPassword_thenReturnResourceAccessException () {
        restTemplate = restTemplate.withBasicAuth("1", "1");

        Student student = new Student(1L, "Legolas", "legolas@lotr.com");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Student> entity = new HttpEntity<Student>(student, headers);

        assertThrows(
                ResourceAccessException.class,
                () -> restTemplate.exchange("http://localhost:8080/v1/admin/students", PUT, entity, String.class, 1L));
    }


    @Test
    @WithMockUser(username = "xxx", password = "xxx", roles = {"USER"})
    public void whenListAllStudentsUsingCorrectRole_thenReturnStatusCode200 () throws Exception {
        List<Student> students = asList(new Student(1L, "Legolas", "legolas@lotr.com"),
                new Student(2L, "Aragorn", "aragorn@lotr.com"));

        Page<Student> pagedStudents = new PageImpl(students);

        when(studentRepository.findAll(isA(Pageable.class))).thenReturn(pagedStudents);

        mockMvc.perform(get("http://localhost:8080/v1/protected/students/"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(studentRepository).findAll(isA(Pageable.class));
    }

    

    @Test
    @WithMockUser(username = "xx", password = "xx", roles = "USER")
    public void whenListAllStudentsUsingCorrectRole_thenReturnCorrectData () throws Exception {
        List<Student> students = asList(new Student(1L, "Legolas", "legolas@lotr.com"),
                new Student(2L, "Aragorn", "aragorn@lotr.com"));

        Page<Student> pagedStudents = new PageImpl(students);

        when(studentRepository.findAll(isA(Pageable.class))).thenReturn(pagedStudents);

        mockMvc.perform(get("http://localhost:8080/v1/protected/students/"))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].name").value("Legolas"))
                .andExpect(jsonPath("$.content[0].email").value("legolas@lotr.com"))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].name").value("Aragorn"))
                .andExpect(jsonPath("$.content[1].email").value("aragorn@lotr.com"));

        verify(studentRepository).findAll(isA(Pageable.class));
    }

}
