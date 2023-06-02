package com.exam.Catering.menu;


public class MenuMapper {

    public static Menu toMenu(MenuDto menuDto) {

        Menu menu = new Menu();
        menu.setTitle(menuDto.getTitle());

        return menu;
    }

    public static MenuDto toMenuDto(Menu menu){

        MenuDto menuDto = new MenuDto();
        menuDto.setTitle(menu.getTitle());

        return menuDto;
    }
}
