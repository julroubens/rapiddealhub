package com.kredimizik.rapiddealapi.repos;

import com.kredimizik.rapiddealapi.domain.Client;
import com.kredimizik.rapiddealapi.domain.Shipment;
import com.kredimizik.rapiddealapi.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findFirstByClient(Client client);

    Transaction findFirstByShipment(Shipment shipment);

}
