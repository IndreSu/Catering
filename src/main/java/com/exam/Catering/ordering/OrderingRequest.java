package com.exam.Catering.ordering;

import lombok.Data;

import java.util.Map;

@Data
public class OrderingRequest{

//    private Long clientId;

    private Long clientId;

    private Map<Long, Integer> mealQuantities;

}
