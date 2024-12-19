package com.keca.AirVentureBack.authentication.infrastructure.repository;

import com.keca.AirVentureBack.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     User findByEmail(String email);

     boolean existsByEmail(String email);

}
