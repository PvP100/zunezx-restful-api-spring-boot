package net.codejava.store.response_model;

import org.springframework.http.HttpStatus;

public class CategoryResponse extends Response{

    public CategoryResponse(String msg) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, msg);
    }
}
