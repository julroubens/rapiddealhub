package com.kredimizik.rapiddealapi.repos;

import com.kredimizik.rapiddealapi.domain.Box;
import com.kredimizik.rapiddealapi.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoxRepository extends JpaRepository<Box, Long> {

    Box findFirstByOrder(Order order);

}
