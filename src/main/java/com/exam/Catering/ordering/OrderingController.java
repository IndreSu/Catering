package com.exam.Catering.ordering;

import com.exam.Catering.client.Client;
import com.exam.Catering.client.ClientRepository;
import com.exam.Catering.meal.MealDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("api/v1/orderings")
public class OrderingController {
    private final OrderingService orderingService;
    private final ClientRepository clientRepository;

    @Autowired
    public OrderingController(OrderingService orderingService,
                              ClientRepository clientRepository) {
        this.orderingService = orderingService;
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public ResponseEntity<List<Ordering>> getAllOrderings() {
        List<Ordering> orderingsList = orderingService.getAllOrderings();
        return new ResponseEntity<>(orderingsList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Optional<Ordering> getOrder(@PathVariable Long id) {

        return orderingService.getById(id);
    }

    @PostMapping("/make")
    public ResponseEntity<Ordering> makeOrdering(@RequestParam("clientId") Long clientId, @RequestBody List<Long> mealIds) {
        Ordering createdOrdering = orderingService.makeOrdering(clientId, mealIds);
        return new ResponseEntity<>(createdOrdering, HttpStatus.CREATED);
    } //in postman url:http://localhost:8080/api/v1/orderings/make?clientId=1; body raw json: [1, 2, 3]. by default (set in ordering status is pending for new orders)

    @PutMapping("/{id}")
    public ResponseEntity<OrderingDto> manageOrderStatus(@PathVariable("id") Long id, @RequestBody OrderingDto orderingDto) { //update order status
        OrderingDto updatedOrdering = orderingService.manageOrderStatus(id, orderingDto);
        return new ResponseEntity<>(updatedOrdering, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdering(@PathVariable("id") Long id) {
        orderingService.deleteOrdering(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}