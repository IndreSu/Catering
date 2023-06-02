package com.exam.Catering.controllersTests;

import com.exam.Catering.meal.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MealControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @InjectMocks
    MealController mealController;

    @Mock
    MealService mealService;

    @Test
    public void mealDtoTest() {
        MealDto meal = new MealDto("Pancakes", "With strawberries", 1);

        assertEquals("Pancakes", meal.getTitle());
        assertEquals("With strawberries", meal.getDescription());
        assertEquals(1, meal.getQuantity());

        meal.setTitle("Hamburger");
        meal.setDescription("With chicken meat");
        meal.setQuantity(1);
        assertEquals("Hamburger", meal.getTitle());
        assertEquals("With chicken meat", meal.getDescription());
        assertEquals(1, meal.getQuantity());
    }

    @Test
    public void getAllMealsTest(){
        Meal meal = new Meal();
        List<Meal> meals = new ArrayList<>();
        meals.add(meal);
        when(mealService.getAllMeals()).thenReturn(meals.stream().map(MealMapper::toMealDto).collect(Collectors.toList()));
        assertEquals(mealController.getAllMeals().getBody().size(), meals.size());
    }

    @Test
    public void viewMealByIdTest() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/meals/1")
        ).andExpect(status().isOk()).andReturn();
        MealDto result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<MealDto>() {
        });
        Assertions.assertEquals(result.getTitle(), "Pancakes","Get meal by Id should return meal with correct title");
    }

    @Test
    public void addMealTest() {

        Long menuId = 1L;
        MealDto mealDto = new MealDto("Pancakes", "With strawberries", 1);
        MealDto createdMeal = new MealDto("Pancakes", "With strawberries", 1);

        when(mealService.addMeal(menuId, mealDto)).thenReturn(createdMeal);

        ResponseEntity<MealDto> response = mealController.addMeal(menuId, mealDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdMeal, response.getBody());
    }

    @Test
    public void updateMealTest() {

        Long mealId = 1L;
        MealDto mealDto = new MealDto("Pancakes", "With strawberries", 1);
        MealDto updatedMeal = new MealDto("Pancakes", "With blueberries", 1);

        when(mealService.updateMeal(mealId, mealDto)).thenReturn(updatedMeal);

        ResponseEntity<MealDto> response = mealController.updateMeal(mealId, mealDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedMeal, response.getBody());
    }

    @Test
    public void deleteMealTest() {

        Long mealId = 1L;

        ResponseEntity<Void> response = mealController.deleteMeal(mealId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(mealService).deleteMeal(mealId);
    }
}
