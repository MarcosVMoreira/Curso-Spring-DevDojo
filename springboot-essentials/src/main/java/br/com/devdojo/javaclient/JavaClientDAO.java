package br.com.devdojo.javaclient;

import br.com.devdojo.handler.RestResponseExceptionHandler;
import br.com.devdojo.model.PageableResponse;
import br.com.devdojo.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class JavaClientDAO {


    private RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:8080/v1/protected/students").basicAuthentication("maria","devdojo").errorHandler(new RestResponseExceptionHandler()).build();

    private RestTemplate restTemplateAdmin = new RestTemplateBuilder().rootUri("http://localhost:8080/v1/admin/students").basicAuthentication("ze","devdojo").errorHandler(new RestResponseExceptionHandler()).build();


    public Student findById(long id) {
        return restTemplate.getForObject("/{id}", Student.class, id);
    }

    public List<Student> listAll () {
        return restTemplate.exchange("/", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Student>>() {}).getBody().getContent();
    }

    public Student save (Student student) {
        return restTemplateAdmin.exchange("/", HttpMethod.POST, new HttpEntity<>(student, createJSONHeader()), Student.class).getBody();
    }

    public void update (Student student) {
        restTemplateAdmin.put("/", student);
    }

    public void delete (int id) {
        restTemplateAdmin.delete("/{id}", id);
    }


    private static HttpHeaders createJSONHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
