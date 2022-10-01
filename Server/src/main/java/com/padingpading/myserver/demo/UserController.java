package com.padingpading.myserver.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author libin
 * @description
 * @date 2022/3/9
 */
@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/v1/user/{id}")
    public ResponseEntity<User> queryUser(@PathVariable Long id, @RequestParam String name)
            throws InterruptedException {
        logger.info("query params: id :{}, name:{}", id, name);
        Thread.sleep(10000);
        return ResponseEntity.ok(new User(id, name, 10));
    }

    @PostMapping("/v1/user")
    public ResponseEntity<User> createUser(@RequestBody User user) throws InterruptedException {
        logger.info("create params: {}", user);
        Thread.sleep(10000);
        return ResponseEntity.ok(user);
    }
}
