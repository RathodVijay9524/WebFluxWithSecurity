package com.example.WebFlux_Crud_SpringSecurity.repository;




import com.example.WebFlux_Crud_SpringSecurity.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}
