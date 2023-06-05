package com.exam.Catering.meal;

import com.exam.Catering.client.Client;
import com.exam.Catering.menu.Menu;
import com.exam.Catering.menu.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("api/v1/meals")
public class MealController {

    private final MealService mealService;
    private final MenuRepository menuRepository;

    @Autowired
    public MealController(MealService mealService, MenuRepository menuRepository) {

        this.mealService = mealService;
        this.menuRepository = menuRepository;
    }

    @GetMapping
    public ResponseEntity<List<MealDto>> getAllMeals() {
        List<MealDto> mealList = mealService.getAllMeals();
        return new ResponseEntity<>(mealList, HttpStatus.OK);
    }

    @GetMapping("/{mealId}")
    public Optional<Meal> getMeal(@PathVariable Long mealId) {

        return mealService.getById(mealId);
    }

    @PostMapping
    public ResponseEntity<MealDto> addMeal(@RequestParam("menuId") Long menuId, @RequestBody MealDto mealDto) {
        MealDto createdMeal = mealService.addMeal(menuId, mealDto);
        return new ResponseEntity<>(createdMeal, HttpStatus.CREATED);
    }

    @PutMapping("/{mealId}")
    public ResponseEntity<MealDto> updateMeal(@PathVariable("mealId") Long mealId, @RequestBody MealDto mealDto) {
        MealDto updatedMeal = mealService.updateMeal(mealId, mealDto);
        return new ResponseEntity<>(updatedMeal, HttpStatus.OK);
    }

    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(@PathVariable("mealId") Long mealId) {
        mealService.deleteMeal(mealId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
