//package com.exam.Catering.client;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@Validated
//@RequestMapping("api/v1/clients")
//public class ClientController {
//
//    private final ClientService clientService;
//
//    @Autowired
//    public ClientController(ClientService clientService) {
//
//        this.clientService = clientService;
//    }
//
//    @GetMapping("/{clientId}")
//    public Optional<Client> getClient(@PathVariable Long clientId) {
//
//        return clientService.getById(clientId);
//    }
//
////    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
////    public List<Client> getAllClients() {
////
////        return clientService.getAllClients();
////    }
////
//    @PostMapping
//    public ResponseEntity<ClientDto> addClient(@RequestBody ClientDto clientDto) {
//        ClientDto createdClient = clientService.addClient(clientDto);
//        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
//    }
////
////    @PutMapping("/{id}")
////    public ResponseEntity<ClientDto> updateClient(@PathVariable("id") Long id, @RequestBody ClientDto clientDto) {
////        ClientDto updatedClient = clientService.updateClient(id, clientDto);
////        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
////    }
////
////    @DeleteMapping("/{id}")
////    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
////        clientService.deleteClient(id);
////        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
////    }
//}
