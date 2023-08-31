package com.rustemsarica.ATMProject.security.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rustemsarica.ATMProject.data.entities.UserEntity;
import com.rustemsarica.ATMProject.data.repositories.UserRepository;
import com.rustemsarica.ATMProject.security.jwtRequests.jwtRegisterRequest;

@Service
public class JwtUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = (UserEntity) userRepository.findByUsername(username).get();

        if (user == null) {
            throw new UsernameNotFoundException("Böyle bir kullanıcı yoktur " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public UserEntity save(jwtRegisterRequest user) throws Exception{
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new Exception("User already exist");
        }
        UserEntity newUser = new UserEntity();
        newUser.setName(user.getName());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(newUser); 
    }

}
