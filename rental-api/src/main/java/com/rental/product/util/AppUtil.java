package com.rental.product.util;

import com.rental.product.mapper.AddressVO;
import com.rental.product.mapper.ResponseVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppUtil {

    public ResponseVO successResponse(Object object, String key, String message){
        ResponseVO responseVO = new ResponseVO();
        Map<String, Object> map = new HashMap<>();
        map.put(key, object);
        responseVO.setOutputData(map);
        responseVO.setSuccessMessage(message);
        responseVO.setAppStatus(true);
        return responseVO;
    }

    public ResponseVO failedResponse(String errorCode, String errorDetails){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setErrorCode(errorCode);
        responseVO.setErrorDetails(errorDetails);
        responseVO.setAppStatus(false);
        return responseVO;
    }

    public static String validateAddress(AddressVO addressVO, String type){
        String error = "";
        if(StringUtils.isBlank(addressVO.getStreet())){
            error = error + type + " Street";
        }
        if(StringUtils.isBlank(addressVO.getState())){
            error = error + type + " State";
        }
        if(StringUtils.isBlank(addressVO.getPostalCode())){
            error = error + type + " Postal Code";
        }
        if(StringUtils.isBlank(addressVO.getAddressLine1())){
            error = error + type + " Address Line 1";
        }
        if(StringUtils.isBlank(addressVO.getCountry())){
            error = error + type + " Country";
        }
        if(StringUtils.isBlank(addressVO.getMobileNumber())){
            error = error + type + " Mobile Number";
        }
        if(StringUtils.isBlank(addressVO.getAlternateMobileNumber())){
            error = error + type + " Alternate Mobile Number";
        }
        return error;
    }
}
