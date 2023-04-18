package SpringBoot_JBDL_L1314.lecture13_14.service;

import SpringBoot_JBDL_L1314.lecture13_14.config.UserInfoConfig;
import SpringBoot_JBDL_L1314.lecture13_14.exceptions.BookException;
import SpringBoot_JBDL_L1314.lecture13_14.exceptions.DueDateExceedException;
import SpringBoot_JBDL_L1314.lecture13_14.exceptions.OrderNotFoundException;
import SpringBoot_JBDL_L1314.lecture13_14.exceptions.UserException;
import SpringBoot_JBDL_L1314.lecture13_14.models.*;
import SpringBoot_JBDL_L1314.lecture13_14.repository.OrderRepository;
import SpringBoot_JBDL_L1314.lecture13_14.requests.CreateOrderRequestDto;
import SpringBoot_JBDL_L1314.lecture13_14.requests.UpdateOrderRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private static Logger log= LoggerFactory.getLogger(BookService.class);
    @Autowired
    OrderRepository repository;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    UserInfoConfig userInfoConfig;

    public Orders createOrder(CreateOrderRequestDto orderRequestDto){
        return saveOrUpdate(orderRequestDto.toOrder());
    }


    public Orders saveOrUpdate(Orders order){
        return repository.save(order);
    }

    public Orders updateOrder(UpdateOrderRequestDto order){
        Optional<Orders> byOrderReference = repository.findByOrderReference(order.getOrderReferenceId());
        if(byOrderReference.isEmpty()){
            throw new OrderNotFoundException(StatusCode.CHEGG_0N);
        }

        Orders existingOrder = byOrderReference.get();
        existingOrder.setOrderStatus(OrderStatus.valueOf(order.getOrderStatus()));
        existingOrder.setAmount(order.getAmount());

        return saveOrUpdate(existingOrder);
    }

    /**
     *
     * {@link  jakarta.transaction.Transaction}
     *
     * @param userId
     * @param bookId
     * @return
     */

    public Orders placeOrder(Integer userId , Integer bookId){
        /**
         * issue a book to a user
         * 1) Validate the user - check if user exists
         * 2) validate the book - check if book exists and book is not issued
         * 3) check if student has reached a quota for books (restrict b2b sellers - meesho)
         * 4) Order with a pending state;
         * 5) mark the book unavailable and issue that to the user
         * 6) update the order with success status
         */
        /**
         * 1
         */
        Optional<UserInfo> userInfo = userService.fetchOneById(userId);
        if(userInfo.isEmpty()){
            throw new UserException(StatusCode.CHEGG_02);
        }
        UserInfo transactingUser = userInfo.get();
        Optional<List<Orders>> pendingOrdersList=repository.findPendingOrders(transactingUser,SubmissionStatus.PENDING,LocalDate.now());
        List<Orders> pendingOrdersAfterDueDate = pendingOrdersList.get();

        if(!pendingOrdersAfterDueDate.isEmpty()){
           log.info("some Books are due__________________________________________________________________");
            throw new DueDateExceedException();
        }
        /**
         * 2
         */
        Optional<Book> book =    bookService.findById(bookId);
        if(book.isEmpty()){
            throw new BookException(StatusCode.CHEGG_01);
        }
        Book catalogueBook= book.get();
        if(catalogueBook.getBookStatus() == BookStatus.UNAVAILABLE){
            throw new BookException(StatusCode.CHEGG_03);
        }
        /**
         * 3
         */

        if(transactingUser.getOrdersList().size() >= userInfoConfig.getBookQuota()){
            throw new UserException(StatusCode.CHEGG_04);
        }
        /**
         * 4) Order with a pending state;
         * 5) mark the book unavailable and issue that to the user
         * 6) update the order with success status
         * */
        Orders orders = Orders.builder()
                .amount((catalogueBook.getCost())*(0.20))
                .associatedBook(catalogueBook)
                .orderStatus(OrderStatus.PENDING)
                .orderDate( LocalDate.now())
                .dueDate(LocalDate.now().plusMonths(2))
                .orderReference(UUID.randomUUID().toString())
                .submitStatus(SubmissionStatus.PENDING)
                .user(transactingUser)
                .fine(0)
                .build();
        try {


            saveOrUpdate(orders);

            catalogueBook.setBookStatus(BookStatus.UNAVAILABLE);
            catalogueBook.setUser(transactingUser);
            bookService.saveOrUpdate(catalogueBook);


            orders.setOrderStatus(OrderStatus.SUCCESS);
            saveOrUpdate(orders);

        } catch (Exception exception){
            // rollback
            catalogueBook.setBookStatus(BookStatus.AVAILABLE);
            catalogueBook.setUser(null);
            bookService.saveOrUpdate(catalogueBook);
            // order has been persisted
            if(Objects.nonNull(orders.getId())){
                orders.setOrderStatus(OrderStatus.FAILED);
                saveOrUpdate(orders);
            }
        }
        return  orders;
    }






    public Orders submitBook(Integer userId , Integer bookId){
        /**
         * submit a book
         * problem is that i cant submit the book if i issued it more than once to same user beacuse while searching for
            issuingorder we will get 2 or more orders with same userId and bookId
         * 1) Validate the user - check if user exists
         * 2) validate the book - check if book exists and book should be issued to transacting user
         * 3) get the order associated with issuing of book to user and check for late submission
         * 4) Order with a pending state;
         * 5) mark the book available and remove user from book
         * 6) update the order with success status and submitted book status
         */
        /**
         * 1
         */
        Optional<UserInfo> userInfo = userService.fetchOneById(userId);
        if(userInfo.isEmpty()){
            throw new UserException(StatusCode.CHEGG_02);
        }
        /**
         * 2
         */
        UserInfo transactingUser = userInfo.get();

        Optional<Book> book =    bookService.findById(bookId);
        if(book.isEmpty()){
            throw new BookException(StatusCode.CHEGG_01);
        }
        Book catalogueBook= book.get();
        if(catalogueBook.getBookStatus() == BookStatus.AVAILABLE){
            throw new BookException(StatusCode.CHEGG_05);
        }
        if(catalogueBook.getUser() != transactingUser){
            throw new UserException(StatusCode.CHEGG_02);
        }

        /**
         * 3
         */
         Optional<Orders> optionalOrder = repository.findByUserAndAssociatedBookAndVersion(transactingUser,catalogueBook,1);
         Orders issuingOrder=optionalOrder.get();


         LocalDate duedate=issuingOrder.getDueDate();
         int fineamount=0;
         try {
             if (duedate.isBefore(LocalDate.now())) {
                 System.out.println("inside fine calculator___________------------------------------------------------------------------ ");
                 long days = ChronoUnit.DAYS.between(duedate,LocalDate.now());
                 System.out.println(days);
                 fineamount = (int) days * (10);
             }
         }
         catch (Exception e){
                e.printStackTrace();
         }
        /**
         * 4) update order;
         * 5) mark the book available and remove user from book
         * 6) update the order with success status
         * */




        try {
              long version=issuingOrder.getVersion();
               version++;
              issuingOrder.setSubmitStatus(SubmissionStatus.SUBMITTED);
              issuingOrder.setFine(fineamount);
              issuingOrder.setVersion(version);

              saveOrUpdate(issuingOrder);

            catalogueBook.setBookStatus(BookStatus.AVAILABLE);
            catalogueBook.setUser(null);
            bookService.saveOrUpdate(catalogueBook);



        } catch (Exception exception){
            // rollback
            catalogueBook.setBookStatus(BookStatus.UNAVAILABLE);
            catalogueBook.setUser(transactingUser);
            bookService.saveOrUpdate(catalogueBook);

            long version=issuingOrder.getVersion();
            version--;
            issuingOrder.setSubmitStatus(SubmissionStatus.PENDING);
            issuingOrder.setFine(0);
            issuingOrder.setVersion(version);

            saveOrUpdate(issuingOrder);

        }
        return  issuingOrder;
    }







    public Optional<Orders> fetchById(String orderReference){
        return repository.findByOrderReference(orderReference);
    }



}
