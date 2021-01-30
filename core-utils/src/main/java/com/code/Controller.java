package com.code;

import com.code.properties.MessagePop;
import com.code.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Controller {

    @Autowired
    MessagePop messagePop;

    @RequestMapping(value = "/")
    public Response main(String err){
        Response response = new Response();
        log.error(messagePop.getMessage(err).getValue());
        response.setMessage(messagePop.getMessage(err));
        return response;
    }
}
