package com.kredimizik.rapiddealapi.repos;

import com.kredimizik.rapiddealapi.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {
}
