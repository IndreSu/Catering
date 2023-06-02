package com.exam.Catering.controllersTests;

import com.exam.Catering.meal.*;
import com.exam.Catering.menu.Menu;
import com.exam.Catering.menu.MenuController;
import com.exam.Catering.menu.MenuDto;
import com.exam.Catering.menu.MenuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MenuControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @InjectMocks
    MenuController menuController;

    @Mock
    MenuService menuService;

    @Test
    public void menuDtoTest() {
        MenuDto menu = new MenuDto(1L, "Breakfast Menu");

        assertEquals(1L, menu.getId());
        assertEquals("Breakfast Menu", menu.getTitle());

        menu.setId(2L);
        menu.setTitle("Lunch Menu");
        assertEquals(2L, menu.getId());
        assertEquals("Lunch Menu", menu.getTitle());
    }

    @Test
    public void getAllMenusWithMealsTest(){

        MenuDto menuDto = new MenuDto();
        menuDto.setTitle("Breakfast Menu");

        MenuDto createdMenu = new MenuDto();
        createdMenu.setId(1L);
        createdMenu.setTitle("Breakfast Menu");

        when(menuService.addMenu(menuDto)).thenReturn(createdMenu);

        MenuController menuController = new MenuController(menuService);

        ResponseEntity<MenuDto> responseEntity = menuController.addMenu(menuDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdMenu, responseEntity.getBody());
    }

    @Test
    public void addMenuTest() {

        MenuDto menuDto = new MenuDto(3L, "Dinner Menu");
        MenuDto createdMenu = new MenuDto(3L, "Dinner Menu");

        when(menuService.addMenu(menuDto)).thenReturn(createdMenu);

        MenuController menuController = new MenuController(menuService);
        menuController.getAllMenusWithMeals();

        ResponseEntity<MenuDto> response = menuController.addMenu(menuDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdMenu, response.getBody());
    }

    @Test
    public void updateMenuTest() {

        Long menuId = 2L;

        MenuDto menuDto = new MenuDto(2l, "Lunch Menu");
        MenuDto updatedMenu = new MenuDto(2L, "Brunch Menu");

        when(menuService.updateMenu(menuId, menuDto)).thenReturn(updatedMenu);

        ResponseEntity<MenuDto> response = menuController.updateMenu(menuId, menuDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedMenu, response.getBody());
    }

    @Test
    public void deleteMenuTest() {

        Long menuId = 1L;

        ResponseEntity<Void> response = menuController.deleteMenu(menuId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(menuService).deleteMenu(menuId);
    }




}
