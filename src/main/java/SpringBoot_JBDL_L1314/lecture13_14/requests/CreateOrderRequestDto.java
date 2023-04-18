package SpringBoot_JBDL_L1314.lecture13_14.requests;

import SpringBoot_JBDL_L1314.lecture13_14.models.Orders;
import SpringBoot_JBDL_L1314.lecture13_14.models.OrderStatus;
import SpringBoot_JBDL_L1314.lecture13_14.models.SubmissionStatus;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequestDto {

    @Positive
    double amount;

    @NotBlank
    String orderStatus;

    public Orders toOrder(){

        return Orders.builder()
                .amount(amount)
                .orderReference(UUID.randomUUID().toString())
                .orderStatus(OrderStatus.valueOf(orderStatus))
                .orderDate( LocalDate.now())
                .dueDate(LocalDate.now().plusMonths(2))
                .submitStatus(SubmissionStatus.PENDING)
                .build();
    }
}
