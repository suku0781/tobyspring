package tobyspring.hellospring;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tobyspring.hellospring.order.Order;
import tobyspring.hellospring.payment.PaymentService;

import java.math.BigDecimal;

public class DataClient {
    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        EntityManagerFactory emf = beanFactory.getBean(EntityManagerFactory .class);

        // 1. Emtity Manager를 생성.
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // 2. Transaction을 생성.
        Order order = new Order("100", BigDecimal.TEN);
        System.out.println(order);
        em.persist(order);

        System.out.println(order);

        em.getTransaction().commit();

        em.close();
        // 3. em.persist

    }
}
