package com.exam.Catering.servisesTests;

import com.exam.Catering.meal.Meal;
import com.exam.Catering.meal.MealDto;
import com.exam.Catering.meal.MealRepository;
import com.exam.Catering.meal.MealService;
import com.exam.Catering.menu.MenuRepository;
import com.exam.Catering.ordering.*;
import com.exam.Catering.users.Users;
import com.exam.Catering.users.UsersRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;

import static com.exam.Catering.security.ApplicationUserRole.CLIENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import java.util.Optional;

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
    UsersRepository usersRepository;

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

        Long clientId = 1L;
        Map<Long, Integer> mealQuantities = new HashMap<>();
        mealQuantities.put(1L, 2);
        mealQuantities.put(2L, 1);

        Users client = new Users();
        client.setId(clientId);
        client.setUsername("John");
        client.setRole(CLIENT);

        Meal meal1 = new Meal();
        meal1.setId(1L);
        meal1.setTitle("Pancakes");

        Meal meal2 = new Meal();
        meal2.setId(2L);
        meal2.setTitle("Hamburger");

        // Mock repository calls
        when(usersRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal1));
        when(mealRepository.findById(2L)).thenReturn(Optional.of(meal2));
        when(orderingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the method under test
        Ordering result = orderingService.makeOrdering(clientId, mealQuantities);

        // Verify the behavior and assertions
        verify(usersRepository).findById(clientId);
        verify(mealRepository).findById(1L);
        verify(mealRepository).findById(2L);
        verify(orderingRepository).save(any(Ordering.class));

        assertNotNull(result);
        assertEquals(client, result.getClient());
        assertEquals(2, result.getOrderedMeals().size());

        OrderedMeal orderedMeal1 = result.getOrderedMeals().get(0);
        assertEquals(meal1, orderedMeal1.getMeal());
        assertEquals(2, orderedMeal1.getQuantity());
        assertEquals(result, orderedMeal1.getOrdering());

        OrderedMeal orderedMeal2 = result.getOrderedMeals().get(1);
        assertEquals(meal2, orderedMeal2.getMeal());
        assertEquals(1, orderedMeal2.getQuantity());
        assertEquals(result, orderedMeal2.getOrdering());

        assertEquals(OrderingStatus.PENDING, result.getStatus());
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

        OrderingService orderingService = new OrderingService(orderingRepository, mealService, mealRepository, menuRepository, usersRepository);

        OrderingDto result = orderingService.manageOrderStatus(orderId, orderingDto);

        assertEquals(orderingDto.getStatus(), result.getStatus());
        verify(orderingRepository, times(1)).findById(orderId);
        verify(orderingRepository, times(1)).save(existingOrdering);
    }

    @Test
    public void deleteOrderingTest() {
        Long orderId = 1L;

        OrderingRepository orderingRepository = mock(OrderingRepository.class);
        OrderingService orderingService = new OrderingService(orderingRepository, mealService, mealRepository, menuRepository, usersRepository);

        orderingService.deleteOrdering(orderId);

        verify(orderingRepository, times(1)).deleteById(orderId);
    }
}
