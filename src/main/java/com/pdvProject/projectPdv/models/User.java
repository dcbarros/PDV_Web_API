package com.pdvProject.projectPdv.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    private Boolean active;

    private LocalDateTime updateAt;
    private LocalDateTime createAt;
    private LocalDateTime deleteAt;
    

    
    public User() {
    }

    public User(@NotBlank(message = "Campo Obrigatório") String username,
            @NotBlank(message = "Campo Obrigatório") @Email(message = "Email Inválido") String email,
            @NotBlank(message = "Campo Obrigatório") String name, 
            @NotBlank(message = "Campo Obrigatório") String cpf,
            @NotBlank(message = "Campo Obrigatório") String password) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.cpf = cpf;
        this.password = password;
        this.active = true;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
        this.deleteAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(LocalDateTime deleteAt) {
        this.deleteAt = deleteAt;
    }

}
