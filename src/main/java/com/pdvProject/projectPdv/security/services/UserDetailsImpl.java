package com.pdvProject.projectPdv.security.services;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pdvProject.projectPdv.models.User;


public class UserDetailsImpl implements UserDetails{
    
    //private static final long SerialVersionUID = 1L;
    private Long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private String name;
    private String cpf;
    private Boolean active;
    private LocalDateTime updateAt;
    private LocalDateTime createAt;
    private LocalDateTime deleteAt;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String email, String password, String name, String cpf,
            Boolean active, LocalDateTime updateAt, LocalDateTime createAt, LocalDateTime deleteAt,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.cpf = cpf;
        this.active = active;
        this.updateAt = updateAt;
        this.createAt = createAt;
        this.deleteAt = deleteAt;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                                            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                                            .collect(Collectors.toList());
        return new UserDetailsImpl(
            user.getId(), 
            user.getUsername(), 
            user.getEmail(), 
            user.getPassword(), 
            user.getName(), 
            user.getCpf(), 
            user.getActive(), 
            user.getUpdateAt(), 
            user.getCreateAt(), 
            user.getDeleteAt(), 
            authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
        return true;
        if (o == null || getClass() != o.getClass())
        return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public LocalDateTime getDeleteAt() {
        return deleteAt;
    }    
    
    

 
}
