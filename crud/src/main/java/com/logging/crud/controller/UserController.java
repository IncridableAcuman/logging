package com.logging.crud.controller;

import com.logging.crud.dto.UserRequest;
import com.logging.crud.dto.UserResponse;
import com.logging.crud.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request){
        log.info("CREATE user request received email={}",request.getEmail());
        return ResponseEntity.ok(userService.create(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUser(@PathVariable Long id){
        log.info("GET user request received id={}",id);
        return ResponseEntity.ok(userService.findUser(id));
    }
}
