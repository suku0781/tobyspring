package tobyspring.hellospring.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestPaymentConfig;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPaymentConfig.class)
class PaymentServiceSpringTest {

    @Autowired PaymentService paymentService;
    @Autowired Clock clock;
    @Autowired ExRateProviderStub exRateProviderStub;
    @Test
    @DisplayName("prepare 메소드가 요구사항 3개지를 잘 충족했는지 검증")// 테스트 성공 또는 실패 시 어떤 테스트가 실행되었는지 뜨는 JUnit 기능
    void prepare() {
        // exRate : 1000
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(1_000));
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));

        // exRate : 500
        exRateProviderStub.setExRate(valueOf(500));
        Payment payment2 = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        assertThat(payment2.getExRate()).isEqualByComparingTo(valueOf(500));
        assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(valueOf(5_000));
    }

    @Test
    void validUntil() {
        Payment payment = paymentService.prepare(1L, "D", BigDecimal.TEN);

        // valid until 이 prepare() 30분 뒤로 설정되었는지?
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime exprctedValidUntil = now.plusMinutes(30);

        assertThat(payment.getValidUntil()).isEqualTo(exprctedValidUntil);
    }
}