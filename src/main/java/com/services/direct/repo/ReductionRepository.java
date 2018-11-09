package com.services.direct.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Reduction;

@Repository
@Transactional
public interface ReductionRepository extends JpaRepository<Reduction, Integer>{

	@Query("SELECT reduction FROM Reduction reduction WHERE reduction.uid=(:uid)")
	Reduction getReductionByUID(@Param("uid") String reductionUid);
	
	@Modifying
	@Query("DELETE FROM Reduction reduction WHERE reduction.uid=(:uid)")
	void deleteReductionByUID(@Param("uid") String reductionUid);
}
