package net.codejava.store.response_model;

import org.springframework.http.HttpStatus;

public class CustomerNotExistsResponse extends Response{

    public CustomerNotExistsResponse(String msg) {
        super(HttpStatus.NOT_FOUND, msg);
    }

}
