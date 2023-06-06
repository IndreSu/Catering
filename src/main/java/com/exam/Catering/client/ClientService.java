//package com.exam.Catering.client;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//
//@Service
//public class ClientService {
//
//    private final ClientRepository clientRepository;
//
//    @Autowired
//    public ClientService(ClientRepository clientRepository) {
//        this.clientRepository = clientRepository;
//    }
//
//
//    public Optional<Client> getById(Long clientId) {
//
//        return clientRepository.findById(clientId);
//    }
//
////    public List<Client> getAllClients() {
////
////        return clientRepository.findAll();
////    }
////
//    public ClientDto addClient(ClientDto clientDto) {
//        Client client = ClientMapper.toClient(clientDto);
//        Client savedClient = clientRepository.save(client);
//        return ClientMapper.toClientDto(savedClient);
//    }
////
////    public ClientDto updateClient(Long id, ClientDto clientDto) {
////        Client existingClient = clientRepository.findById(id)
////                .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + id));
////
////        existingClient.setName(clientDto.getName());
////
////        Client updatedClient = clientRepository.save(existingClient);
////        return ClientMapper.toClientDto(updatedClient);
////    }
////
////    public void deleteClient(Long id) {
////        clientRepository.deleteById(id);
////    }
//}
