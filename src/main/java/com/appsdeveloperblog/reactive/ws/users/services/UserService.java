package com.appsdeveloperblog.reactive.ws.users.services;

import com.appsdeveloperblog.reactive.ws.users.controller.CreateUserRequest;
import com.appsdeveloperblog.reactive.ws.users.data.UserEntity;
import com.appsdeveloperblog.reactive.ws.users.data.UserRepository;
import com.appsdeveloperblog.reactive.ws.users.dto.UsersRestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public interface UserService {
    Mono<UsersRestDTO> createUser(Mono<CreateUserRequest> createUserRequestMono);
    Mono<UsersRestDTO> getUserById(UUID id);
    Flux<UsersRestDTO> findAllBy(int pageNum, int limit);
}
