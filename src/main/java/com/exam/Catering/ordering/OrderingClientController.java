package com.exam.Catering.ordering;

import com.exam.Catering.client.ClientRepository;
import com.exam.Catering.meal.MealDto;
import com.exam.Catering.meal.MealService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("client/api/v1/orderings")
public class OrderingClientController {

    private final OrderingService orderingService;
    private final ClientRepository clientRepository;

    @Autowired
    public OrderingClientController(OrderingService orderingService,
                              ClientRepository clientRepository) {
        this.orderingService = orderingService;
        this.clientRepository = clientRepository;
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

//    @PostMapping("/make")
//    public ResponseEntity<Ordering> makeOrdering(@RequestParam("clientId") Long clientId, @RequestBody List<Long> mealIds) {
//        Ordering createdOrdering = orderingService.makeOrdering(clientId, mealIds);
//        return new ResponseEntity<>(createdOrdering, HttpStatus.CREATED);
//    }

    //in postman url:http://localhost:8080/client/api/v1/orderings/make?clientId=1; body raw json: [1, 2, 3]. by default (set in ordering status is pending for new orders)

