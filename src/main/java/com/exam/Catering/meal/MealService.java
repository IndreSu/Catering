package com.exam.Catering.meal;

import com.exam.Catering.client.Client;
import com.exam.Catering.menu.Menu;
import com.exam.Catering.menu.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public MealService(MealRepository mealRepository, MenuRepository menuRepository) {
        this.mealRepository = mealRepository;
        this.menuRepository = menuRepository;
    }

    public List<MealDto> getAllMeals() {
        List<Meal> mealList = mealRepository.findAll();
        return mealList.stream()
                .map(MealMapper::toMealDto)
                .collect(Collectors.toList());
    }

    public Optional<Meal> getById(Long mealId) {

        return mealRepository.findById(mealId);
    }

    public MealDto addMeal(Long menuId, MealDto mealDto) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found with id: " + menuId));

        Meal meal = MealMapper.toMeal(mealDto);
        meal.setMenu(menu);

        meal = mealRepository.save(meal);

        return MealMapper.toMealDto(meal);
    }

    public MealDto updateMeal(Long id, MealDto mealDto) {
        Meal existingMeal = mealRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meal not found with id: " + id));

        existingMeal.setTitle(mealDto.getTitle());
        existingMeal.setDescription(mealDto.getDescription());
        existingMeal.setQuantity(mealDto.getQuantity());

        Meal updatedMeal = mealRepository.save(existingMeal);
        return MealMapper.toMealDto(updatedMeal);
    }

    public void deleteMeal(Long id) {

        mealRepository.deleteById(id);
    }
}
