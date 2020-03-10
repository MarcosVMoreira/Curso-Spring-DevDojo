package br.com.devdojo.awesome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication: mesma coisa que usar @Configuration, @EnableAutoConfigation e @ComponentScan
//passa uma ideia pro spring do que ele tem que configurar com base nos componentes que temos
//o componenteScan passa onde está o controller, já que nao está na mesma pasta do ApplicationStart. Sem ele, a página nao consegue achar os endpoins e retorna 404 qnd a gente acessa pelo navegador
//entretanto, quando usamos a classe ApplicationStart na raiz do pacote, nao precisamos passar pro ComponentScan onde está o controller, caso ele esteja em uma pasta também na raiz do nosso pacote
//Exemplo: nesse caso, ApplcationStart está no mesmo nível de StudentEndpoint, que é nosso controller
@SpringBootApplication
public class ApplicationStart {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }

}

