package com.kredimizik.rapiddealapi.service;

import com.kredimizik.rapiddealapi.domain.Box;
import com.kredimizik.rapiddealapi.domain.Shipment;
import com.kredimizik.rapiddealapi.domain.TrackingEvent;
import com.kredimizik.rapiddealapi.domain.Transaction;
import com.kredimizik.rapiddealapi.model.ShipmentDTO;
import com.kredimizik.rapiddealapi.repos.BoxRepository;
import com.kredimizik.rapiddealapi.repos.ShipmentRepository;
import com.kredimizik.rapiddealapi.repos.TrackingEventRepository;
import com.kredimizik.rapiddealapi.repos.TransactionRepository;
import com.kredimizik.rapiddealapi.util.NotFoundException;
import com.kredimizik.rapiddealapi.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final BoxRepository boxRepository;
    private final TransactionRepository transactionRepository;
    private final TrackingEventRepository trackingEventRepository;

    public ShipmentService(final ShipmentRepository shipmentRepository,
            final BoxRepository boxRepository, final TransactionRepository transactionRepository,
            final TrackingEventRepository trackingEventRepository) {
        this.shipmentRepository = shipmentRepository;
        this.boxRepository = boxRepository;
        this.transactionRepository = transactionRepository;
        this.trackingEventRepository = trackingEventRepository;
    }

    public List<ShipmentDTO> findAll() {
        final List<Shipment> shipments = shipmentRepository.findAll(Sort.by("shipmentId"));
        return shipments.stream()
                .map(shipment -> mapToDTO(shipment, new ShipmentDTO()))
                .toList();
    }

    public ShipmentDTO get(final Long shipmentId) {
        return shipmentRepository.findById(shipmentId)
                .map(shipment -> mapToDTO(shipment, new ShipmentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ShipmentDTO shipmentDTO) {
        final Shipment shipment = new Shipment();
        mapToEntity(shipmentDTO, shipment);
        return shipmentRepository.save(shipment).getShipmentId();
    }

    public void update(final Long shipmentId, final ShipmentDTO shipmentDTO) {
        final Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(shipmentDTO, shipment);
        shipmentRepository.save(shipment);
    }

    public void delete(final Long shipmentId) {
        shipmentRepository.deleteById(shipmentId);
    }

    private ShipmentDTO mapToDTO(final Shipment shipment, final ShipmentDTO shipmentDTO) {
        shipmentDTO.setShipmentId(shipment.getShipmentId());
        shipmentDTO.setTrackingNumber(shipment.getTrackingNumber());
        shipmentDTO.setShimentDate(shipment.getShimentDate());
        shipmentDTO.setDeliveryDate(shipment.getDeliveryDate());
        shipmentDTO.setStatus(shipment.getStatus());
        shipmentDTO.setCarrier(shipment.getCarrier());
        shipmentDTO.setBox(shipment.getBox() == null ? null : shipment.getBox().getPackegeId());
        return shipmentDTO;
    }

    private Shipment mapToEntity(final ShipmentDTO shipmentDTO, final Shipment shipment) {
        shipment.setTrackingNumber(shipmentDTO.getTrackingNumber());
        shipment.setShimentDate(shipmentDTO.getShimentDate());
        shipment.setDeliveryDate(shipmentDTO.getDeliveryDate());
        shipment.setStatus(shipmentDTO.getStatus());
        shipment.setCarrier(shipmentDTO.getCarrier());
        final Box box = shipmentDTO.getBox() == null ? null : boxRepository.findById(shipmentDTO.getBox())
                .orElseThrow(() -> new NotFoundException("box not found"));
        shipment.setBox(box);
        return shipment;
    }

    public ReferencedWarning getReferencedWarning(final Long shipmentId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(NotFoundException::new);
        final Transaction shipmentTransaction = transactionRepository.findFirstByShipment(shipment);
        if (shipmentTransaction != null) {
            referencedWarning.setKey("shipment.transaction.shipment.referenced");
            referencedWarning.addParam(shipmentTransaction.getTransactionId());
            return referencedWarning;
        }
        final TrackingEvent shipmentTrackingEvent = trackingEventRepository.findFirstByShipment(shipment);
        if (shipmentTrackingEvent != null) {
            referencedWarning.setKey("shipment.trackingEvent.shipment.referenced");
            referencedWarning.addParam(shipmentTrackingEvent.getEventId());
            return referencedWarning;
        }
        return null;
    }

}
