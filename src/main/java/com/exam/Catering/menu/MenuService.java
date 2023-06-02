package com.exam.Catering.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

        private final MenuRepository menuRepository;

        @Autowired
        public MenuService(MenuRepository menuRepository) {

            this.menuRepository = menuRepository;
        }

//        public List<Menu> getAllMenus() {
//            List<Menu> menus = menuRepository.findAll();
//            return menus;
//        }

    public List<Menu> getAllMenusWithMeals() {

            return menuRepository.findAllWithMeals();
    }


    public MenuDto addMenu(MenuDto menuDto) {
        Menu menu = MenuMapper.toMenu(menuDto);
        Menu savedMenu = menuRepository.save(menu);
        return MenuMapper.toMenuDto(savedMenu);
    }

    public MenuDto updateMenu(Long id, MenuDto menuDto) {
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found with id: " + id));

        existingMenu.setTitle(menuDto.getTitle());

        Menu updatedMenu = menuRepository.save(existingMenu);
        return MenuMapper.toMenuDto(updatedMenu);
    }

    public void deleteMenu(Long id) {

            menuRepository.deleteById(id);
    }
}
