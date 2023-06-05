package com.exam.Catering.ordering;

import com.exam.Catering.client.Client;
import com.exam.Catering.client.ClientRepository;
import com.exam.Catering.client.ClientService;
import com.exam.Catering.meal.*;
import com.exam.Catering.menu.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
public class OrderingService {

    private final OrderingRepository orderingRepository;
    private final ClientService clientService;
    private final MealService mealService;
    private final MealRepository mealRepository;
    private final ClientRepository clientRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public OrderingService(OrderingRepository orderingRepository,
                           ClientService clientService,
                           MealService mealService,
                           MealRepository mealRepository,
                           ClientRepository clientRepository,
                           MenuRepository menuRepository) {

        this.orderingRepository = orderingRepository;
        this.clientService = clientService;
        this.mealService = mealService;
        this.mealRepository = mealRepository;
        this.clientRepository = clientRepository;
        this.menuRepository = menuRepository;
    }

    public List<Ordering> getAllOrderings() {
        List<Ordering> orderings = orderingRepository.findAll();
        return orderings;
    }

    public Optional<Ordering> getById(Long id) {

        return orderingRepository.findById(id);
    }

    @Transactional
    public Ordering makeOrdering(Long clientId, Map<Long, Integer> mealQuantities) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        Ordering ordering = new Ordering();

        List<OrderedMeal> orderedMeals = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : mealQuantities.entrySet()) {
            Long mealId = entry.getKey();
            Integer quantity = entry.getValue();

            Meal meal = mealRepository.findById(mealId)
                    .orElseThrow(() -> new IllegalArgumentException("Meal not found"));

            OrderedMeal orderedMeal = new OrderedMeal();
            orderedMeal.setMeal(meal);
            orderedMeal.setQuantity(quantity);
            orderedMeal.setOrdering(ordering);
            orderedMeals.add(orderedMeal);
        }

        ordering.setClient(client);
        ordering.setOrderedMeals(orderedMeals);
        ordering.setStatus(OrderingStatus.PENDING);

        return orderingRepository.save(ordering);
    }



//    @Transactional
//    public OrderingDto makeOrdering(Long clientId, List<MealDto> mealDtoList) {
//        Client client = clientRepository.findById(clientId)
//                .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + clientId));
//
//        Ordering ordering = new Ordering();
//        ordering.setClient(client);
//        ordering.setMeals(new ArrayList<>());
//
//        for (MealDto mealDto : mealDtoList) {
//            Meal meal = mealRepository.findById(mealDto.getId())
//                    .orElseThrow(() -> new IllegalArgumentException("Meal not found with id: " + mealDto.getId()));
//
//            meal.setQuantity(mealDto.getQuantity());
//
//            ordering.getMeals().add(meal);
//        }
//
//        ordering.setStatus(OrderingStatus.PENDING);
//
//        Ordering savedOrdering = orderingRepository.save(ordering);
//        return OrderingMapper.toOrderingDto(savedOrdering);
//    }

    public OrderingDto manageOrderStatus(Long id, OrderingDto orderingDto) { //update order status
        Ordering existingOrdering = orderingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ordering not found with id: " + id));

        existingOrdering.setStatus(orderingDto.getStatus());

        Ordering updatedOrdering = orderingRepository.save(existingOrdering);
        return OrderingMapper.toOrderingDto(updatedOrdering);
    }

    public void deleteOrdering(Long id) {
        orderingRepository.deleteById(id);
    }
}
