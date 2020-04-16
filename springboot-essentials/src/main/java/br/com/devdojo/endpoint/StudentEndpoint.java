package br.com.devdojo.endpoint;

import br.com.devdojo.error.ResourceNotFoundException;
import br.com.devdojo.model.Student;
import br.com.devdojo.repository.StudentRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

//RestController, em comparacao com Controller, adiciona automaticamente a notacao ResponseBody para transformar para JSON
@RestController
@RequestMapping("v1")
@Api("API REST Student")

public class StudentEndpoint {
    private final StudentRepository studentDAO;

    @Autowired
    public StudentEndpoint (StudentRepository studentDAO) {
        this.studentDAO = studentDAO;
    }

    @GetMapping(path = "protected/students")
    @ApiOperation(value = "Return a list with all students", response = Student[].class, produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    public ResponseEntity<?> listAll (Pageable pageable) {
        return new ResponseEntity<>(studentDAO.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/{id}")
    @ApiOperation(value = "Return student by given id", response = Student.class)
    public ResponseEntity<?> getStudentById (@PathVariable("id") Long id, Authentication authentication) { //esse authentication recebe os dados de quem está usando o endpoint
        System.out.println("Detalhes do usuario: "+authentication.getPrincipal());
        verifyIfStudentExists(id);
        Optional<Student> student = studentDAO.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/findByName/{name}")
    @ApiOperation(value = "Return student by given id", response = Student.class)
    public ResponseEntity<?> findStudentsByName (@PathVariable String name) {
        return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping(path = "admin/students")
    @ApiOperation(value = "Save given student", response = Student.class, produces="application/json", consumes="application/json")
    public ResponseEntity<?> save (@Valid @RequestBody Student student) {
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

