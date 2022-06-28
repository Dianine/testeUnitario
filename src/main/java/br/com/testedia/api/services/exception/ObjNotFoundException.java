package br.com.testedia.api.services.exception;

public class ObjNotFoundException extends RuntimeException {


    //Construtor
    public ObjNotFoundException(String message) {
        super(message);
    }
}
