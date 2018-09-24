package com.services.direct.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.direct.bean.City;
import com.services.direct.repo.CityRepository;

@Service
public class CityService implements ICityService {

	@Autowired
	private CityRepository repository;

	@Override
	public List<City> findAll() {

		List<City> cities = (List<City>) repository.findAll();

		return cities;
	}
}
