package com.services.direct.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.services.direct.bean.City;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {

}
