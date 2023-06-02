package com.exam.Catering.servisesTests;

import com.exam.Catering.client.Client;
import com.exam.Catering.client.ClientRepository;
import com.exam.Catering.client.ClientService;
import com.exam.Catering.meal.Meal;
import com.exam.Catering.meal.MealDto;
import com.exam.Catering.meal.MealRepository;
import com.exam.Catering.meal.MealService;
import com.exam.Catering.menu.MenuRepository;
import com.exam.Catering.ordering.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.ClientHttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderingServiceTests {

    @Mock
    Ordering ordering;

    @Mock
    OrderingDto orderingDto;

    @InjectMocks
    OrderingService orderingService;

    @Mock
    OrderingRepository orderingRepository;

    @Mock
    MenuRepository menuRepository;

    @Mock
    MealRepository mealRepository;

    @Mock
    ClientRepository clientRepository;

    @Mock
    ClientService clientService;

    @Mock
    MealService mealService;

    @Test
    public void getAllOrderingsTest() {
        orderingService.getAllOrderings();
        verify(orderingRepository).findAll();
    }

    public static final long Id = 1;
    @Test
    public void viewOrderingByIdTest(){
        orderingService.getById(Id);
        verify(orderingRepository).findById(Id);
    }


    @Test
    void makeOrderingTest() {

        Client client = new Client();
        client.setId(1L);
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        List<Long> mealIds = new ArrayList<>();
        mealIds.add(1L);
        mealIds.add(2L);

        Meal meal1 = new Meal();
        meal1.setId(1L);
        Meal meal2 = new Meal();
        meal2.setId(2L);

        List<Meal> meals = new ArrayList<>();
        meals.add(meal1);
        meals.add(meal2);

        when(mealRepository.findAllById(mealIds)).thenReturn(meals);

        Ordering ordering = new Ordering();
        ordering.setId(1L);
        ordering.setClient(client);
        ordering.setMeals(meals);
        ordering.setStatus(OrderingStatus.PENDING);
        when(orderingRepository.save(any(Ordering.class))).thenReturn(ordering);

        Ordering resultOrdering = orderingService.makeOrdering(client.getId(), mealIds);

        verify(clientRepository).findById(client.getId());
        verify(mealRepository).findAllById(mealIds);
        verify(orderingRepository).save(any(Ordering.class));

        assertEquals(ordering.getId(), resultOrdering.getId());
        assertEquals(ordering.getClient(), resultOrdering.getClient());
        assertEquals(ordering.getMeals(), resultOrdering.getMeals());
        assertEquals(ordering.getStatus(), resultOrdering.getStatus());
    }

    @Test
    public void manageOrderingTest() {
        Long orderId = 1L;
        OrderingDto orderingDto = new OrderingDto();
        orderingDto.setStatus(OrderingStatus.COMPLETED);

        Ordering existingOrdering = new Ordering();
        existingOrdering.setId(orderId);
        existingOrdering.setStatus(OrderingStatus.PENDING);

        Ordering updatedOrdering = new Ordering();
        updatedOrdering.setId(orderId);
        updatedOrdering.setStatus(OrderingStatus.COMPLETED);

        OrderingRepository orderingRepository = mock(OrderingRepository.class);
        when(orderingRepository.findById(orderId)).thenReturn(Optional.of(existingOrdering));
        when(orderingRepository.save(existingOrdering)).thenReturn(updatedOrdering);

        OrderingService orderingService = new OrderingService(orderingRepository, clientService, mealService, mealRepository, clientRepository, menuRepository);

        OrderingDto result = orderingService.manageOrderStatus(orderId, orderingDto);

        assertEquals(orderingDto.getStatus(), result.getStatus());
        verify(orderingRepository, times(1)).findById(orderId);
        verify(orderingRepository, times(1)).save(existingOrdering);
    }

    @Test
    public void deleteOrderingTest() {
        Long orderId = 1L;

        OrderingRepository orderingRepository = mock(OrderingRepository.class);
        OrderingService orderingService = new OrderingService(orderingRepository, clientService, mealService, mealRepository, clientRepository, menuRepository);

        orderingService.deleteOrdering(orderId);

        verify(orderingRepository, times(1)).deleteById(orderId);
    }
}
