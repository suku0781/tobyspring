package tobyspring.hellospring.order;

import jakarta.persistence.*;

import java.math.BigDecimal;

/* @Entity 어노테이션이 있다켠 이 오브젝트 정보를 데이터베이스에 저장하거나 읽어와서 값을 확인할 때 사용할 수 있는 클래스이다. */
public class Order {
    private Long id; // DB가 자동으로 생성되게끔 할 예정.

    private String no;

    private BigDecimal total;

    public Order() {
    }

    public Order(String no, BigDecimal total) {
        this.no = no;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public String getNo() {
        return no;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", total=" + total +
                '}';
    }
}
