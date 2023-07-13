package com.pdvProject.projectPdv.payload.requests;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SignupRequest {

    @NotBlank(message = "Campo Obrigatório")
    private String username;    
    @NotBlank(message = "Campo Obrigatório")
    @Email(message = "Email Inválido")
    private String email;
    @NotBlank(message = "Campo Obrigatório")
    private String name;
    @NotBlank(message = "Campo Obrigatório")
    private String cpf; 
    @NotBlank(message = "Campo Obrigatório")
    private String password;

    private Set<String> role;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }
    
    public void setRole(Set<String> role){
        this.role = role;
    }
    
}
