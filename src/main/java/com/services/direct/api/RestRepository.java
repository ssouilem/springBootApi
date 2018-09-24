package com.services.direct.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.services.direct.bean.Direct;

@RepositoryRestResource
public interface RestRepository extends CrudRepository<Direct, Long>{

}
