package com.logging.crud.service;

import com.logging.crud.dto.UserRequest;
import com.logging.crud.dto.UserResponse;
import com.logging.crud.entity.User;
import com.logging.crud.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("New user should be successfully create")
    void create_shouldCreateUserAndReturnResponse(){
        UserRequest request = new UserRequest("John","johndoe@gmail.com");
        User user = new User();
        user.setId(100L);
        user.setUsername("John");
        user.setEmail("johndoe@gmail.com");

        given(userRepository.save(any(User.class))).willReturn(user);

        UserResponse response = userService.create(request);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(100L);
        assertThat(response.username()).isEqualTo("John");
        assertThat(response.email()).isEqualTo("johndoe@gmail.com");
        verify(userRepository).save(any(User.class));
    }
    @Test
    @DisplayName("Mavjud bo'lmagan id bo'lsa exception otishi kerak")
    void findUser_UserNotFound_shouldThrowException(){
        Long id = 999L;
        given(userRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findUser(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");
    }
}
