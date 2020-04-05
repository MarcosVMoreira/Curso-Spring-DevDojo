package br.com.devdojo.javaclient;

import br.com.devdojo.model.PageableResponse;
import br.com.devdojo.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class JavaSpringClientTest {

    public static void main (String[] args) {

        RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:8080/v1/protected/students").basicAuthentication("maria","devdojo").build();

        RestTemplate restTemplateAdmin = new RestTemplateBuilder().rootUri("http://localhost:8080/v1/admin/students").basicAuthentication("ze","devdojo").build();

        //usando getForObject
        Student student = restTemplate.getForObject("/{id}", Student.class, 13);
        System.out.println(student);

        //usando getForEntity
        ResponseEntity<Student> responseEntity = restTemplate.getForEntity("/{id}", Student.class, 13);
        System.out.println(responseEntity);
         System.out.println(responseEntity.getBody());

//        Student[] students = restTemplate.getForObject("/", Student[].class);
//        System.out.println(Arrays.toString(students));


        //para trabalhar SEM pageable no endpoint
//        ResponseEntity<List<Student>> exchange = restTemplate.exchange("/", HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {});
//        System.out.println(exchange);

        //para trabalhar COM pageable no endpoint
        ResponseEntity<PageableResponse<Student>> exchange = restTemplate.exchange("/?sort=id,desc&sort=name,asc", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Student>>() {});
        System.out.println(exchange);

        System.out.println("Sending Jhon Wick POST...");
        Student studentPost = new Student();
        studentPost.setName("John Wick");
        studentPost.setEmail("john.wick@gmail.com");
        ResponseEntity<Student> exchangePost = restTemplateAdmin.exchange("/", HttpMethod.POST, new HttpEntity<>(studentPost, createJSONHeader()), Student.class);
        System.out.println(exchangePost);
        Student studentPostForObject = restTemplateAdmin.postForObject("/", studentPost, Student.class);
        System.out.println(studentPostForObject);
        ResponseEntity<Student> studentResponseEntity = restTemplateAdmin.postForEntity("/", studentPost, Student.class);
        System.out.println(studentResponseEntity);



    }

    private static HttpHeaders createJSONHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
