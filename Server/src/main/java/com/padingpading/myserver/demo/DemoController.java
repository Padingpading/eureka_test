package com.padingpading.myserver.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author libin
 * @description
 * @date 2022-02-27
 */
@RestController
public class DemoController {

    @GetMapping("/v1/uuid")
    public ResponseEntity<String> getUUID() {
        String uuid = UUID.randomUUID().toString();
        return ResponseEntity.ok(uuid);
    }
}
