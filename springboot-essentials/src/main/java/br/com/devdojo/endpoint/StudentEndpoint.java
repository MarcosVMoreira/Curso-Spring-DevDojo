package br.com.devdojo.endpoint;

import br.com.devdojo.Repository.StudentRepository;
import br.com.devdojo.error.CustomErrorType;
import br.com.devdojo.error.ResourceNotFoundException;
import br.com.devdojo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//RestController, em comparacao com Controller, adiciona automaticamente a notacao ResponseBody para transformar para JSON
@RestController
@RequestMapping("students")
public class StudentEndpoint {
    private final StudentRepository studentDAO;

    @Autowired
    public StudentEndpoint (StudentRepository studentDAO) {
        this.studentDAO = studentDAO;
    }

    @GetMapping
    public ResponseEntity<?> listAll () {
        return new ResponseEntity<>(studentDAO.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStudentById (@PathVariable("id") Long id) {
        verifyIfStudentExists(id);
        Optional<Student> student = studentDAO.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "/findByName/{name}")
    public ResponseEntity<?> findStudentsByName (@PathVariable String name) {
        return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save (@RequestBody Student student) {
        studentDAO.save(student);
        if (true) {
            throw new RuntimeException("test transaction");
        }
        return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        verifyIfStudentExists(id);
        studentDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update (@RequestBody Student student) {
        verifyIfStudentExists(student.getId());
        studentDAO.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private void verifyIfStudentExists(Long id) {

        if (studentDAO.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Student not found for ID: "+id);
        }
    }


}

