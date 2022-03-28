package net.codejava.store.response_model;

import org.springframework.http.HttpStatus;

public class BannerErrorResponse extends Response {

    public BannerErrorResponse(String msg) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, msg);
    }

}
