package com.exam.Catering.servisesTests;

import com.exam.Catering.client.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientServiceTests {

    @Mock
    Client client;

    @Mock
    ClientDto clientDto;

    @InjectMocks
    ClientService clientService;
    @Mock
    ClientRepository clientRepository;

//    @Test
//    public void getAllClientsTests() {
//        clientService.getAllClients();
//        verify(clientRepository).findAll();
//    }

    public static final long Id = 1;
    @Test
    public void viewClientByIdTest(){
        clientService.getById(Id);
        verify(clientRepository).findById(Id);
    }

    @Test
    public void addClient(){
        ClientDto clientDto = mock(ClientDto.class);
        Client client = mock(Client.class);
        when(client.getName()).thenReturn("John");
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        clientService.addClient(clientDto);
        verify(clientRepository).save(any(Client.class));
    }
}
