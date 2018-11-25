package com.services.direct.repo;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Bordereau;

@Repository
@Transactional
public interface BordereauRepository extends JpaRepository<Bordereau, Integer>{
	
	String Q_GET_ALL_BORDEREAUX = "select br from Bordereau br left join br.customer";

	@Query("SELECT br FROM Bordereau br WHERE br.id=(:id)")
	Bordereau getBordereauById(@Param("id") Integer id);
	
	@Query("SELECT br FROM Bordereau br WHERE br.uid=(:uid)")
	Bordereau getBordereauByUID(@Param("uid") String bordereauUid);
	
	@Modifying
	@Query("DELETE FROM Bordereau br WHERE br.uid=(:uid)")
	void deleteBordereauByUID(@Param("uid") String bordereauUid);
	
	@Query(Q_GET_ALL_BORDEREAUX)
	Collection<Bordereau> getAllBordereaux();

}
