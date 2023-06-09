package SpringBoot_JBDL_L1314.lecture13_14.commons;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static SpringBoot_JBDL_L1314.lecture13_14.commons.ResponseInfo.ResultStatusCode.SUCCESS;

@Slf4j
@Component("responseMapper")
public class ResponseGenerator {


    @Autowired
    Gson gson;


    public <T> ResponseEntity<String> generateSuccessResponse(T data, HttpStatus httpStatus){
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setData(data);
        responseInfo.setStatusCode(SUCCESS);
        return new ResponseEntity<String>(gson.toJsonTree(responseInfo).toString(), httpStatus);
    }


}
