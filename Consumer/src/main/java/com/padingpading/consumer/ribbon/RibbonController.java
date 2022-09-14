package com.padingpading.consumer.ribbon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RibbonController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private RestTemplate restTemplate;
    
    @GetMapping("/v1/id")
    public ResponseEntity<String> getId() {
        // 通过服务名的形式调用
        ResponseEntity<String> result = restTemplate.getForEntity("http://demo-producer/v1/uuid", String.class);
        String uuid = result.getBody();
        logger.info("request id: {}", uuid);
        return ResponseEntity.ok(uuid);
    }
}
