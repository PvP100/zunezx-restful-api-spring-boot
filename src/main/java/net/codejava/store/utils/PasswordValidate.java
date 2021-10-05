package net.codejava.store.utils;

public class PasswordValidate {
    public static boolean isPasswordValidate(String password){
        if(password.length() < 6){
            return false;
        }
        return true;
    }
}
