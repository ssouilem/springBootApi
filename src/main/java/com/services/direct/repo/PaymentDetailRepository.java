package com.services.direct.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.PaymentDetail;

@Repository
@Transactional
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Integer>{


}
