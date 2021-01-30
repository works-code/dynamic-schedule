package com.code.properties;

import com.code.vo.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Slf4j
@Component
public class MessagePop {

    @Autowired
    Environment environment;

    public Info getMessage(String key){
        return new Info(){{
            setKey(key);
            setValue(environment.getProperty(key));
        }};
    }
}
