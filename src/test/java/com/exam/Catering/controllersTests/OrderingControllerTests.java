package com.exam.Catering.controllersTests;

import com.exam.Catering.meal.MealRepository;
import com.exam.Catering.ordering.*;
import com.exam.Catering.users.UsersRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderingControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @InjectMocks
    OrderingController orderingController;

    @Mock
    OrderingService orderingService;

    @Mock
    UsersRepository usersRepository;

    @Autowired
    private MealRepository mealRepository;


    @Test
    public void orderingDtoTest() {
        OrderingDto ordering = new OrderingDto(OrderingStatus.PENDING);

        assertEquals(OrderingStatus.PENDING, ordering.getStatus());

        ordering.setStatus(OrderingStatus.COMPLETED);
        assertEquals(OrderingStatus.COMPLETED, ordering.getStatus());
    }

    @Test
    public void getAllOrderingsTest(){
        Ordering ordering = new Ordering();
        List<Ordering> orderings = new ArrayList<>();
        orderings.add(ordering);
        when(orderingService.getAllOrderings()).thenReturn(orderings);
        assertEquals(orderingController.getAllOrderings().getBody().size(), orderings.size());
    }

//    @Test
//    public void viewOrderingByIdTest() throws Exception {
//
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orderings/1")
//        ).andExpect(status().isOk()).andReturn();
//        OrderingDto result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<OrderingDto>() {
//        });
//        Assertions.assertEquals(result.getStatus(), OrderingStatus.CONFIRMED,"Get ordering by Id should return ordering with correct status");
//    }

    @Test
    public void manageOrderTest() {

        OrderingDto orderingDto = new OrderingDto();
        orderingDto.setStatus(OrderingStatus.COMPLETED);

        OrderingDto updatedOrderingDto = new OrderingDto();
        updatedOrderingDto.setStatus(OrderingStatus.COMPLETED);
        when(orderingService.manageOrderStatus(1L, orderingDto)).thenReturn(updatedOrderingDto);

        OrderingController orderingController = new OrderingController(orderingService, usersRepository);

        ResponseEntity<OrderingDto> responseEntity = orderingController.manageOrderStatus(1L, orderingDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedOrderingDto, responseEntity.getBody());
    }

    @Test
    public void deleteOrderingTest() {

        Long orderingId = 1L;

        ResponseEntity<Void> response = orderingController.deleteOrdering(orderingId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderingService).deleteOrdering(orderingId);
    }
}
