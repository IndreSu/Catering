package com.exam.Catering.servisesTests;

import com.exam.Catering.meal.MealRepository;
import com.exam.Catering.menu.Menu;
import com.exam.Catering.menu.MenuDto;
import com.exam.Catering.menu.MenuRepository;
import com.exam.Catering.menu.MenuService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
public class MenuServiceTests {

    @Mock
    MenuDto menuDto;

    @InjectMocks
    MenuService menuService;

    @Mock
    MenuRepository menuRepository;

    @Mock
    MealRepository mealRepository;

    @Test
    public void getAllMenusWithMealsTest() {
        menuService.getAllMenusWithMeals();
        verify(menuRepository).findAllWithMeals();
    }

    @Test
    public void addMenu() {

        MenuDto menuDto = new MenuDto();
        menuDto.setTitle("Breakfast Menu");

        Menu menu = new Menu();
        menu.setTitle(menuDto.getTitle());

        Menu savedMenu = new Menu();
        savedMenu.setId(1L);
        savedMenu.setTitle(menuDto.getTitle());

        when(menuRepository.save(any(Menu.class))).thenReturn(savedMenu);

        MenuDto resultDto = menuService.addMenu(menuDto);

        verify(menuRepository).save(menu);
        assertEquals(savedMenu.getTitle(), resultDto.getTitle());
    }

    @Test
    public void updateMealTest() {

        MenuDto menuDto = new MenuDto();
        menuDto.setId(1L);
        menuDto.setTitle("Updated Menu");

        Menu existingMenu = new Menu();
        existingMenu.setId(menuDto.getId());
        existingMenu.setTitle("Breakfast Menu");

        Menu updatedMenu = new Menu();
        updatedMenu.setId(menuDto.getId());
        updatedMenu.setTitle(menuDto.getTitle());

        when(menuRepository.findById(menuDto.getId())).thenReturn(Optional.of(existingMenu));
        when(menuRepository.save(any(Menu.class))).thenReturn(updatedMenu);

        MenuDto resultDto = menuService.updateMenu(menuDto.getId(), menuDto);

        verify(menuRepository).findById(menuDto.getId());
        verify(menuRepository).save(existingMenu);

        assertEquals(updatedMenu.getTitle(), resultDto.getTitle());
    }

    @Test
    public void testDeleteMeal() {

            Long menuId = 1L;
            menuService.deleteMenu(menuId);
            verify(menuRepository).deleteById(menuId);
        }
}
