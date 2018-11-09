package com.services.direct.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.BordereauDetail;

@Repository
@Transactional
public interface BordereauDetailRepository extends JpaRepository<BordereauDetail, Integer>{

	@Query("SELECT bordereauDetail FROM BordereauDetail bordereauDetail WHERE bordereauDetail.uid=(:uid)")
	BordereauDetail getBordereauDetailByUID(@Param("uid") String bordereauDetailUid);
	
	@Modifying
	@Query("DELETE FROM BordereauDetail bordereauDetail WHERE bordereauDetail.uid=(:uid)")
	void deleteBordereauDetailByUID(@Param("uid") String bordereauDetailUid);
	
}
