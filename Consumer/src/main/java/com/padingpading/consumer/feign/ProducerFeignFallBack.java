package com.padingpading.consumer.feign;

import com.padingpading.consumer.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author libin
 * @description
 * @date 2022/3/9
 */
@Component
public class ProducerFeignFallBack implements ProducerFeignClient {

    @Override
    public ResponseEntity<User> getUserById(Long id, String name) {
        return ResponseEntity.ok(new User(1L,"熔断",1));
    }

    @Override
    public ResponseEntity<User> createUser(User user) {
        return ResponseEntity.ok(new User(1L,"熔断",1));
    }
}
