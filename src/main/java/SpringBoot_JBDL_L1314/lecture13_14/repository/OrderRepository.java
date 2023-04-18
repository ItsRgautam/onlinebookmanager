package SpringBoot_JBDL_L1314.lecture13_14.repository;

import SpringBoot_JBDL_L1314.lecture13_14.models.Orders;
import SpringBoot_JBDL_L1314.lecture13_14.models.Book;
import SpringBoot_JBDL_L1314.lecture13_14.models.SubmissionStatus;
import SpringBoot_JBDL_L1314.lecture13_14.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Integer> {


    /**
     * JPQL  -- java persistence query language -- this format executes query considering java objects
     * native SQL -- this is same as old sql
     * and
     * Modern way -- this is prety easy
     */

    Optional<Orders> findByOrderReference(String orderRef);
    Optional<Orders> findByUserAndAssociatedBookAndVersion(UserInfo user, Book book,int version);

    @Query(value = "select o from Orders o WHERE o.user =:user and o.submitStatus =:submitState and o.dueDate <:date ")
    Optional<List<Orders>> findPendingOrders(@Param("user") UserInfo user, @Param("submitState") SubmissionStatus submitStatus, @Param("date") LocalDate date);




//    @Query(value = "select * from order where order_reference = orderRef", nativeQuery = true)
//    Optional<Order> findOrdersbyOrderRefence(@Param("orderRef") String orderReference);
//
//    @Query(value = "select o from Order o where o.orderReference = :orderRef")
//    Optional<Order> fuindByOrderRef(@Param("orderRef") String orderRef);
}
