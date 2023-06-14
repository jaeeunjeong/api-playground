package learning;

import com.jejeong.apipractice.controller.sign.response.SignResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ResponseEntityTest {

    @InjectMocks
    private TestController testController;
    private MockMvc mockMvc;

    @Controller
    public static class TestController {
        public ResponseEntity<SignResponse> getEntity(String email) {
            return new ResponseEntity<>(SignResponse.of(email, "nickname"), HttpStatus.OK);
        }
    }


    @Test
    @DisplayName("ResponseEntity가 정상적으로 되는 지 확인한다.")
    void test1() {
        // given
        String email = "email@email.com";

        // when
        ResponseEntity<SignResponse> responseEntity = testController.getEntity(email);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();

        SignResponse res = responseEntity.getBody();
        assertThat(res.getEmail()).isEqualTo(email);
    }
}
