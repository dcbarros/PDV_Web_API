package com.pdvProject.projectPdv.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pdvProject.projectPdv.Utils.CpfValidator;
import com.pdvProject.projectPdv.models.Role;
import com.pdvProject.projectPdv.models.User;
import com.pdvProject.projectPdv.models.enums.ERole;
import com.pdvProject.projectPdv.payload.requests.LoginRequest;
import com.pdvProject.projectPdv.payload.requests.SignupRequest;
import com.pdvProject.projectPdv.payload.responses.MessageResponse;
import com.pdvProject.projectPdv.payload.responses.UserInfoResponse;
import com.pdvProject.projectPdv.repository.RoleRepository;
import com.pdvProject.projectPdv.repository.UserRepository;
import com.pdvProject.projectPdv.security.jwt.JwtUtils;
import com.pdvProject.projectPdv.security.services.UserDetailsImpl;

@Service
public class AuthenticationService {
    
    @Autowired
    AuthenticationManager _authenticationManager;

    @Autowired
    UserRepository _userRepository;

    @Autowired
    RoleRepository _roleRepository;

    @Autowired
    PasswordEncoder _encoder;

    @Autowired
    JwtUtils _jwtUtils;

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = _authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = _jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .body(new UserInfoResponse(userDetails.getId(),
                                                            userDetails.getUsername(),
                                                            userDetails.getEmail(),
                                                            roles));
        
    }

    public ResponseEntity<?> registerUser(SignupRequest signupRequest){
        
        String cpf = signupRequest.getCpf();

        if(_userRepository.existsByUsername(signupRequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username já está em uso"));
        }
        if (_userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email já está em uso"));
        }
        if(_userRepository.existsByCpf(cpf))
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: CPF já está em uso"));
        }
        if(!CpfValidator.isValid(cpf)){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: CPF inválido"));
        }

        User user = new User(signupRequest.getUsername(),
                             signupRequest.getEmail(),
                             signupRequest.getName(), 
                             signupRequest.getCpf(), 
                             _encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = _roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Papel não encontrado."));
            roles.add(userRole);
        }else{
            strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = _roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                    break;
                default:
                    Role userRole = _roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        _userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Usuário cadastrado com sucesso!"));
        
    }

    public ResponseEntity<?> logoutUser(){
        ResponseCookie cookie = _jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new MessageResponse("Usuário desconectado!"));
    }
}
