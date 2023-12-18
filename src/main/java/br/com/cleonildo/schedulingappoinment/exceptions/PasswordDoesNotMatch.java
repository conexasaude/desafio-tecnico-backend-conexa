package br.com.cleonildo.schedulingappoinment.exceptions;

public class PasswordDoesNotMatch extends RuntimeException {
    public PasswordDoesNotMatch() {
        super("Password does not match!");
    }
}
