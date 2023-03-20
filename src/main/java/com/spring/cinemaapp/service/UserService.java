package com.spring.cinemaapp.service;

import com.spring.cinemaapp.dto.RegisterDTO;
import com.spring.cinemaapp.model.Role;
import com.spring.cinemaapp.model.RoleType;
import com.spring.cinemaapp.model.User;
import com.spring.cinemaapp.repository.RoleRepository;
import com.spring.cinemaapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }


    public User register(RegisterDTO newUser) {
        Optional<User> foundUserOptional = userRepository.findUserByUsername(newUser.getUsername());
        if (foundUserOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CREATED, "already exist");
        }
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));

        String roleString = newUser.getRole();
        Role userRole = new Role();
        if ("ADMIN".equals(roleString)) {
            Optional<Role> roleOptional = roleRepository.findByRoleType(RoleType.ROLE_ADMIN);
            if (roleOptional.isEmpty()) {
                userRole.setRoleType(RoleType.ROLE_ADMIN);
                userRole = roleRepository.save(userRole);
            } else {
                userRole = roleOptional.get();
            }
        } else if ("CLIENT".equals(roleString)) {
            Optional<Role> roleOptional = roleRepository.findByRoleType(RoleType.ROLE_CLIENT);
            if (roleOptional.isEmpty()) {
                userRole.setRoleType(RoleType.ROLE_CLIENT);
                userRole =  roleRepository.save(userRole);
            } else {
                userRole = roleOptional.get();
            }
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you cannot register with this role");
        }
        Role role = roleRepository.findByRoleType(userRole.getRoleType()).get();
        user.getRoleList().add(role);
        role.getUserList().add(user);

        /*Role foundRole = roleRepository.findByRoleType(RoleType.ROLE_CLIENT);
        user.getRoleList().add(foundRole);
        foundRole.getUserList().add(user);*/
        return userRepository.save(user);

    }
}