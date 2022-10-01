//package com.padingpading.consumer.feign;
//
//import com.padingpading.consumer.User;
//import org.springframework.cloud.openfeign.FallbackFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
///**
// * @author libin
// * @description
// * @date 2022/3/9
// */
//@Component
//public class ProducerFeignFallBackFactory implements FallbackFactory<ProducerFeignClient> {
//
//    @Override
//    public ProducerFeignClient create(Throwable cause) {
//        //Throwable:错误原因,定制不同的降级策略。
//        return new ProducerFeignClient() {
//            @Override
//            public ResponseEntity<User> getUserById(Long id, String name) {
//             return ResponseEntity.ok(new User(1L,"熔断",1));
//            }
//
//            @Override
//            public ResponseEntity<User> createUser(User user) {
//                return ResponseEntity.ok(new User(1L,"熔断",1));
//            }
//        };
//    }
//}
