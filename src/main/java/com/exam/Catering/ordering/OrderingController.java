package com.exam.Catering.ordering;

import com.exam.Catering.users.UsersRepository;
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
//    private final ClientRepository clientRepository;
    private final UsersRepository userRepository;

    @Autowired
    public OrderingController(OrderingService orderingService,
                              UsersRepository usersRepository) {
        this.orderingService = orderingService;
//        this.clientRepository = clientRepository;
        this.userRepository = usersRepository;
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
