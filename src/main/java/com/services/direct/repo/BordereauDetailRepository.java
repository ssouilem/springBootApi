package com.services.direct.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.BordereauDetail;

@Repository
@Transactional
public interface BordereauDetailRepository extends JpaRepository<BordereauDetail, Integer>{

}
