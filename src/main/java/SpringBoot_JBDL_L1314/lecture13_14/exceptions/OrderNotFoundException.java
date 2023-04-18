package SpringBoot_JBDL_L1314.lecture13_14.exceptions;

import SpringBoot_JBDL_L1314.lecture13_14.models.StatusCode;
import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {

    StatusCode statusCode;
    public OrderNotFoundException(StatusCode statusCode){
        super(statusCode.getExceptionMessage());
        this.statusCode= statusCode;
    }
}
