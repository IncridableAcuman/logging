package com.logging.crud.controller;

import com.logging.crud.dto.UserRequest;
import com.logging.crud.dto.UserResponse;
import com.logging.crud.exception.NotFound;
import com.logging.crud.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("/api/users create new user")
    void createUser_shouldReturnCreatedUser() throws Exception {
        UserRequest request = new UserRequest("vali","valiyev@gmail.com");
        UserResponse response = new UserResponse(1L,"vali","valiyev@gmail.com");

        given( userService.create( any( UserRequest.class ) ) ).willReturn(response);

        String json = """
               {
                "username" : "vali",
                "email" : "valiyev@gmail.com"
               }
               \s""";
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("vali"))
                .andExpect(jsonPath("$.email").value("valiyev@gmail.com"));

    }
    @Test
    @DisplayName("GET /api/user/100 user topilsa 200 OK")
    void getUser_existingId__shouldReturnUser() throws Exception {
        Long id = 100L;
        UserResponse response = new UserResponse(id,"jamshid","jamshid@gmail.com");

        given(userService.findUser(id)).willReturn(response);

        mockMvc.perform(get("/api/users/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("jamshid"));
    }
    @Test
    @DisplayName("/api/users/999 user topilmasa xatolik yuborish")
    void getUser_notFound_shouldThrowException() throws Exception {
        Long id = 999L;

        given(userService.findUser(id)).willThrow(new NotFound("User not found"));

        mockMvc.perform(get("/api/users/{id}",id))
                .andExpect(status().isNotFound());
    }
}
