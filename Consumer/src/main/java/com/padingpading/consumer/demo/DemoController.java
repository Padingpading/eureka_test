package com.padingpading.consumer.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController {


    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/v1/id")
    public ResponseEntity<String> getId() {
        ResponseEntity<String> result = restTemplate.getForEntity("http://demo-producer/v1/uuid", String.class);
        String uuid = result.getBody();
        return ResponseEntity.ok(uuid);
    }
}
