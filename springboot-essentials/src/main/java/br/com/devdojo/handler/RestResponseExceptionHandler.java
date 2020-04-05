package br.com.devdojo.handler;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@ControllerAdvice
public class RestResponseExceptionHandler extends DefaultResponseErrorHandler {
   // essa classe trata os erros 404 do JavaClientDAO, para quando usamos, por exemplo, um  dao.findById(ID) utilizando um ID como parametro que nao existe.

    @Override
    public boolean hasError (ClientHttpResponse response) throws IOException {
        System.out.println("Inside hasError ");
        return super.hasError(response);
    }

    @Override
    public void handleError (ClientHttpResponse response) throws IOException {
        System.out.println("Doing something with statusCode "+response.getStatusCode());
        System.out.println("Doing something with body "+ IOUtils.toString(response.getBody(), "UTF-8"));
//        super.handleError(response);
    }
}
