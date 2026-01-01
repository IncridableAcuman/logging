package com.logging.crud.service;

import com.logging.crud.dto.UserRequest;
import com.logging.crud.dto.UserResponse;
import com.logging.crud.entity.User;
import com.logging.crud.exception.NotFound;
import com.logging.crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse create(UserRequest request){
        if (!StringUtils.hasText(request.getUsername()) || !StringUtils.hasText(request.getEmail())){
            log.info("Invalid user data: {}",request);
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        log.debug("Creating user with email={}", user.getEmail());
         user = userRepository.save(user);
         log.info("User created successfully id={},email={}",user.getId(),user.getEmail());
        return new UserResponse(user.getId(),user.getUsername(),user.getEmail());
    }
    @Transactional
    public UserResponse findUser(Long id){
        log.info("Finding user by id={}",id);
        User user = userRepository.findById(id).orElseThrow(()-> {
            log.warn("User not found id={}",id);
            return new NotFound("User not found");
        });
        return new UserResponse(user.getId(),user.getUsername(),user.getEmail());
    }
}
