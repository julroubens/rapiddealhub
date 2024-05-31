package com.kredimizik.rapiddealapi.service;

import com.kredimizik.rapiddealapi.domain.Client;
import com.kredimizik.rapiddealapi.domain.Order;
import com.kredimizik.rapiddealapi.domain.Transaction;
import com.kredimizik.rapiddealapi.model.ClientDTO;
import com.kredimizik.rapiddealapi.repos.ClientRepository;
import com.kredimizik.rapiddealapi.repos.OrderRepository;
import com.kredimizik.rapiddealapi.repos.TransactionRepository;
import com.kredimizik.rapiddealapi.util.NotFoundException;
import com.kredimizik.rapiddealapi.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;

    public ClientService(final ClientRepository clientRepository,
            final OrderRepository orderRepository,
            final TransactionRepository transactionRepository) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<ClientDTO> findAll() {
        final List<Client> clients = clientRepository.findAll(Sort.by("clientId"));
        return clients.stream()
                .map(client -> mapToDTO(client, new ClientDTO()))
                .toList();
    }

    public ClientDTO get(final Long clientId) {
        return clientRepository.findById(clientId)
                .map(client -> mapToDTO(client, new ClientDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ClientDTO clientDTO) {
        final Client client = new Client();
        mapToEntity(clientDTO, client);
        return clientRepository.save(client).getClientId();
    }

    public void update(final Long clientId, final ClientDTO clientDTO) {
        final Client client = clientRepository.findById(clientId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(clientDTO, client);
        clientRepository.save(client);
    }

    public void delete(final Long clientId) {
        clientRepository.deleteById(clientId);
    }

    private ClientDTO mapToDTO(final Client client, final ClientDTO clientDTO) {
        clientDTO.setClientId(client.getClientId());
        clientDTO.setClientName(client.getClientName());
        clientDTO.setClientEmail(client.getClientEmail());
        clientDTO.setPhoneNumber(client.getPhoneNumber());
        clientDTO.setAddress(client.getAddress());
        return clientDTO;
    }

    private Client mapToEntity(final ClientDTO clientDTO, final Client client) {
        client.setClientName(clientDTO.getClientName());
        client.setClientEmail(clientDTO.getClientEmail());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setAddress(clientDTO.getAddress());
        return client;
    }

    public ReferencedWarning getReferencedWarning(final Long clientId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Client client = clientRepository.findById(clientId)
                .orElseThrow(NotFoundException::new);
        final Order clientOrder = orderRepository.findFirstByClient(client);
        if (clientOrder != null) {
            referencedWarning.setKey("client.order.client.referenced");
            referencedWarning.addParam(clientOrder.getOderId());
            return referencedWarning;
        }
        final Transaction clientTransaction = transactionRepository.findFirstByClient(client);
        if (clientTransaction != null) {
            referencedWarning.setKey("client.transaction.client.referenced");
            referencedWarning.addParam(clientTransaction.getTransactionId());
            return referencedWarning;
        }
        return null;
    }

}
