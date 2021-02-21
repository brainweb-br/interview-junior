package br.com.brainweb.interview.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericException extends RuntimeException {

    private final HttpStatus status;
    private final String responseMessage;

    public GenericException(HttpStatus status, String responseMessage) {
        super(responseMessage);
        this.status = status;
        this.responseMessage = responseMessage;
    }

}
