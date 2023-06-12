package com.jejeong.apipractice.controller.sign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jejeong.apipractice.controller.sign.request.SignUpRequest;
import com.jejeong.apipractice.dto.member.MemberDto;
import com.jejeong.apipractice.sevice.member.SignService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SignControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    SignService memberService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithAnonymousUser
    void signUpRequest() throws Exception {
        SignUpRequest req = SignUpRequest.of("email@example.com", "password", "nickname");

        // when
        mockMvc.perform(post("/api/v1/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))
        ).andExpect(status().isCreated());

    }

    @Test
    @WithAnonymousUser
    @DisplayName("이메일 값이 적절하지 않을 경우")
    void test1() throws Exception {
        SignUpRequest req = SignUpRequest.of("email@example", "password", "nickname");

        // when
        mockMvc.perform(post("/api/v1/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))
        ).andExpect(status().is4xxClientError());

    }

    @Test
    void processUserInfoRequest() throws Exception {
        // given
        String email = "test@test.com";
        String nickname = "nickname";
        MemberDto memberDto = new MemberDto(email, nickname);
        when(memberService.loadUserByUserEmail(anyString())).thenReturn(memberDto);

        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/me")
                .param("email", email)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(email))
            .andExpect(jsonPath("$.nickname").value(nickname))
            .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println(responseBody);
    }

    @Test
    void processWithdrawalRequest() throws Exception {
        // given
        String email = "email@gmail.com";

        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/me")
                .param("email", email)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        // then
        verify(memberService).deleteUserByUserEmail(email);
    }
}