package net.codejava.store.response_model;

import java.util.regex.Pattern;

public class ResponseConstant {
    public static final String VI = "vi";
    public static final String EN = "en";

    public static final String MSG_OK = "Ok";

    public static class Vi {
        public static final String WRONG_EMAIL_OR_PASSWORD = "Sai Email hoặc mật khẩu";
        public static final String OLD_PASSWORD_MISMATCH = "Mật khẩu cũ không khớp";


    }



    public static class ErrorMessage {
        public static final String NOT_FOUND = "not found";
        public static final String RESOURCE_EXIST = "resource exist";
        public static final String INVALID_EMAIL = "invalid email";
        public static final String ACCOUNT_NOT_VERIFIED = "account has't been verified";
        public static final String ACCOUNT_VERIFED = "account has been verified";
        public static final String INTERNAL_SERVER_ERROR = "internal server error";
        public static final String PASSWORD_TOO_SHORT = "Password need to be at lease 6 character length";
        public static final String INVALID_CATEGORY_TITLE = "Category title must not contain special character";
        public static final String SUBCATEGORY_IS_ALREADY_EXIST = "This subCategory is already exist";
        public static final String CATEGORY_IS_ALREADY_EXIST = "This Category is already exist";
        public static final String YOUR_ACCOUNT_IS_LOCKED_BY_ADMIN = "YOUR_ACCOUNT_IS_LOCKED_BY_ADMIN";
    }
}