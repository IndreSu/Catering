package com.exam.Catering.ordering;

public class OrderingMapper {

    public static Ordering toOrdering(OrderingDto orderingDto) {

        Ordering ordering = new Ordering();
        ordering.setStatus(ordering.getStatus());

        return ordering;
    }

    public static OrderingDto toOrderingDto(Ordering ordering){

        OrderingDto orderingDto = new OrderingDto();
        orderingDto.setStatus(ordering.getStatus());

        return orderingDto;
    }
}
