package io.pivotal.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CapsService {

    private Logger LOG = LoggerFactory.getLogger(Application.class);

    @Autowired
    private RestTemplate _restTemplate;

    @RequestMapping("/caps/{msg}")
    @HystrixCommand(fallbackMethod = "errorCondition")
    public ResponseObject caps(@PathVariable String msg) {
        LOG.info("Application received the message to shift to uppercase: " + msg);
        if(msg == null) msg = "";

        //Using this notation rest-template is autowired to connect to Eureka to lookup reverse service
        long start = System.currentTimeMillis();
        String response = _restTemplate.getForObject("http://reverse/reverse/" + msg, String.class);
        long end = System.currentTimeMillis();

        ResponseObject obj = new ResponseObject(response.toUpperCase(), msg);
        obj.setProcessingTime(end - start);

        return obj;
    }

    public ResponseObject errorCondition(String msg) {
        LOG.info("Invoking error condition: " + msg);
        if(msg == null) msg = "";
        return new ResponseObject(msg.toUpperCase(), msg, true);
    }
}