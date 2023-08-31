package com.rustemsarica.ATMProject.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rustemsarica.ATMProject.business.services.UserServices;
import com.rustemsarica.ATMProject.data.entities.UserEntity;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider{

    private final int MAX_FAILED_ATTEMPTS = 3;
    
    @Autowired
    private UserServices userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        // Kullanıcı hesabını al
        UserEntity user = userService.getUserByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        } 
        else if (user.isAccountNonLocked()) {
            if (passwordMatches(password, user.getPassword())) {
                // Şifre doğru, hesap kilidi kaldır
                user.setFailedLoginAttempts(0);
                userService.saveUser(user);
                return new UsernamePasswordAuthenticationToken(username, password);
            } else {
                // Şifre yanlış, deneme sayısını artır
                int failedAttempts = user.getFailedLoginAttempts() + 1;
                user.setFailedLoginAttempts(failedAttempts);
                if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
                    user.setAccountNonLocked(false);
                }
                userService.saveUser(user);
                throw new BadCredentialsException("Invalid password");
            }
        } 
        else if(!user.isAccountNonLocked()){
            throw new LockedException("Account is locked");
        }else{
            throw new UsernameNotFoundException("error");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
    
    private boolean passwordMatches(String rawPassword, String encodedPassword) {
        
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
