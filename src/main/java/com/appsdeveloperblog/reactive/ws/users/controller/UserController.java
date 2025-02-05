package com.appsdeveloperblog.reactive.ws.users.controller;

import com.appsdeveloperblog.reactive.ws.users.dto.UsersRestDTO;
import com.appsdeveloperblog.reactive.ws.users.services.UserService;
import com.appsdeveloperblog.reactive.ws.users.services.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /*
     *Reactive http post request
     *With Mono we make this method reactive. We're waiting for ONE element to be created.
     *@Valid annotation is used to apply tha validations performed in CreateUserRequest
     **/
    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<UsersRestDTO>> createUser(@RequestBody @Valid Mono<CreateUserRequest> createUserRequest) {

        return userService.createUser(createUserRequest)
                .map(userRequest -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .location(URI.create("/users/" + userRequest.getId()))
                        .body(userRequest)
                );

        /* return createUserRequest.map(request -> new UsersRestDTO(UUID.randomUUID(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail()))
                .map(userRest -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .location(URI.create("/users/" + userRest.getId()))
                        .body(userRest));*/

    }

    /*
     * Mono return one user
     * */
    @GetMapping("/{userId}") // http://localhost:8080/users/{userId}
    public Mono<ResponseEntity<UsersRestDTO>> getUserById(@PathVariable("userId") UUID userId) {
        return userService.getUserById(userId)
                .map(userRequest -> ResponseEntity.status(HttpStatus.OK).body(userRequest))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));

        /*//Fake return. We still need to work with the DB layer
        return Mono.just(new UsersRestDTO(
                userId,
                "Natalia",
                "Ronquillo",
                "Natalia@SpringBoot.com"
        ));*/
    }

    /*
     * Flux return many users
     * offset and limit are frequent parameters to implement pagination
     * */
    @GetMapping // http://localhost:8080/users?offset=0&limit=50
    public Flux<UsersRestDTO> getUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "50") int limit
    ) {
        //note: page 1 starts in page 0
        return userService.findAllBy(page, limit);

        //hardcoded implementation
       /* return Flux.just(
                new UsersRestDTO(UUID.randomUUID(), "Luis", "Ronquillo", "luis@springboot.com"),
                new UsersRestDTO(UUID.randomUUID(), "Martha", "Rocha", "martha@springboot.com"),
                new UsersRestDTO(UUID.randomUUID(), "Natalia", "Ronquillo Rocha", "natalia@springboot.com")
        );*/
    }

    @GetMapping("/list") // http://localhost:8080/users/list
    public List<UsersRestDTO> getUsersList() {
        return Arrays.asList(
                new UsersRestDTO(UUID.randomUUID(), "Luis", "Ronquillo", "luis@springboot.com"),
                new UsersRestDTO(UUID.randomUUID(), "Martha", "Rocha", "martha@springboot.com"),
                new UsersRestDTO(UUID.randomUUID(), "Natalia", "Ronquillo Rocha", "natalia@springboot.com")
        );
    }


}
