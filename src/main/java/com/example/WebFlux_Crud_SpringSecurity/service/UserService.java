package com.example.WebFlux_Crud_SpringSecurity.service;

import com.example.WebFlux_Crud_SpringSecurity.dto.UserDto;
import com.example.WebFlux_Crud_SpringSecurity.entity.User;
import com.example.WebFlux_Crud_SpringSecurity.exception.UserNotFoundException;
import com.example.WebFlux_Crud_SpringSecurity.mapper.Mapper;
import com.example.WebFlux_Crud_SpringSecurity.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserService(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public Flux<UserDto> getUsers() {
        return userRepository.findAll()
                .map(user -> mapper.toDto(user,UserDto.class));
    }

    public Mono<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with id: " + id)))
                .map(user -> mapper.toDto(user,UserDto.class));
    }

    public Mono<UserDto> createUser(UserDto userDto) {
        User user=Mapper.toEntity(userDto,User.class,"id");
        return userRepository.save(user)
                .map(savedUser -> mapper.toDto(savedUser,UserDto.class));
    }

    public Mono<UserDto> updateUser(Long id, UserDto userDetails) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with id: " + id)))
                .flatMap(existingUser -> {
                    BeanUtils.copyProperties(userDetails, existingUser, "id");
                    return userRepository.save(existingUser)
                            .map(user -> mapper.toDto(user,UserDto.class));
                });
    }

    public Mono<Void> deleteUser(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with id: " + id)))
                .flatMap(userRepository::delete);
    }
}
