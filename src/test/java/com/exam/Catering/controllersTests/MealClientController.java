package com.exam.Catering.controllersTests;

import com.exam.Catering.meal.Meal;
import com.exam.Catering.meal.MealController;
import com.exam.Catering.meal.MealMapper;
import com.exam.Catering.meal.MealService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MealClientController {

    @InjectMocks
    MealController mealController;

    @Mock
    MealService mealService;

    @Test
    public void getAllMealsTest(){
        Meal meal = new Meal();
        List<Meal> meals = new ArrayList<>();
        meals.add(meal);
        when(mealService.getAllMeals()).thenReturn(meals.stream().map(MealMapper::toMealDto).collect(Collectors.toList()));
        assertEquals(mealController.getAllMeals().getBody().size(), meals.size());
    }
}
