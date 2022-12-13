package br.com.hugolini.exceptions;

public class RequiredObjectIsNullException extends RuntimeException{

    public RequiredObjectIsNullException(String message) {
        super(message);
    }
}
