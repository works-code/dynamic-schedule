package com.code;

import com.code.dao.SchaduleDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class DataStoreApplicationTests {

    @Autowired
    SchaduleDao schaduleDao;

    @Test
    void contextLoads() {
    }

    @Test
    void 스케쥴조회테스트(){
        log.error("===> {}",schaduleDao.selectJobSchedules());
    }

}
