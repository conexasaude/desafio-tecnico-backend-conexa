package br.com.conexa.domain.user;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    CARDIOLOGISTA("Cardiologista");

    private String role;

    UserRole(String role){
        this.role = role;
    }

}


