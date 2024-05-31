package com.kredimizik.rapiddealapi.repos;

import com.kredimizik.rapiddealapi.domain.Client;
import com.kredimizik.rapiddealapi.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findFirstByClient(Client client);

}
