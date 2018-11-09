package com.services.direct.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Company;

@Repository
@Transactional
public interface CompanyRepository extends JpaRepository<Company, Integer>{

	@Query("SELECT cmp FROM Company cmp WHERE cmp.id=(:id)")
	Company getCompanyById(@Param("id") Integer id);
	
	@Query("SELECT cmp FROM Company cmp WHERE cmp.uid=(:uid)")
	Company getCompanyByUID(@Param("uid") String companyUid);
	
	@Modifying
	@Query("DELETE FROM Company cmp WHERE cmp.uid=(:uid)")
	void deleteCompanyByUID(@Param("uid") String companyUid);
	
//	@Query("SELECT contact FROM Contact contact")
//	List<Contact> getAllContacts();
}
