package com.padingpading.consumer.demo;


import com.padingpading.consumer.User;
import com.padingpading.consumer.feign.ProducerFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class FeignController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProducerFeignClient producerFeignClient;

    @GetMapping("/v1/user/query")
    public ResponseEntity<User> queryUser() {
        ResponseEntity<User> result = producerFeignClient.getUserById(1L, "tom");
        User user = result.getBody();
        logger.info("query user: {}", user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/v1/user/create")
    public ResponseEntity<User> createUser() {
        ResponseEntity<User> result = producerFeignClient.createUser(new User(10L, "Jerry", 20));
        User user = result.getBody();
        logger.info("create user: {}", user);
        return ResponseEntity.ok(user);
    }
}
