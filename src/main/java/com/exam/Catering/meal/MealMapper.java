package com.exam.Catering.meal;

import com.exam.Catering.client.Client;
import com.exam.Catering.client.ClientDto;

public class MealMapper {

    public static Meal toMeal(MealDto mealDto) {

        Meal meal = new Meal();
        meal.setTitle(mealDto.getTitle());
        meal.setDescription(mealDto.getDescription());
        meal.setQuantity(mealDto.getQuantity());

        return meal;
    }

    public static MealDto toMealDto(Meal meal){

       MealDto mealDto = new MealDto();
       mealDto.setTitle(meal.getTitle());
       mealDto.setDescription(meal.getDescription());
       mealDto.setQuantity(meal.getQuantity());

       return mealDto;
    }
}
