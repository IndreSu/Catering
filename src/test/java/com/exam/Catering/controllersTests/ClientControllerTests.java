//package com.exam.Catering.controllersTests;
//
//import com.exam.Catering.client.Client;
//import com.exam.Catering.client.ClientController;
//import com.exam.Catering.client.ClientDto;
//import com.exam.Catering.client.ClientService;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import javax.transaction.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//public class ClientControllerTests {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @InjectMocks
//    ClientController clientController;
//
//    @Mock
//    ClientService clientService;
//
//    @Test
//    public void clientDtoTest() {
//        ClientDto client = new ClientDto("John");
//
//        assertEquals("John", client.getName());
//
//        client.setName("Peter");
//        assertEquals("Peter", client.getName());
//    }
//
//    @Test
//    public void viewClientByIdTest() throws Exception {
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clients/1")
//        ).andExpect(status().isOk()).andReturn();
//        ClientDto result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<ClientDto>() {
//        });
//        Assertions.assertEquals(result.getName(), "John", "Get client by Id should return existing client");
//    }
//
//    @Test
//    public void addClientTest() {
//
//        MockitoAnnotations.openMocks(this);
//
//        ClientDto clientDto = new ClientDto();
//        ClientDto createdClient = new ClientDto();
//
//        when(clientService.addClient(clientDto)).thenReturn(createdClient);
//        ResponseEntity<ClientDto> response = clientController.addClient(clientDto);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//    }
//}