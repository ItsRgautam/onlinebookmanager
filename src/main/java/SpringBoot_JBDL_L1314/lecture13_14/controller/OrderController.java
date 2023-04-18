package SpringBoot_JBDL_L1314.lecture13_14.controller;

import SpringBoot_JBDL_L1314.lecture13_14.commons.ResponseGenerator;
import SpringBoot_JBDL_L1314.lecture13_14.models.Orders;
import SpringBoot_JBDL_L1314.lecture13_14.requests.CreateBookRequestDto;
import SpringBoot_JBDL_L1314.lecture13_14.requests.CreateOrderRequestDto;
import SpringBoot_JBDL_L1314.lecture13_14.requests.UpdateOrderRequestDto;
import SpringBoot_JBDL_L1314.lecture13_14.service.OrderService;
import lombok.extern.slf4j.Slf4j;
//import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ResponseGenerator responseGenerator;

    @PostMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequestDto order){
        log.info("Request Received {} " , order);
        return new ResponseEntity<> (orderService.createOrder(order), HttpStatus.CREATED);
    }

    @PutMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateOrder(@Valid @RequestBody UpdateOrderRequestDto order){
        log.info("Request Received {} " , order);
        return new ResponseEntity<> (orderService.updateOrder(order), HttpStatus.OK);
    }

    @GetMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOrder(@RequestParam(value = "orderId") String orderRefence){
        return new ResponseEntity<> (orderService.fetchById(orderRefence), HttpStatus.OK);
    }


    @PostMapping(value = "/issue/order", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orders> issueBook(@RequestParam(value = "bookId") Integer bookId,
                                            @RequestParam(value = "userId") Integer userId){
        log.info("Request Received {} {} " , bookId, userId);
        return new ResponseEntity<Orders>(orderService.placeOrder(userId, bookId), HttpStatus.CREATED);
    }

    @PostMapping(value = "/submit", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orders> submitBook(@RequestParam(value = "bookId") Integer bookId,
                                            @RequestParam(value = "userId") Integer userId){
        log.info("Request Received {} {} " , bookId, userId);
        return new ResponseEntity<Orders>(orderService.submitBook(userId, bookId), HttpStatus.ACCEPTED);
    }


}
