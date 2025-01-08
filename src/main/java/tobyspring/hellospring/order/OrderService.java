package tobyspring.hellospring.order;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import tobyspring.hellospring.data.JpaOrderRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PlatformTransactionManager transactionManager; // JPA를 쓰는 트랜잭션 메니저이든 JDBC기술을 사용하는 트랜잭션 매니저이든 관계없이 코드는 그대로 변경되지않고 유지됨.

    public OrderService(OrderRepository orderRepository, PlatformTransactionManager transactionManager) {
        this.orderRepository = orderRepository;
        this.transactionManager = transactionManager;
    }

    public Order createOrder(String no, BigDecimal total) {
        Order order = new Order(no, total);

        this.orderRepository.save(order); // jdbc autocommit이 있기때문에 transaction을 시작하지 않아도 문제가 발생하지 않는다.

//        return new TransactionTemplate(transactionManager).execute(status -> {
//            this.orderRepository.save(order);
//            return order;
//        });

        return order;
    }

    public List<Order> createOrders(List<OrderReq> reqs) {
        return new TransactionTemplate(transactionManager).execute(status -> reqs.stream().map(req ->  createOrder(req.no(), req.total()) ).toList() );
    }
}
