package tobyspring.hellospring.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import tobyspring.hellospring.order.Order;

import java.math.BigDecimal;

public class OrderRepository { // 새로운 주문을 DB에 저장하는 역할
    private final EntityManagerFactory emf;

    public OrderRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Order order ){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            em.persist(order);
            em.flush();

            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction.isActive()) transaction.rollback();
            throw e; 
        } finally {
            if(transaction.isActive()) em.close();
        }
    }
}
