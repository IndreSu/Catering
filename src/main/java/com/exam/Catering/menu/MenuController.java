package com.exam.Catering.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("api/v1/menus")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

//    @GetMapping
//    public ResponseEntity<List<Menu>> getAllMenus() {
//        List<Menu> menus = menuService.getAllMenus();
//        return new ResponseEntity<>(menus, HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenusWithMeals() {
        List<Menu> menus = menuService.getAllMenusWithMeals();
        return ResponseEntity.ok(menus);
    }

    @PostMapping
    public ResponseEntity<MenuDto> addMenu(@RequestBody MenuDto menuDto) {
        MenuDto createdMenu = menuService.addMenu(menuDto);
        return new ResponseEntity<>(createdMenu, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuDto> updateMenu(@PathVariable("id") Long id, @RequestBody MenuDto menuDto) {
        MenuDto updatedMenu = menuService.updateMenu(id, menuDto);
        return new ResponseEntity<>(updatedMenu, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable("id") Long id) {
        menuService.deleteMenu(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
