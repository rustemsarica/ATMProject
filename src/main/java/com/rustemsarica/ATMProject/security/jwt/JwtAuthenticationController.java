package com.rustemsarica.ATMProject.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rustemsarica.ATMProject.data.entities.UserEntity;
import com.rustemsarica.ATMProject.providers.CustomAuthenticationProvider;
import com.rustemsarica.ATMProject.security.jwtRequests.JwtLoginRequest;
import com.rustemsarica.ATMProject.security.jwtRequests.jwtRegisterRequest;


@RestController
@CrossOrigin
public class JwtAuthenticationController {
    
    @Autowired
    private CustomAuthenticationProvider authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;


    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody jwtRegisterRequest user) throws Exception {
        try {
            UserEntity userEntity = userDetailsService.save(user);
            return ResponseEntity.ok(userEntity);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
        
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtLoginRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        
        final String token = jwtTokenUtil.generateToken(userDetails);
        
        return ResponseEntity.ok(new JwtResponse(token));
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch(UsernameNotFoundException e){
            System.out.println("*************");
            System.out.println("user not :"+e);          
            throw new Exception("user not found", e);
        }
        catch(LockedException e){
            System.out.println("*************");
            System.out.println("Locked :"+e);          
            throw new Exception("user not found", e);
        } catch (DisabledException e) {
            System.out.println("*************");
            System.out.println("Disabled :"+e);          
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            
            System.out.println("*************");
            System.out.println(e);
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch(Exception e){
            System.out.println("*************");
            System.out.println(e);
        }
    }
}
