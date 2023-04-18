package SpringBoot_JBDL_L1314.lecture13_14.exceptions;

import SpringBoot_JBDL_L1314.lecture13_14.models.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BookException extends RuntimeException{

    StatusCode statusCode;
    public BookException(StatusCode statusCode){
        super(statusCode.getExceptionMessage());
        this.statusCode = statusCode;
    }

}
