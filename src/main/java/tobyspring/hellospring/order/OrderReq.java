package tobyspring.hellospring.order;

import java.math.BigDecimal;

public record OrderReq(String no, BigDecimal total) { // 생성자에 해당하는 부분을 파라미터에 바로 집어넣는다.
}
