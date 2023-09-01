package com.rustemsarica.ATMProject.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rustemsarica.ATMProject.data.entities.UserEntity;
import com.rustemsarica.ATMProject.data.repositories.UserRepository;
import com.rustemsarica.ATMProject.security.jwtRequests.JwtRegisterRequest;

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
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString())); 
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    public UserEntity save(JwtRegisterRequest user) {
        
        UserEntity newUser = new UserEntity();
        newUser.setName(user.getName());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(newUser); 
    }

}
