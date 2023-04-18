package SpringBoot_JBDL_L1314.lecture13_14.requests;

import SpringBoot_JBDL_L1314.lecture13_14.models.OrderStatus;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderRequestDto {

    @Positive
    double amount;

    @NotBlank
    String orderStatus;

    @NotBlank
    String orderReferenceId;


}
