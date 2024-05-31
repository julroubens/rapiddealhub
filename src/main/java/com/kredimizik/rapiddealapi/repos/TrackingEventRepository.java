package com.kredimizik.rapiddealapi.repos;

import com.kredimizik.rapiddealapi.domain.Shipment;
import com.kredimizik.rapiddealapi.domain.TrackingEvent;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TrackingEventRepository extends JpaRepository<TrackingEvent, Long> {

    TrackingEvent findFirstByShipment(Shipment shipment);

}
