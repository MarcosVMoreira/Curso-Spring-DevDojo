package br.com.devdojo.awesome.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//passa uma ideia pro spring do que ele tem que configurar com base nos componentes que temos
//o componenteScan passa onde está o controller, já que nao está na mesma pasta do ApplicationStart. Sem ele, a página nao consegue achar os endpoins e retorna 404 qnd a gente acessa pelo navegador
@EnableAutoConfiguration
@ComponentScan(basePackages = "br.com.devdojo.awesome.endpoint")
public class ApplicationStart {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }

}
