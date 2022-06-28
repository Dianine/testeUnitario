package br.com.testedia.api.resources.exceptions;

import br.com.testedia.api.services.exception.DataIntegratyViolationException;
import br.com.testedia.api.services.exception.ObjNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

//TRATAMENTO DE EXCEÇÃO PERSONALIZADO - POSTMAM//

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjNotFoundException.class)
    public ResponseEntity<StandarError> ObjectNotFound(ObjNotFoundException ex, HttpServletRequest request) {
        StandarError error = new StandarError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegratyViolationException.class)
    public ResponseEntity<StandarError> dataIntegratyViolationException(DataIntegratyViolationException ex,
                                                                        HttpServletRequest request) {
        StandarError error = new StandarError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}