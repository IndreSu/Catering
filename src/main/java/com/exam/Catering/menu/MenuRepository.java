package com.exam.Catering.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query("SELECT DISTINCT m FROM Menu m LEFT JOIN FETCH m.meals")
    List<Menu> findAllWithMeals();

}
