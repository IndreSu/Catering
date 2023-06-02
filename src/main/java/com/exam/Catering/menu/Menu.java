package com.exam.Catering.menu;

import com.exam.Catering.meal.Meal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="MENU")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "menu")
//    @JoinColumn(name = "meal_id")
//    @JsonIgnore // Add this annotation to ignore the meals field during serialization
    @JsonIgnoreProperties("menu") // Exclude the meals field during serialization
    private List<Meal> meals;

}
