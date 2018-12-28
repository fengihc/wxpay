package cn.feng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.feng.dao")
@SpringBootApplication
public class WxpayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxpayApplication.class, args);
    }

}

