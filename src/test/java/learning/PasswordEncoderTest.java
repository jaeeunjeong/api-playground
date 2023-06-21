package learning;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("비밀번호가 암호화 되었는지 확인")
    void test1() {
        // given
        String password = "password";

        // when
        String encodedPassword = passwordEncoder.encode(password);

        // then
        assertThat(encodedPassword).isNotEqualTo(password);

    }

    @Test
    @DisplayName("비밀번호 매칭되는지 확인")
    void test2() {
        // given
        String password = "password";
        String encodingPassword = passwordEncoder.encode(password);

        // when
        boolean isMatch = passwordEncoder.matches(password, encodingPassword);

        // then
        assertThat(isMatch).isTrue();


    }
}
