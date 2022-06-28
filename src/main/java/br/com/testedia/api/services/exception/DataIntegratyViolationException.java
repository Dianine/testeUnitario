package br.com.testedia.api.services.exception;

public class DataIntegratyViolationException extends RuntimeException {


    //Construtor
    public DataIntegratyViolationException(String message) {
        super(message);
    }
}
