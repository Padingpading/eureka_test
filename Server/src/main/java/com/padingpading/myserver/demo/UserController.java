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
    public ResponseEntity<User> queryUser(@PathVariable Long id, @RequestParam String name) {
        logger.info("query params: id :{}, name:{}", id, name);
        return ResponseEntity.ok(new User(id, name, 10));
    }

    @PostMapping("/v1/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("create params: {}", user);
        return ResponseEntity.ok(user);
    }
}
