package learning;

import com.jejeong.apipractice.util.JwtTokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TokenUtilsTest {

    @Test
    @DisplayName("토큰 생성하기 및 검증하기")
    void test1() {
        // given
        String email = "email@email.com";
        String accessEncodedKey = Base64.getEncoder().encodeToString("access-token-api-playground".getBytes());
        String refreshEncodedKey = Base64.getEncoder().encodeToString("refresh-token-api-playground".getBytes());
        long accessExpiredTimeMs = 25920000L;
        long refreshExpiredTimeMs = 25920000L;

        // when
        String accessToken = JwtTokenUtils.generateAccessToken(email, accessEncodedKey, accessExpiredTimeMs);
        String refreshToken = JwtTokenUtils.generateAccessToken(email, refreshEncodedKey, refreshExpiredTimeMs);

        // then
        System.out.println(accessToken);
        System.out.println(refreshToken);
    }

    @Test
    @DisplayName("파싱 확인하기")
    void test2() {
        // given
        String email = "email@email.com";
        String accessEncodedKey = Base64.getEncoder().encodeToString("access-token-api-playground-2023-ABCDEFGHIJKLMNOP".getBytes());
        long accessExpiredTimeMs = 25920000L;

        // when
        String accessToken = JwtTokenUtils.generateAccessToken(email, accessEncodedKey, accessExpiredTimeMs);

        // then
        String userEmail = JwtTokenUtils.getUserEmail(accessToken, accessEncodedKey);
        assertThat(userEmail).isEqualTo(email);
    }

    @Test
    @DisplayName("토큰 검증하기")
    void test3() {
        // given
        String email = "email@email.com";
        String accessEncodedKey = Base64.getEncoder().encodeToString("access-token-secret-key-api-playground".getBytes());
        long accessExpiredTimeMs = 25920000L;

        // when
        String accessToken = JwtTokenUtils.generateAccessToken(email, accessEncodedKey, accessExpiredTimeMs);
        System.out.println(accessToken);
        // then
        assertThat(JwtTokenUtils.validate(accessToken, email, accessEncodedKey)).isTrue();
    }


    @Test
    @DisplayName("토큰 검증하기 - 키가 다름")
    void test4() {
        // given
        String email = "email@email.com";
        String accessEncodedKey = Base64.getEncoder().encodeToString("access-token-secret-key-api-playground".getBytes());
        long accessExpiredTimeMs = 25920000L;

        // when
        String accessToken = JwtTokenUtils.generateAccessToken(email, accessEncodedKey.concat("-fake"), accessExpiredTimeMs);
        System.out.println(accessToken);

        // then
        assertFalse(JwtTokenUtils.validate(accessToken, email, accessEncodedKey));
    }

    @Test
    @DisplayName("토큰 검증하기 -기간 만료")
    void test5() {
        // given
        String email = "email@email.com";
        String accessEncodedKey = Base64.getEncoder().encodeToString("access-token-secret-key-api-playground".getBytes());
        long accessExpiredTimeMs = 1L;

        // when
        String accessToken = JwtTokenUtils.generateAccessToken(email, accessEncodedKey, accessExpiredTimeMs);
        System.out.println("test5 : " + accessToken);

        // then
        assertFalse(JwtTokenUtils.validate(accessToken, email, accessEncodedKey));
    }
}
