package net.codejava.store.response_model;

import org.springframework.http.HttpStatus;

public class NotFoundResponse extends Response {
    public NotFoundResponse() {
        super(HttpStatus.NOT_FOUND, ResponseConstant.ErrorMessage.NOT_FOUND);
    }

    public NotFoundResponse(String what) {
        super(HttpStatus.NOT_FOUND, what);
    }

    public <T> NotFoundResponse(T data) {
        super(HttpStatus.NOT_FOUND, ResponseConstant.ErrorMessage.NOT_FOUND, data);
    }
}
