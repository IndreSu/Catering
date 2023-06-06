package com.exam.Catering.servisesTests;

import com.exam.Catering.meal.Meal;
import com.exam.Catering.meal.MealDto;
import com.exam.Catering.meal.MealRepository;
import com.exam.Catering.meal.MealService;
import com.exam.Catering.menu.Menu;
import com.exam.Catering.menu.MenuRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class MealServiceTests {

    @Mock
    Meal meal;

    @Mock
    MealDto mealDto;

    @InjectMocks
    MealService mealService;

    @Mock
    MealRepository mealRepository;

    @Mock
    MenuRepository menuRepository;

    @Test
    public void getAllMealsTest() {
        mealService.getAllMeals();
        verify(mealRepository).findAll();
    }

    public static final long Id = 1;
    @Test
    public void viewMealByIdTest(){
        mealService.getById(Id);
        verify(mealRepository).findById(Id);
    }

    @Test
    public void addMeal() {

        MockitoAnnotations.openMocks(this);
        mealService = new MealService(mealRepository, menuRepository);

        Menu menu = mock(Menu.class);
        MealDto mealDto = mock(MealDto.class);
        Meal meal = mock(Meal.class);

        when(menuRepository.findById(any(Long.class))).thenReturn(Optional.of(menu));
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);

        mealService.addMeal(1L, mealDto);

        verify(menuRepository).findById(1L);
        verify(mealRepository).save(any(Meal.class));
    }

    @Test
    public void updateMealTest() {

        MockitoAnnotations.openMocks(this);
        MealService mealService = new MealService(mealRepository, menuRepository);

        Long mealId = 1L;
        MealDto mealDto = new MealDto();
        mealDto.setTitle("Pancakes");
        mealDto.setDescription("With strawberries");
        mealDto.setQuantity(1);

        Meal existingMeal = new Meal();
        existingMeal.setId(mealId);

        when(mealRepository.findById(mealId)).thenReturn(Optional.of(existingMeal));
        when(mealRepository.save(any(Meal.class))).thenReturn(existingMeal);

        MealDto updatedMealDto = mealService.updateMeal(mealId, mealDto);

        verify(mealRepository).findById(mealId);
        verify(mealRepository).save(any(Meal.class));

        assertEquals(mealDto.getTitle(), updatedMealDto.getTitle());
        assertEquals(mealDto.getDescription(), updatedMealDto.getDescription());
        assertEquals(mealDto.getQuantity(), updatedMealDto.getQuantity());
    }

    @Test
    public void testDeleteMeal() {
        MockitoAnnotations.openMocks(this);
        MealService mealService = new MealService(mealRepository, menuRepository);

        Long mealId = 1L;
        mealService.deleteMeal(mealId);
        verify(mealRepository).deleteById(mealId);
    }
}
