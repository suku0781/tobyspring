package tobyspring.hellospring.data;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.simple.JdbcClient;
import tobyspring.hellospring.order.Order;
import tobyspring.hellospring.order.OrderRepository;

import javax.sql.DataSource;

public class JdbcOrderRepository implements OrderRepository {
    private final JdbcClient jdbcClient;

    public JdbcOrderRepository(DataSource dataSource) {
        this.jdbcClient = JdbcClient.create(dataSource);
    }

    @PostConstruct // 자바표준 어노테이션, 컨스트럴터가 실행이 다 되고 초기화 작업까지 끝난 다음 이걸 ㅋㄴ테이너가 자동으로 실행해주는것.
    void initDb() { // 클래스가 만들어지고 초기화가 다 끝나고 나서 빈이 준비되면 이 메서드를 실행해달라는 뜻.
        jdbcClient.sql("""
                create table orders (id bigint not null, no varchar(255), total numeric(38,2), primary key (id));
                alter table if exists orders drop constraint if exists UK43egxxciqr9ncgmxbdx2avi8n;
                alter table if exists orders add constraint UK43egxxciqr9ncgmxbdx2avi8n unique (no);
                create sequence orders_SEQ start with 1 increment by 50;
                """).update();
    }

    @Override
    public void save(Order order) {
        Long id = jdbcClient.sql("select next value for orders_SEQ").query(Long.class).single();

        order.setId(id);

        jdbcClient.sql("insert into orders (no,total,id) values (?,?,?)")
                .params(order.getNo(), order.getTotal(), order.getId())
                .update();
    }
}
