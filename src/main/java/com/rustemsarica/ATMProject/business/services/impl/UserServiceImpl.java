package com.rustemsarica.ATMProject.business.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rustemsarica.ATMProject.business.dto.UserDto;
import com.rustemsarica.ATMProject.business.services.UserServices;
import com.rustemsarica.ATMProject.data.entities.UserEntity;
import com.rustemsarica.ATMProject.data.repositories.UserRepository;
import com.rustemsarica.ATMProject.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> listDto = new ArrayList<>();
        Iterable<UserEntity> users = userRepository.findAll();

        for(UserEntity temp : users){
            UserDto userDto = entityToDto(temp);
            listDto.add(userDto);
        }

        return listDto;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        UserEntity userEntity = dtoToEntity(userDto);        
        userRepository.save(userEntity);
        return userDto;
    }

    @Override
    public ResponseEntity<UserDto> getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found."));
        UserDto userDto = entityToDto(userEntity);
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<UserDto> updateUser(Long id, UserDto userDto) {
        UserEntity newUserEntity = dtoToEntity(userDto);

        UserEntity userEntity = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));

        userEntity.setName(newUserEntity.getName());
        userEntity.setUsername(newUserEntity.getUsername());
        
        UserEntity updatedUser = userRepository.save(userEntity);

        return ResponseEntity.ok(entityToDto(updatedUser));
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        userRepository.delete(userEntity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", true);
        return ResponseEntity.ok(response);
    }

    @Override
    public UserDto entityToDto(UserEntity userEntity) {
       UserDto userDto = modelMapper.map(userEntity, UserDto.class);
       return userDto;
    }

    @Override
    public UserEntity dtoToEntity(UserDto userDto) {
       UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
       return userEntity;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User not found."));
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    
    
}
