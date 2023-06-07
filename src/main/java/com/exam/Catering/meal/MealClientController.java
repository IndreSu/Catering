package com.exam.Catering.meal;

import com.exam.Catering.menu.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("client/api/v1/meals")
public class MealClientController {

    private final MealService mealService;
    private final MenuRepository menuRepository;

    @Autowired
    public MealClientController(MealService mealService, MenuRepository menuRepository) {

        this.mealService = mealService;
        this.menuRepository = menuRepository;
    }

    @GetMapping (produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<MealDto>> getAllMeals() {
        List<MealDto> mealList = mealService.getAllMeals();
        return new ResponseEntity<>(mealList, HttpStatus.OK);
    }
}
