package br.com.devdojo.awesome.endpoint;

import br.com.devdojo.awesome.model.Student;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;

//RestController, em comparacao com Controller, adiciona automaticamente a notacao ResponseBody para transformar para JSON
@RestController
@RequestMapping("student")
public class StudentEndpoint {
    @RequestMapping(method = RequestMethod.GET, path = "/list")
    public List<Student> listAll() {
        return asList(new Student("Deku"), new Student("Todoroki"));
    }


}
