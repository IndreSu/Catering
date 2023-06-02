package com.exam.Catering.ordering;

import com.exam.Catering.client.ClientDto;
import com.exam.Catering.meal.MealDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderingDto {

//    private ClientDto client;
//
//    private List<MealDto> meals;

    private OrderingStatus status;
}
