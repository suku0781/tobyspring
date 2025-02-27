package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import tobyspring.hellospring.data.OrderRepository;
import tobyspring.hellospring.order.OrderService;

@Configuration
@Import(DataConfig.class) // OrderConfig를 로딩할 때 DataConfig에 있는 모든 빈 설들도 다 가져오게 함.
public class OrderConfig {
    @Bean
    public OrderService orderService(JpaTransactionManager transactionManager) {
        return new OrderService(orderRepository(), transactionManager);
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository();
    }
}
