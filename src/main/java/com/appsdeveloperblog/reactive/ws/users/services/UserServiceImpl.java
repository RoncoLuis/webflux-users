package com.appsdeveloperblog.reactive.ws.users.services;

import com.appsdeveloperblog.reactive.ws.users.controller.CreateUserRequest;
import com.appsdeveloperblog.reactive.ws.users.data.UserEntity;
import com.appsdeveloperblog.reactive.ws.users.data.UserRepository;
import com.appsdeveloperblog.reactive.ws.users.dto.UsersRestDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Service //This annotation means this class will contain business logic
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    /*If we were to inject the entire WebSecurityConfiguration class, we would be introducing an unnecessary dependency, and the UserServiceImpl class would have access to more functionality than it needs.*/
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<UsersRestDTO> createUser(Mono<CreateUserRequest> createUserRequestMono) {
        return createUserRequestMono
                .flatMap(this::convertToEntity)
                .flatMap(userRepository::save) //this line needs to be handled with an exception
                .mapNotNull(this::convertToRest);
    }

    @Override
    public Mono<UsersRestDTO> getUserById(UUID id) {
        return userRepository.findById(id) //this line returns a UserEntity
                .mapNotNull(this::convertToRest); //converts userEntity into userRequest
    }

    @Override
    public Flux<UsersRestDTO> findAllBy(int page, int limit) {
        //if page number is provided and greater than 0 decrease in one
        if (page > 0) page = page - 1; //this line fix the natural mismatch of pageable starting in 0
        //Create pageable obj
        Pageable pageable = PageRequest.of(page, limit);
        return userRepository.findAllBy(pageable).mapNotNull(this::convertToRest);
    }

    private Mono<UserEntity> convertToEntity(CreateUserRequest createUserRequest) {
        return Mono.fromCallable(() -> {
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(createUserRequest, userEntity);
            //Encrypting password
            userEntity.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
            return userEntity;
        }).subscribeOn(Schedulers.boundedElastic());


    }

    private UsersRestDTO convertToRest(UserEntity userEntity) {
        UsersRestDTO usersRestDTO = new UsersRestDTO();
        BeanUtils.copyProperties(userEntity, usersRestDTO);
        return usersRestDTO;
    }
}
