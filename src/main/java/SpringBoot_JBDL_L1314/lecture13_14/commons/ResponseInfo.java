package SpringBoot_JBDL_L1314.lecture13_14.commons;

import SpringBoot_JBDL_L1314.lecture13_14.models.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.MDC;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseInfo <T>{

    T data;
    ResultStatusCode statusCode;
    String message;
    String exceptionCode;
    String traceId;



    public ResponseInfo(StatusCode statusCode){
        this.exceptionCode = statusCode.getExceptionCode();
        this.message = statusCode.getExceptionMessage();
        this.traceId = MDC.get("traceId");
    }

    enum ResultStatusCode {
        SUCCESS,
        FAILED,
        PENDING
    }

}
