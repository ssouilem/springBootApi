//package com.services.direct.config;
//import java.util.stream.Collectors;
//
//import javax.persistence.EntityManager;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
//import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
//
//import com.services.direct.bean.Customer;
//import com.services.direct.repo.CustomerRepository;
//
//@Configuration
//public class AppConfig extends RepositoryRestConfigurerAdapter {
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Override
//    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream().map(e -> e.getJavaType()).collect(Collectors.toList()).toArray(new Class[0]));
//        config.exposeIdsFor(Customer.class);
//        config.withEntityLookup().//
//		forRepository(CustomerRepository.class, Customer::getId, CustomerRepository::findById);
//    }
//
//}