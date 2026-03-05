package com.xulei.likebackend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class LikeBackendApplication {

    public static void main(String[] args) {
        new LikeBackendApplication().run(args);
    }


    void run(String[] args){
        SpringApplication.run(LikeBackendApplication.class,args);
        log.info("app start...");
    }
}
