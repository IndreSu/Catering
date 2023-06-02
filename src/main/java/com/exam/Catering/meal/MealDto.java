package com.exam.Catering.meal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDto {

    private String title;

    private String description;

    private Integer quantity;
}
