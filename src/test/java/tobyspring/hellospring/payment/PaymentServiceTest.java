package tobyspring.hellospring.payment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PaymentServiceTest {
    Clock clock;
    @BeforeEach
    void beforeEach() {
         this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }
    @Test
    @DisplayName("prepare 메소드가 요구사항 3개지를 잘 충족했는지 검증")// 테스트 성공 또는 실패 시 어떤 테스트가 실행되었는지 뜨는 JUnit 기능
    void prepare() {


        testAmount(valueOf(500), valueOf(5_000), clock);
        testAmount(valueOf(1_000), valueOf(10_000), clock);
        testAmount(valueOf(3_000), valueOf(30_000), clock);


        // 원화 환산 금액의 유효시간 계산
//        assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
//        assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));

    }

    @Test
    void validUntil() {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(valueOf(1_000)), clock);

        Payment payment = paymentService.prepare(1L, "D", BigDecimal.TEN);

        // valid until 이 prepare() 30분 뒤로 설정되었는지?
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime exprctedValidUntil = now.plusMinutes(30);

        assertThat(payment.getValidUntil()).isEqualTo(exprctedValidUntil);
    }

    private static void testAmount(BigDecimal exRate, BigDecimal convertedAmount, Clock clock) {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);

        Payment payment = paymentService.prepare(1L, "D", BigDecimal.TEN);

        // 환율 정보를 가져온다.
        assertThat(payment.getExRate()).isEqualTo(exRate);

        // 원화 환산 금액 계산
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount); // 자바에서 자릿수를 구분해주기위해서 _ 를 사용할 수 있다.
        /*
        * BigDecimal 은 isEqualTo() 를 사용하는것 보다 isEqualByComparingTo()를 사용하는 것이 좋다.
        * BigDecimal a = new BigDecimal("1.0");
        * BigDecimal b = new BigDecimal("1.00");
        * 을 isEqualTo()로 비교시에는 false가 나오지만(a는 스케일이 1이고, b 는 스케일이 2 이다) isEqualByComparingTo()로 비교할 경우에 true가 나온다.
        * isEqualByComparingTo()는 스케일을 무시하고 수학적으로 동등한 값을 비교할 경우에 사용한다.
        * */
    }
}