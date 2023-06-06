package com.exam.Catering.ordering;

//import com.exam.Catering.client.ClientRepository;
import com.exam.Catering.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("client/api/v1/orderings")
public class OrderingClientController {

    private final OrderingService orderingService;
    private final UsersRepository userRepository;

    @Autowired
    public OrderingClientController(OrderingService orderingService, UsersRepository userRepository) {
        this.orderingService = orderingService;
        this.userRepository = userRepository;
    }

    @PostMapping("/make")
    public ResponseEntity<Ordering> makeOrdering(@RequestBody OrderingRequest request) {
        Ordering ordering = orderingService.makeOrdering(request.getClientId(), request.getMealQuantities());
        return ResponseEntity.ok(ordering);
    }
}

//in postman url: http://localhost:8080/client/api/v1/orderings/make, body:
// {
//  "clientId": 3,
//  "mealQuantities": {
//    "2": 1,
//    "3": 3
//  }
//}
