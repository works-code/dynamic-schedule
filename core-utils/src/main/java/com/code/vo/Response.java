package com.code.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Data
public class Response {

    private String responseCode = "0";
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message = "";

    public void setMessage(Info info) {
        List<String> key = Arrays.asList(StringUtils.split(info.getKey(),"."));
        if(!CollectionUtils.isEmpty(key)){
            this.responseCode = key.get(key.size()-1);
            this.message = info.getValue();
        }else{
            // 오류 코드를 보내지 않았다면 기본 오류 메세지
            this.responseCode = "-1";
            this.message = "에러가 발생했습니다.";
        }
    }
}






