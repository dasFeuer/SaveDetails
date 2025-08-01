package com.example.CollectionProject.services.impl;

import com.example.CollectionProject.domain.entities.User;
import com.example.CollectionProject.domain.entities.UserPrincipal;
import com.example.CollectionProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            System.out.println("User Not Found");
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new UserPrincipal(user);
//    // Will need Username for login and other stuff where username is required
//    }
//}

    @Override
    public UserDetails loadUserByUsername(String email) throws BadCredentialsException {
        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserPrincipal(user);
    }     // Will need email for login and other stuff where email is required
}