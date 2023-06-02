package com.exam.Catering.ordering;

import com.exam.Catering.client.Client;
import com.exam.Catering.client.ClientRepository;
import com.exam.Catering.client.ClientService;
import com.exam.Catering.meal.*;
import com.exam.Catering.menu.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Ordering makeOrdering(Long clientId, List<Long> mealIds) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found with ID: " + clientId));

        List<Meal> meals = mealRepository.findAllById(mealIds);

        Ordering ordering = new Ordering();
        ordering.setClient(client);
        ordering.setMeals(meals);
        ordering.setStatus(OrderingStatus.PENDING);

        return orderingRepository.save(ordering);
    }

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
