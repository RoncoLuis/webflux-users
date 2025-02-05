package com.appsdeveloperblog.reactive.ws.users.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {

    //ReactiveCrudRepository doesn't support pagination by default. Instead, create our own method
    //find -> performs -> select
    //All -> performs -> *
    //By -> performs -> where
    Flux<UserEntity> findAllBy(Pageable pageable);
}
