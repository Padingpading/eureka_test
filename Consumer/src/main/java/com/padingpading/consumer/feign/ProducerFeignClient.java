package com.padingpading.consumer.feign;

import com.padingpading.consumer.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author libin
 * @description
 * @date 2022/3/9
 */
@FeignClient(value = "demo-producer")
public interface ProducerFeignClient {

    @GetMapping("/v1/user/{id}")
    ResponseEntity<User> getUserById(@PathVariable Long id, @RequestParam(required = false) String name);

    @PostMapping("/v1/user")
    ResponseEntity<User> createUser(@RequestBody User user);
}
