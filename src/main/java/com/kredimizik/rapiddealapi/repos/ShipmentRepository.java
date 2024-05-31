package com.kredimizik.rapiddealapi.repos;

import com.kredimizik.rapiddealapi.domain.Box;
import com.kredimizik.rapiddealapi.domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    Shipment findFirstByBox(Box box);

}
