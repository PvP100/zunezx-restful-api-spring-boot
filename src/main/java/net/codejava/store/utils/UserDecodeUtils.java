package net.codejava.store.utils;


import net.codejava.store.auth.models.User;
import net.codejava.store.response_model.HeaderConstant;

public class UserDecodeUtils {
    public static User decodeFromAuthorizationHeader(String headerValue) {
        headerValue = headerValue.replace(HeaderConstant.AUTHORIZATION_VALUE_PREFIX, "");
        String decodedValue = Base64Utils.decode(headerValue);
        String values[] = decodedValue.split(":");
        return new User(values[0], values[1]);
    }
}
