package com.exam.Catering.meal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDto {

    private Long id;

    private String title;

    private String description;

    private Integer quantity;
}
