package SpringBoot_JBDL_L1314.lecture13_14.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter

public enum StatusCode {

    CHEGG_01("BNF_001", "Book Not found", HttpStatus.BAD_REQUEST),
    CHEGG_06("BNF_001", "Book Not found", HttpStatus.OK),
    CHEGG_07("BNF_001", "Author Not found", HttpStatus.OK),
    CHEGG_02("UNF_001", "User Not found", HttpStatus.BAD_REQUEST),
    CHEGG_08("UNF_002", "User Already exists ", HttpStatus.BAD_REQUEST),
    CHEGG_04("UQE_001", "User Quota Exceeded", HttpStatus.BAD_REQUEST),
    CHEGG_03("BNA_001", "Book Not available", HttpStatus.BAD_REQUEST),
    CHEGG_05("BNI_001", "Book Not Issued to User", HttpStatus.BAD_REQUEST),
    CHEGG_0N("ONF_001", "Order Not found", HttpStatus.BAD_REQUEST);




    String exceptionCode;
    String exceptionMessage;
    HttpStatus httpStatus;



}
