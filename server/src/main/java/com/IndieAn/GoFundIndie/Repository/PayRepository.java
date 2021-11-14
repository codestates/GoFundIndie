package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.PayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PayRepository extends EntityManagerExtend{
    private final EntityManager entityManager;

    @Autowired
    public PayRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<PayRequest> FindPayRequestByEmail(String email) {
        List<PayRequest> payRequestList = entityManager
                .createQuery("SELECT p FROM PayRequest AS p WHERE p.email='" + email + "'", PayRequest.class)
                .getResultList();

        end(entityManager);
//        if(payRequestList.size() == 0) return null;
//        return payRequestList.get(0);
        return payRequestList;
    }

    public void CreatePayRequest(String email, String tid, Integer amount, String nextRedirectPcUrl) {
        PayRequest payRequest = new PayRequest();
        payRequest.setEmail(email);
        payRequest.setTid(tid);
        payRequest.setAmount(amount);
        payRequest.setNextRedirectPcUrl(nextRedirectPcUrl);

        entityManager.persist(payRequest);
        end(entityManager);
    }

    public void DeletePayRequest(long payId) {
        PayRequest payRequest = entityManager.find(PayRequest.class, payId);
        entityManager.remove(payRequest);
        end(entityManager);
    }

    public PayRequest FindPayRequestByEmailandUrl(String email, String next_redirect_pc_url) {
        List<PayRequest> payRequestList = entityManager
                .createQuery("SELECT p FROM PayRequest AS p WHERE p.email='" + email + "'" +
                        " AND p.nextRedirectPcUrl='" + next_redirect_pc_url + "'", PayRequest.class)
                .getResultList();

        end(entityManager);
        if(payRequestList.size() == 0) return null;
        return payRequestList.get(0);
    }
}
