package br.com.devdojo.endpoint;

import br.com.devdojo.repository.StudentRepository;
import br.com.devdojo.error.ResourceNotFoundException;
import br.com.devdojo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

//RestController, em comparacao com Controller, adiciona automaticamente a notacao ResponseBody para transformar para JSON
@RestController
@RequestMapping("v1")
public class StudentEndpoint {
    private final StudentRepository studentDAO;

    @Autowired
    public StudentEndpoint (StudentRepository studentDAO) {
        this.studentDAO = studentDAO;
    }

    @GetMapping(path = "admin/students")
    public ResponseEntity<?> listAll (Pageable pageable) {
        return new ResponseEntity<>(studentDAO.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/{id}")
    public ResponseEntity<?> getStudentById (@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        verifyIfStudentExists(id);
        Optional<Student> student = studentDAO.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/findByName/{name}")
    public ResponseEntity<?> findStudentsByName (@PathVariable String name) {
        return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping(path = "admin/students")
    public ResponseEntity<?> save (@Valid @RequestBody Student student) {
        System.out.println("param recebido "+student.toString());
        return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "admin/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        verifyIfStudentExists(id);
        studentDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "admin/students")
    public ResponseEntity<?> update (@RequestBody Student student) {
        verifyIfStudentExists(student.getId());
        studentDAO.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfStudentExists(Long id) {

        if (!studentDAO.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Student not found for ID: "+id);
        }
    }


}

