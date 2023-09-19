package com.dev.rest;

import com.alibaba.fastjson.JSON;
import com.dev.rest.entity.Black;
import com.dev.rest.mapper.BlackMapper;
import com.dev.rest.utils.GeneratePhoneNumber;
import com.dev.rest.utils.IdWorker;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;

public class MybatisCacheTest extends DevRestApplicationTests {

    @Autowired
    private BlackMapper blackMapper;

    @Autowired
    private IdWorker idWorker;

    @Test
    public void enableFirstLevelCache() throws IOException {
        Black black = blackMapper.selectByMobile("19739877067");
        System.err.println(black.hashCode()+JSON.toJSONString(black));
        Black black2 = blackMapper.selectByMobile("19739877067");
        System.err.println(black2.hashCode()+JSON.toJSONString(black2));
    }

    @Test
    public void closeFirstLevelCache() {
        Black black = blackMapper.selectByMobile("19739877067");
        System.err.println(JSON.toJSONString(black));
        Black black2 = blackMapper.selectByMobile("19739877067");
        System.err.println(JSON.toJSONString(black2));
    }

    @Test
    public void enableSecondLevelCache() {
        Black black = blackMapper.selectByMobile("19739877067");
        System.err.println(JSON.toJSONString(black));
        Black black2 = blackMapper.selectByMobile("19739877067");
        System.err.println(JSON.toJSONString(black2));
    }

    @Test
    public void closeSecondLevelCache() {
        Black black = blackMapper.selectByMobile("19739877067");
        System.err.println(JSON.toJSONString(black));
        Black black2 = blackMapper.selectByMobile("19739877067");
        System.err.println(JSON.toJSONString(black2));
    }



    @Test
    public void initDB() {
        for (int i = 0; i < 3; i++) {
            Black black = new Black();
            black.setMobile(GeneratePhoneNumber.generatePhoneNumber());
            black.setId(idWorker.nextId());
            blackMapper.insert(black);
        }
    }
}
