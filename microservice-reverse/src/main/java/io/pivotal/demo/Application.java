package io.pivotal.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@RestController
public class Application {

    private Logger LOG = LoggerFactory.getLogger(Application.class);

    @RequestMapping("/reverse/{msg}")
    public String reverse(@PathVariable String msg) {
        LOG.info("Application received the message to reverse: " + msg);
        if(msg == null) msg = "";

        //Special kill switch
        if("kill".equalsIgnoreCase(msg)) System.exit(-1);

        return new StringBuilder(msg).reverse().toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

