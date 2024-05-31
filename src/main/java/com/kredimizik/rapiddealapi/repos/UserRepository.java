package com.kredimizik.rapiddealapi.repos;

import com.kredimizik.rapiddealapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
