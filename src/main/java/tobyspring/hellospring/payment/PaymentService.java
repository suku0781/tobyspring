package tobyspring.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;


public class PaymentService {
    private final ExRateProvider exRateProvider;
    private final Clock clock;

    public PaymentService(ExRateProvider exRateProvider, Clock clock) {
        this.exRateProvider =  exRateProvider;
        this.clock = clock;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrentAmount) throws IOException {
        BigDecimal exRate = exRateProvider.getExRate(currency);
        /* 의존하고 있는 다른 오브젝트로 부터 뭔가를 요청해서 정보를 가오는 것, 핵심적인 로직 호출만해서 리턴함. 사용하는 클라이언트 쪽으로*/

        return Payment.createPrepared(orderId, currency, foreignCurrentAmount, exRate, LocalDateTime.now(clock));
    }


}
