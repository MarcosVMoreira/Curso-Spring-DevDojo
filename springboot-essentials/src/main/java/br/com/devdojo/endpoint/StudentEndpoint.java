package br.com.devdojo.awesome.endpoint;

import br.com.devdojo.awesome.model.Student;
import br.com.devdojo.awesome.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;

//RestController, em comparacao com Controller, adiciona automaticamente a notacao ResponseBody para transformar para JSON
@RestController
@RequestMapping("student")
public class StudentEndpoint {
    @Autowired
    private DateUtil dateUtil;

    @RequestMapping(method = RequestMethod.GET, path = "/list")
    public List<Student> listAll() {

        System.out.println("Data e hora: "+dateUtil.formatLocalDateToDatabaseStyle(LocalDateTime.now()));
        return asList(new Student("Deku"), new Student("Todoroki"));

    }


}

